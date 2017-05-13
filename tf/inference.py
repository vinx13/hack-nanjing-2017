"""Run DeepLab-ResNet on a given image.

This script computes a segmentation mask for a given image.
"""

from __future__ import print_function

import argparse
from datetime import datetime
import os
import sys
import time

from PIL import Image

import tensorflow as tf
import numpy as np

from deeplab_resnet import DeepLabResNetModel, ImageReader, prepare_label

IMG_MEAN = np.array((104.00698793, 116.66876762, 122.67891434), dtype=np.float32)

NUM_CLASSES = 21
SAVE_DIR = './'
MODEL_WEIGHTS='/Users/Vincent/deeplab_resnet.ckpt'
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
    #raw_output_up = tf.image.resize_bilinear(raw_output, tf.shape(img)[0:2, ])
    raw_output_up = tf.argmax(raw_output, dimension=3)
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

    return (sess, data_pl)

def infer(img, feed_dict):
    # Convert RGB to BGR.
    img_r, img_g, img_b = tf.split(axis=2, num_or_size_splits=3, value=img)
    img = tf.cast(tf.concat(axis=2, values=[img_b, img_g, img_r]), dtype=tf.float32)
    # Extract mean.
    img -= IMG_MEAN
    img = tf.expand_dims(img, dim=0)
    #feed_dict = {data_pl: img}

    # Perform inference.
    preds = sess.run(pred, feed_dict)
    pred = preds[0]
    mask = pred[:, :, 0] != 15
    return mask


if __name__ == '__main__':
    main()
