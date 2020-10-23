import cv2
import numpy as np
import glob
import random


# Load Yolo
net = cv2.dnn.readNet("./yolov3_tomato_model/yolov3_training_final.weights", "./yolov3_tomato_model/yolov3_testing.cfg")

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
    count_ind_tomato = 1
    output_list = []
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
        font = cv2.FONT_HERSHEY_PLAIN
        count_tomatoes = len(boxes)
        orignal_image = img
        copy_image = img
        for i in range(len(boxes)):
            if i in indexes:
                x, y, w, h = boxes[i]
                label = str(classes[class_ids[i]])
                color = colors[class_ids[i]]
                copy_image = orignal_image
                crop_img = copy_image[y:y+h, x:x+w]
                if len(crop_img) != 0:
                    # cv2.imwrite("./output/cropped/"+str(count_ind_tomato)+".jpg", crop_img)
                    count_ind_tomato = count_ind_tomato + 1
                cv2.rectangle(img, (x, y), (x + w, y + h), color, 2)
                # cv2.putText(img, label, (x, y + 30), font, 2, color, 1)
        counter = counter + 1
        cv2.imwrite("./output/"+str("predict")+".jpg", img)
        output_list.append({
            "image": img,
            "count": count_ind_tomato-1
        })
    return output_list[0]["image"], output_list[0]["count"] 
        # cv2.imshow("Image", img)
        # key = cv2.waitKey(0)

    # cv2.destroyAllWindows()
