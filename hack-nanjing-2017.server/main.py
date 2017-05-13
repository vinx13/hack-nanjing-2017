# -*- coding: utf-8 -*-
# @Author: gigaflower
# @Date:   2017-04-19 09:49:55
# @Last Modified by:   gigaflower
# @Last Modified time: 2017-04-19 10:10:13

from flask import Flask, request, jsonify, send_from_directory, abort
from config import ALLOWED_EXTENSIONS, UPLOAD_IMAGE_FOLDER
from werkzeug import secure_filename
import time, os

app = Flask("HP")
app.secret_key = "HackNanjing"


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS


@app.route('/')
def main():
    return '''
        <style>
            h1 {
                font-size:150px;
                color: #ada;
                font-family: cursive;
                text-align: center;
                margin: 70px 30px;
                font-weight: normal;
            }
            p {
                color: #9a9;
                font-size:20px;
                text-align: right;
            }

        </style>
        <h1>合<br>拍</h1>
        <p>%s</p>
        ''' % time.asctime()


@app.route("/uploadimage", methods=['POST'])
def uploadimage():
    name = request.form.get('name')
    image = request.file('image')
    if image and allowed_file(image):
        filename = secure_filename(file.filename)
        file.save(os.path.join(UPLOAD_IMAGE_FOLDER, filename))
        return jsonify({"error": 0, "msg": "upload success"})
    else:
        return jsonify({"error": 1, "msg": "upload failure"})
    

@app.route("/getimage/<img_name>", methods=['GET'])
def getimage(img_name):
    if os.path.isfile(os.path.join('upload', img_name)):
        return send_from_directory('upload', img_name, as_attachment=True)
    abort(404)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)