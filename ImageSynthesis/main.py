# -*- coding: utf-8 -*-
# @Author:      HgS_1217_
# @Create Date: 2017/5/14

import cv2, numpy
import config
import tf.inference

sess, pred, data_pl = tf.inference.create_sesssion()


def get_transform_point(original_point, transform_matrix):
    original_point_list = numpy.array([[original_point[0]], [original_point[1]], [1.0]])
    target_point_list = transform_matrix * original_point_list
    x = int(target_point_list[0][0] / target_point_list[2][0])
    y = int(target_point_list[1][0] / target_point_list[2][0])
    print x, y
    return x, y


def get_overlap(img1, img2, i, j, k, rows):
    weight = 0.5 + abs(i - rows / 2.0) / rows
    if img1[i][j][k] == 0:
        return img2[i][j][k]
    elif img2[i][j][k] == 0:
        return img1[i][j][k]
    else:
        return (1 - weight) * img1[i][j][k] + weight * img2[i][j][k]


def merge_img(img1, img2):
    if len(img1) > len(img2):
        smaller_img = img2
        bigger_img = img1
    else:
        smaller_img = img1
        bigger_img = img2
    height = len(bigger_img)
    width = len(smaller_img[0])
    new_img = numpy.ones((height, width, 3)) * 255
    if len(smaller_img) % 2 == 0:
        new_img[height / 2 - len(smaller_img) / 2: height / 2 + len(smaller_img) / 2, :, :] = smaller_img
    else:
        new_img[height / 2 - len(smaller_img) / 2: height / 2 + len(smaller_img) / 2 + 1, :, :] = smaller_img

    if len(img1) > len(img2):
        img = numpy.hstack((bigger_img, new_img))
        first_img_start_x = 0
        first_img_start_y = 0
        second_img_start_x = len(bigger_img[0])
        second_img_start_y = height / 2 - len(smaller_img) / 2
    else:
        img = numpy.hstack((new_img, bigger_img))
        first_img_start_x = 0
        first_img_start_y = height / 2 - len(smaller_img) / 2
        second_img_start_x = width
        second_img_start_y = 0

    return img, first_img_start_x, first_img_start_y, second_img_start_x, second_img_start_y


def draw_key_point(img1, img2, kp_pairs):
    img, first_img_start_x, first_img_start_y, second_img_start_x, second_img_start_y = merge_img(img1, img2)
    for kp1, kp2 in kp_pairs:
        first_x = kp1[0]
        first_y = kp1[1]
        second_x = kp2[0]
        second_y = kp2[1]
        cv2.line(img, (int(first_img_start_x + first_x), int(first_img_start_y + first_y)),
                 (int(second_img_start_x + second_x), int(second_img_start_y + second_y)), (255, 0, 0), 2)
    return img


def main():
    img1 = cv2.imread(config.UPLOAD_IMAGE_FOLDER + "/origin.png", cv2.IMREAD_COLOR)
    img2 = cv2.imread(config.UPLOAD_IMAGE_FOLDER + "/new.png", cv2.IMREAD_COLOR)
    gray1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY);
    gray2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY);

    sift = cv2.SIFT()
    kp1, des1 = sift.detectAndCompute(gray1, None)
    kp2, des2 = sift.detectAndCompute(gray2, None)

    bf = cv2.BFMatcher()
    matches = bf.knnMatch(des1, des2, k=2)

    good = []
    for m, n in matches:
        if m.distance < 0.75 * n.distance:
            good.append((m, m.distance / n.distance))

    good_sorted = sorted(good, key=lambda x: x[1])
    good = [x[0] for x in good_sorted]
    kp_pairs = []
    for i in range(40):
        kp_pairs.append([kp1[good[i].queryIdx].pt, kp2[good[i].trainIdx].pt])

    img = draw_key_point(img1, img2, kp_pairs)
    cv2.imwrite("matchResult.jpg", img)

    kp1, kp2 = numpy.array([x[0] for x in kp_pairs]), numpy.array([x[1] for x in kp_pairs])
    homo = cv2.findHomography(kp1, kp2, cv2.RANSAC)
    adjust_mat = numpy.matrix([[1.0, 0, len(img1[0])], [0, 1.0, 0], [0, 0, 1.0]])
    adjust_homo = adjust_mat * homo[0]

    original_link_point = kp1[0]
    target_link_point = get_transform_point(original_link_point, adjust_homo)
    based_image_point = kp2[0]

    img_trans = cv2.warpPerspective(img1, adjust_homo, (len(img1[0]) + len(img2[0]) + 110, len(img2)))

    img1_input = img_trans.copy()
    img2_input = img2.copy()

    # tf here  use img1_input & img2_input and give the mat only with people


    img1_tf_output = tf.inference.infer(sess, pred, img1_input, data_pl)
    img2_tf_output = tf.inference.infer(sess, pred, img2_input, data_pl)

    col = int(target_link_point[0] - based_image_point[0])
    img1_overlap = img_trans[0: len(img2), col:]
    img2_overlap = img2[0: len(img1_overlap), 0: len(img1_overlap[0])]
    cv2.imwrite("img1_overlap.jpg", img1_overlap)
    cv2.imwrite("img2_overlap.jpg", img2_overlap)

    if len(img1_overlap[0]) > len(img2_overlap[0]):
        img1_overlap = img1_overlap[0: len(img2), 0: len(img2_overlap[0])]
    img1_roi_copy = img1_overlap.copy()

    rows = len(img2_overlap)
    for i in range(len(img1_overlap)):
        for j in range(len(img1_overlap[0])):
            img1_overlap[i][j][0] = get_overlap(img1_roi_copy, img2_overlap, i, j, 0, rows)
            img1_overlap[i][j][1] = get_overlap(img1_roi_copy, img2_overlap, i, j, 1, rows)
            img1_overlap[i][j][2] = get_overlap(img1_roi_copy, img2_overlap, i, j, 2, rows)

    for i in range(len(img_trans)):
        for j in range(len(img_trans[0])):
            for k in range(3):
                if img1_tf_output[i][j][k] != 0:
                    img_trans[i][j][k] = img1_tf_output[i][j][k]

    for i in range(len(img2)):
        for j in range(col, col + len(img2_overlap)):
            for k in range(3):
                if img2_tf_output[i][j][k] != 0:
                    img_trans[i][j][k] = img2_tf_output[i][j][k]

    img_trans = img_trans[0: len(img1_overlap), col: col + len(img1_overlap[0])]
    cv2.imwrite("transResult.jpg", img_trans)


if __name__ == '__main__':
    main()
