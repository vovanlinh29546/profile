# -*- coding: utf-8 -*-
"""
Created on Sat Jul 18 16:51:51 2020

@author: DELL
"""

import numpy as np
import cv2
face_casceda=cv2.CascadeClassifier("haarcascade_frontalface_default.xml")
eye=cv2.CascadeClassifier("haarcascade_eye_tree_eyeglasses.xml")
cap=cv2.VideoCapture(0)
Y=0
while(True):
    red, frame = cap.read()
    gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    #faces=face_casceda.detectMultiScale(gray)
    faces = face_casceda.detectMultiScale(gray) 
    eyes=eye.detectMultiScale(gray)
    for (x,y,w,h) in faces:
        cv2.rectangle(frame, (x,y), (x+w,y+h), (0,255,0),2)
        
        roi_color=frame[y:y +h,x:x+w]

    
    cv2.imshow('DETECTING', frame)
    if cv2.waitKey(1) &0xFF==ord('q'):
        break
cap.release()
cv2.destroyAllWindows()