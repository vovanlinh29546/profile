# -*- coding: utf-8 -*-
"""
Created on Tue Jul 21 18:54:25 2020

@author: DELL
"""


from gtts import gTTS
import os
import speech_recognition as sr

import time
import random
import datetime
import warnings
import calendar


warnings.filterwarnings('ignore')
def talk(audio):
    "speaks audio passed as argument"

    print(audio)
    for line in audio.splitlines():
        text_to_speech = gTTS(text=audio, lang='vi')
    # Save the converted audio to a file    
        text_to_speech.save('assistant_response.mp3')
    # Play the converted file
        os.system('start assistant_response.mp3')
       

def getDate():
    
    now = datetime.datetime.now()
    my_date = datetime.datetime.today()
    weekday = calendar.day_name[my_date.weekday()]# e.g. Monday
    monthNum = now.month
    dayNum = now.day
    month_names = ['tháng 1', 'tháng 2', 'tháng 3', 'tháng 4', 'tháng 5',
       'tháng 6', 'tháng 7', 'tháng 8', 'tháng 9', 'tháng 10', 'tháng 11',   
       'tháng 12']
    ordinalNumbers = ['1st', '2nd', '3rd', '4th', '5th', '6th', 
                      '7th', '8th', '9th', '10th', '11th', '12th',                      
                      '13th', '14th', '15th', '16th', '17th', 
                      '18th', '19th', '20th', '21st', '22nd', 
                      '23rd', '24th', '25th', '26th', '27th', 
                      '28th', '29th', '30th', '31st']
   
    return 'hôm nay ' + weekday + ' ' + month_names[monthNum - 1] + ' ngày ' + ordinalNumbers[dayNum - 1] + '.'


def myCommand():
    "listens for commands"
    #Initialize the recognizer
    #The primary purpose of a Recognizer instance is, of course, to recognize speech. 
    r = sr.Recognizer()

    with sr.Microphone() as source:
        print('TARS is Ready...')
        r.pause_threshold = 1
        r.adjust_for_ambient_noise(source, duration=2)
        audio = r.listen(source)
    try:
        command = r.recognize_google(audio,language='vi-VN').lower()
        print('You said: ' + command + '\n')
        #time.sleep(1)

    #loop back to continue to listen for commands if unrecognizable speech is received
    except sr.UnknownValueError:
        print('Your last command couldn\'t be heard')
        
        command = myCommand();

    return command

def tars(command):
    errors=[
        "I don't know what you mean",
        "Excuse me?",
        "Can you repeat it please?",
    ]
    "if statements for executing commands"

    # Search on Google
    
    response = '' #Empty response string
   

    if 'xin chào' in command:
        #talk('Hello! I am TARS. How can I help you?')
        response='Xin chào!, tôi có thể giúp gì cho bạn'  
        #time.sleep(1)
    elif 'ngày' in command:
        get_date = getDate()
        response = response + ' ' + get_date
    
        print(response)
    elif 'bạn là ai' in command:
        #talk('I am one of four former U.S. Marine Corps tactical robots')
        response='tôi là rô bót đến từ việt nam'
        #time.sleep(1)
    elif 'giờ' in command:
        t = time.localtime()
        current_time = time.strftime("%H:%M", t)
        response=current_time
        
    else:
        error = random.choice(errors)
        response=error
        time.sleep(1)
    talk(response)

talk('bắt đầu nào')
#loop to continue executing multiple commands
while True:
    #time.sleep(2)
    tars(myCommand())
