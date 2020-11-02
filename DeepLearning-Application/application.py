# libraries
from flask import Flask, request, jsonify, make_response # Flask libraries
from load_model import preprocessing, predict_binary, predict_tomato_leaf_or_not, predict_tomato_or_not # Funtion to invoke model
from yolov3 import yolov3_tomato_detection
from aws_s3 import upload_to_aws
import base64 # 
import os # lib for interacting with the operating system
from flask_restful import Resource, Api
import json
import random


application= app = Flask(__name__)
api=Api(application)

class GetPrediction(Resource):
    def get(self):
        return make_response(jsonify({"about": "hello world"}), 200)
    def post(self):

        json_data=request.get_json(force=True)
        imgstring = json_data['image'] 
        # checking whether request body contains data
        if len(imgstring) != 0: 
            # Converting base64 into byte
            imgdata = base64.b64decode(imgstring) 
            filename = 'predict_image.jpeg' 
            with open(filename, 'wb') as f: 
                # Creating Image out of byte
                f.write(imgdata) 

            class_binary = predict_binary(filename)
            if class_binary == 'Leaves':
                tomato_leaf_or_not = predict_tomato_leaf_or_not(filename)
                if tomato_leaf_or_not == 'Tomato_Leaves':
                    # calling function for prediction
                    returnClass = preprocessing(filename) 
                    # removing generated image
                    os.remove(filename) 
                    # sending response to app with status code
                    return make_response(jsonify({"prediction": returnClass}), 200)
                else:
                    # removing generated image
                    os.remove(filename)
                    # sending response to app with status code
                    return make_response(jsonify({"Respone": "Please select the Tomato Leaves Image"}), 400)
            else:
                # removing generated image
                os.remove(filename) 
                # sending response to app with status code
                return make_response(jsonify({"Respone": "Please select the Leaves Image"}), 400)
        else:
            # If not contained data sending error stating 'Select the Image'
            return make_response(jsonify({"Error": "Please select the Image"}), 400)

class GetObjectPrediction(Resource):
    def get(self):
        return make_response(jsonify({"about": "hello world"}), 200)
    def post(self):
        json_data=request.get_json(force=True)
        imgstring = json_data['image'] 
        # checking whether request body contains data
        if len(imgstring) != 0: 
            # Converting base64 into byte
            imgdata = base64.b64decode(imgstring) 
            filename = 'predict_image.jpg' 
            with open(filename, 'wb') as f: 
                # Creating Image out of byte
                f.write(imgdata) 
            # call the function to detect tomato

            # tomato_or_not = predict_tomato_or_not(filename)
            # if tomato_or_not == "Tomato":
            output_list = yolov3_tomato_detection([filename])
            # generate 6 digit random number
            n = random.randint(100000, 999999)
            # generate a unique name for a image to upload in AWS s3
            aws_file = "IMG"+str(n)+".jpg" 
            # call the function which upload the image in aws s3 bucket
            aws_file_path = upload_to_aws('./output/predict.jpg', 'tomato-virus-ranison', aws_file)
            # removing generated image
            os.remove(filename) 
            # sending response to app with status code
            return make_response(jsonify({ 
                "image": aws_file_path, 
                "count": output_list[0]["count"], 
                "good_count": output_list[0]["good_count"],
                "bad_count": output_list[0]["bad_count"],
                "good_percentage": output_list[0]["good_percentage"],
                "bad_percentage": output_list[0]["bad_percentage"]
                }), 200)
            # else:
            #     n = random.randint(100000, 999999)
            #     # generate a unique name for a image to upload in AWS s3
            #     aws_file = "IMG"+str(n)+".jpg" 
            #     # call the function which upload the image in aws s3 bucket
            #     aws_file_path = upload_to_aws(filename, 'tomato-virus-ranison', aws_file)
            #     # removing generated image
            #     os.remove(filename) 
            #     # sending response to app with status code
            #     return make_response(jsonify({ 
            #         "image": aws_file_path, 
            #         "count": 0, 
            #         "good_count": 0,
            #         "bad_count": 0,
            #         "good_percentage": 0,
            #         "bad_percentage": 0
            #         }), 400)

        else:
            # If not contained data sending error stating 'Select the Image'
            return make_response(jsonify({"Error": "Please select the Image"}), 400)


api.add_resource(GetPrediction, '/')
api.add_resource(GetObjectPrediction, '/object')

if __name__ == '__main__':
  # running application on host 'localhost' and port '9000'
  application.run(host='0.0.0.0', debug=True) 
