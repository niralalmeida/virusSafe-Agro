import cv2
import numpy as np
import glob
import random
from load_model import predict_tomato_good_or_bad
import os

# Load Yolo
net = cv2.dnn.readNet("./yolov3_tomato_model/yolov3_training_final_v2.weights", "./yolov3_tomato_model/yolov3_testing.cfg")

# Name custom object
classes = ["tomato"]

def yolov3_tomato_detection(images_path):
    # Images path
    # images_path = glob.glob("sample/*.jpg")
    layer_names = net.getLayerNames()
    output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
    colors = np.random.uniform(0, 255, size=(len(classes), 3))

    # Insert here the path of your images
    random.shuffle(images_path)
    # loop through all the images
    counter = 1
    count_ind_tomato = 0
    output_list = []
    good_count = 0
    bad_count = 0
    text_scale = 2
    for img_path in images_path:
        # Loading image
        img = cv2.imread(img_path)
        # img = cv2.resize(img, None, fx=0.4, fy=0.4)
        height, width, channels = img.shape

        # Detecting objects
        blob = cv2.dnn.blobFromImage(img,1/255 , (416, 416), (0, 0, 0), True, crop=False)
        net.setInput(blob)
        outs = net.forward(output_layers)
        # Showing informations on the screen
        class_ids = []
        confidences = []
        boxes = []
        for out in outs:
            for detection in out:
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                if confidence > 0.3:
                    # Object detected
                    # print(class_id)
                    center_x = int(detection[0] * width)
                    center_y = int(detection[1] * height)
                    w = int(detection[2] * width)
                    h = int(detection[3] * height)

                    # Rectangle coordinates
                    x = int(center_x - w / 2)
                    y = int(center_y - h / 2)

                    boxes.append([x, y, w, h])
                    confidences.append(float(confidence))
                    class_ids.append(class_id)

        indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)
        # print(indexes)
        font = cv2.FONT_HERSHEY_SCRIPT_COMPLEX
        print(np.array(boxes).shape)
        if len(boxes) > 15:
            text_scale = 1
        orignal_image = img
        copy_image = img
        color_rect = (0, 0, 0) #(0, 0, 0)
        color_text = (0, 0, 0) #(255, 255, 255)
        #(42,18,104)
        for i in range(len(boxes)):
            if i in indexes:
                x, y, w, h = boxes[i]
                label = str(classes[class_ids[i]])
                #color_rect = colors[class_ids[i]]
                #color_text = colors[class_ids[i]]
                copy_image = orignal_image
                if x < 0 :
                    x = 0
                if y < 0:
                    y = 0
                crop_img = copy_image[y:y+h, x:x+w]
                if len(crop_img) != 0 or crop_img != []:
                    filename = "./output/cropped/"+str(count_ind_tomato)+".jpg"
                    cv2.imwrite(filename, crop_img)
                    text_label = predict_tomato_good_or_bad(filename)
                    if text_label == "Good":
                        good_count = good_count + 1
                    else:
                        bad_count = bad_count + 1
                    os.remove(filename)
                    count_ind_tomato = count_ind_tomato + 1
                cv2.rectangle(img, (x, y), (x + w, y + h), color_rect, 2)
                cv2.putText(img, text_label, (x + 15, y + 60), font, text_scale, color_text, 2)
        counter = counter + 1
        cv2.imwrite("./output/"+str("final")+".jpg", img)
        cv2.imwrite("./output/"+str("predict")+".jpg", img)
        if count_ind_tomato == 0:
            temp_good_percentage = 0
            temp_bad_percentage = 0
        else:
            temp_good_percentage = round((good_count / count_ind_tomato) * 100)
            temp_bad_percentage = round((bad_count / count_ind_tomato) * 100)
        output_list.append({
            "image": img,
            "count": count_ind_tomato,
            "good_count": good_count,
            "bad_count": bad_count,
            "good_percentage": temp_good_percentage,
            "bad_percentage": temp_bad_percentage
        })
    return output_list
        # cv2.imshow("Image", img)
        # key = cv2.waitKey(0)

    # cv2.destroyAllWindows()
