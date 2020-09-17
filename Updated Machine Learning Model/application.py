# libraries
from flask import Flask, request, jsonify, make_response # Flask libraries
from load_model import preprocessing, predict_binary # Funtion to invoke model
import base64 # 
import os # lib for interacting with the operating system
from flask_restful import Resource, Api

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
                return make_response(jsonify({"Respone": "Please select the Leaves Image"}), 400)
        else:
            # If not contained data sending error stating 'Select the Image'
            return make_response(jsonify({"Error": "Please select the Image"}), 400)


api.add_resource(GetPrediction, '/')

if __name__ == '__main__':
  # running application on host 'localhost' and port '9000'
  application.run(host='0.0.0.0', debug=True) 