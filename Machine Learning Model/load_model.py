# Libraries
from tensorflow.keras.models import Sequential
from tensorflow.keras import layers
from tensorflow import keras
import tensorflow as tf
import numpy as np

# Function load model arch and weights
def getModel():
    # Architecture
    model = Sequential([tf.keras.Input(shape=(256,256,3)) , 
        layers.Conv2D(16, 3, padding='same', activation='relu'),
        layers.MaxPooling2D(),
        layers.Conv2D(32, 3, padding='same', activation='relu'),
        layers.MaxPooling2D(),
        layers.Conv2D(64, 3, padding='same', activation='relu'),
        layers.MaxPooling2D(),
        layers.Dropout(0.2),
        layers.Flatten(),
        layers.Dense(128, activation='relu'),
        layers.Dense(10, activation = 'softmax')
    ])
    # loading saved weights
    model.load_weights('final_model.h5')
    # returning model
    return model

# Function return label for index
def getClass(index):
    # labels
    classList = {
        0: 'Bacterial_spot',
        1: 'Early_blight',
        2: 'Late_blight',
        3: 'Leaf_Mold',
        4: 'Septoria_leaf_spot',
        5: 'Spider_mites Two-spotted_spider_mite',
        6: 'Target_Spot',
        7: 'Tomato_Yellow_Leaf_Curl_Virus',
        8: 'Tomato_mosaic_virus',
        9: 'healthy'
    }
    # returning label for perticular class index
    return classList[index]

# Function called by getPrediction for preprocessing
def preprocessing(file):
    # Creating architecture, loading saved weights and returning model
    model = getModel() 
    # loading the image on size 256x256, and create PIL Image instance
    img = keras.preprocessing.image.load_img(file, target_size=(256,256)) 
    # Converts a PIL Image instance to a Numpy array.
    img_array = keras.preprocessing.image.img_to_array(img) 
    # Expanding dimention by 1 for prediction
    img_array = tf.expand_dims(img_array/255, 0) 
    # predict the index for class, call getclass function and return class
    return getClass(np.argmax(model.predict(img_array,steps=1))) 