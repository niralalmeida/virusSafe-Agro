# Android App Project - VirusSafe Agro
**VirusSafe** Afro is an Android App for learning and checking the common tomato viruses in Victoria.
***
## Project Mahara Page Link:  
[![Mahara page](https://mahara.org/artefact/file/download.php?file=432674&view=133484&embedded=1&text=575322)](https://mahara.infotech.monash.edu.au/mahara/view/view.php?id=49981)
***
## Brief Description

***
## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/hoyyang/virusSafe-Agro.git
```
***
## Generating signed APK
From Android Studio:
1. **Build** menu
2. **Generate Signed APK...**
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

***
## Architecture
### Whole System Architecture diagram
![System Architecture](https://raw.githubusercontent.com/hoyyang/virusSafe-Agro/master/design%20files/TA24-system%20architecture.png?token=ANAMHQUP7GHZC2FMDCCHMU27LN3EKe)
The whole distributed system architecture for this Android App project is a **client-server architecture**.  The client endpoint, the App in an Android phone, holds all views and logic for handling data of displaying.  In this distributed system, there are several servers for holding persistent data and resources or calculating model.  And the client and servers deliver data by open API created by ourselves.  We build this application by using the following web services:
1. **Machine Learning Model** - deployed on AWS with EC2
2. **MySql Database** - deployed on AWS with RDS (APIs are built by Lambda function and API Gateway in AWS)
3. other **Web Service**

### Android App Architecture diagram
![App Architecture](https://raw.githubusercontent.com/hoyyang/virusSafe-Agro/master/design%20files/App%20-%20android%20system%20structure.png?token=ANAMHQRGXKOOONDKS4HQTTC7LOLLG)
Within the client endpoint(Android App),  there are different widgets for different pages and functions.  For our project, there is **one activity**(current version) holding several **fragments**.  One fragment holds one page.  And each fragment has a **ViewModel**, holding **live data**, mapping with it individually, which is for controlling the data interact with the remote server or other data storage tool.  The interaction way for ViewModel and remote servers is **API**.

***
## Technology
### Image Classification
One of the functionalities of our application is to provide novice farmers with the capability to check the infection in tomato plants. For this purpose, we have built an image classification model using the following technology stack.
- Tensorflow
- Keras
- Used Convolutional Neural Network Architecture
- Flask Web Application Framework to create a flask application of model so that it can be deployed on AWS
- AWS EC2 for model deployment
##### Model Building Process: 
There are 3 key phases of Model building mentioned below:
1. **First**, the model is trained using Keras and Tensorflow deep learning framework and using CNN architecture.
2. **Second**, Flask has been used to create Restful API which can be deployed on cloud.
3. **Third**, flask application is deployed on AWS EC2.
##### Model Accuracy: 
Model is currently approximately **89% accurate**.
##### Dataset:
Model is trained on 10,000 images belonging to following **10 classes**:
- Tomato mosaic virus
- Target Spot
- Bacterial spot
- Tomato Yellow Leaf Curl Virus
- Late blight
- Leaf Mold
- Early blight
- Spider Mites Two-spotted 
- Tomato healthy
- Septoria spot

***
# About Our Team 
### Team No.
TA24
### Team members
|Name|Email|Major
|---|---|---|
|Ranison Modgil|rmod0001@student.monash.edu|Data Science|
|Yao Chen (Cindy)|yche0193@student.monash.edu|Network Security|
|Niral Almeida|nalm0004@student.monash.edu|Business Information System|
|Haoyu Yang (Hoy)|hyan0050@student.monash.edu|Information Technology|
***
