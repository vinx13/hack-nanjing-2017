"""Run DeepLab-ResNet on a given image.

This script computes a segmentation mask for a given image.
"""

from __future__ import print_function

import argparse
from datetime import datetime
import os
import sys
import time
import numpy as np
import deeplab_resnet.utils
from PIL import Image

import tensorflow as tf
import numpy as np

from deeplab_resnet import DeepLabResNetModel, ImageReader, prepare_label

IMG_MEAN = np.array((104.00698793, 116.66876762, 122.67891434), dtype=np.float32)

NUM_CLASSES = 21
SAVE_DIR = './'
MODEL_WEIGHTS = '/Users/Vincent/deeplab_resnet.ckpt'
INPUT_HEIGHT = 300
INPUT_WEIGHT = 300


def load(saver, sess, ckpt_path=MODEL_WEIGHTS):
    '''Load trained weights.

    Args:
      saver: TensorFlow saver object.
      sess: TensorFlow session.
      ckpt_path: path to checkpoint file with parameters.
    '''
    saver.restore(sess, ckpt_path)
    print("Restored model parameters from {}".format(ckpt_path))


def create_sesssion():
    """Create the model and start the evaluation process."""
    # Create network.
    data_pl = tf.placeholder(tf.float32, shape=(1, None, None, 3))
    net = DeepLabResNetModel({'data': data_pl}, is_training=False, num_classes=NUM_CLASSES)

    # Which variables to load.
    restore_var = tf.global_variables()

    # Predictions.
    raw_output = net.layers['fc1_voc12']
    raw_output_up = tf.image.resize_bilinear(raw_output, tf.shape(data_pl)[1:3])
    raw_output_up = tf.argmax(raw_output_up, dimension=3)
    pred = tf.expand_dims(raw_output_up, dim=3)

    # Set up TF session and initialize variables.
    config = tf.ConfigProto()
    config.gpu_options.allow_growth = True
    sess = tf.Session(config=config)

    init = tf.global_variables_initializer()
    sess.run(init)
    # Load weights.
    loader = tf.train.Saver(var_list=restore_var)
    load(loader, sess)

    return (sess, pred, data_pl)

i=1
def infer(sess, pred, img, data_pl):
    # Convert RGB to BGR.
    img_r, img_g, img_b = np.split(img, 3, axis=2)
    img = np.concatenate((img_b, img_g, img_r), axis=2).astype(dtype=np.float32)
    # Extract mean.
    img -= IMG_MEAN
    img = np.expand_dims(img, axis=0)
    feed_dict = {data_pl: img}

    # Perform inference.
    preds = sess.run(pred, feed_dict)
    msk = deeplab_resnet.utils.decode_labels(preds)
    im = Image.fromarray(msk[0])
    global i
    i+=1
    im.save('mask'+str(i)+'.png')


    pred = preds[0]
    mask = np.zeros_like(pred)
    mask[pred[:, :, 0] != 15] = 1

    return mask


if __name__ == '__main__':
    main()
