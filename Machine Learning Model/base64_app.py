from werkzeug.wrappers import Request, Response
from flask import Flask, request, jsonify
from load_model import getModel, getClass, preprocessing
import numpy as np
import uuid
import os
import base64


app = Flask(__name__)

@app.route('/Image', methods = ['GET'])
def getPrediction():
  if request.method == 'GET':
    imgstring = request.json['image']
    if len(imgstring) != 0:
      imgdata = base64.b64decode(imgstring)
      filename = 'predict_image.jpeg'
      with open(filename, 'wb') as f:
        f.write(imgdata)
      returnClass = preprocessing(filename)
      os.remove(filename)
      return jsonify({"prediction": returnClass}), 200
    else:
      return jsonify({"Error": "Please select the Image"}), 400

if __name__ == '__main__':
  from werkzeug.serving import run_simple
  run_simple('localhost', 9000, app)  