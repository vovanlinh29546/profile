# -*- coding: utf-8 -*-
"""
Created on Tue Jul 21 12:54:52 2020

@author: vovanlinh
"""


import speech_recognition as sr
import os
from gtts import gTTS
import datetime
import warnings
import calendar
import random
# Ignore any warning messages
warnings.filterwarnings('ignore')
# Record audio and return it as a string
def recordAudio():    
    # Record the audio
    r = sr.Recognizer()
    with sr.Microphone() as source:  
       print('Say something!')
       audio = r.listen(source)
    
    # Speech recognition using Google's Speech Recognition
    data = ''
    try:
        data = r.recognize_google(audio)
        print('You said: ' + data)
    except sr.UnknownValueError:
        print('Google Speech Recognition could not understand')
    except sr.RequestError as e:
        print('Request error from Google Speech Recognition')
    return data

# Function to get the virtual assistant response
def assistantResponse(text):
    print(text)
    # Convert the text to speech
    myobj = gTTS(text=text, lang='en-uk')
    
    # Save the converted audio to a file    
    myobj.save('assistant_response.mp3')
    # Play the converted file
    os.system('start assistant_response.mp3')

# A function to check for wake word(s)
def wakeWord(text):
    WAKE_WORDS = ['hey', 'okay'] 
    text = text.lower()  # Convert the text to all lower case words
  # Check to see if the users command/text contains a wake word    
    for phrase in WAKE_WORDS:
        if phrase in text:
            return True
  # If the wake word was not found return false
    return False
def getDate():
    
    now = datetime.datetime.now()
    my_date = datetime.datetime.today()
    weekday = calendar.day_name[my_date.weekday()]# e.g. Monday
    monthNum = now.month
    dayNum = now.day
    month_names = ['January', 'February', 'March', 'April', 'May',
       'June', 'July', 'August', 'September', 'October', 'November',   
       'December']
    ordinalNumbers = ['1st', '2nd', '3rd', '4th', '5th', '6th', 
                      '7th', '8th', '9th', '10th', '11th', '12th',                      
                      '13th', '14th', '15th', '16th', '17th', 
                      '18th', '19th', '20th', '21st', '22nd', 
                      '23rd', '24th', '25th', '26th', '27th', 
                      '28th', '29th', '30th', '31st']
   
    return 'Today is ' + weekday + ' ' + month_names[monthNum - 1] + ' the ' + ordinalNumbers[dayNum - 1] + '.'

# Function to return a random greeting response
def greeting(text):
    # Greeting Inputs
    GREETING_INPUTS = ['hi', 'hey', 'hola', 'greetings', 'wassup', 'hello']
     # Greeting Response back to the user
    GREETING_RESPONSES = ['howdy', 'whats good', 'hello', 'hey there']
     # If the users input is a greeting, then return random response
    for word in text.split():
        if word.lower() in GREETING_INPUTS:
            return random.choice(GREETING_RESPONSES) + '.'
    # If no greeting was detected then return an empty string
    return ''
# Function to get a person first and last name

while True:
    # Record the audio
    text = recordAudio()
    response = '' #Empty response string
    
    # Checking for the wake word/phrase
    #get_date = getDate()
    #print(get_date)
    if (wakeWord(text) == True):
         # Check for greetings by the user
         #response = response + greeting(text)
         # Check to see if the user said date
         if 'date' in text:
            get_date = getDate()
            response = response + ' ' + get_date
         elif 'hello' in text:
            get_date = getDate()
            response = response + ' ' + get_date
         # Check to see if the user said time
         elif'time' in text:
            now = datetime.datetime.now()
            meridiem = ''
            if now.hour >= 12:
                meridiem = 'p.m' #Post Meridiem (PM)
                hour = now.hour - 12
            else:
                meridiem = 'a.m'#Ante Meridiem (AM)
                hour = now.hour
           # Convert minute into a proper string
            if now.minute < 10:
                minute = '0'+str(now.minute)
            else:
                minute = str(now.minute)
                response = response + ' '+ 'It is '+ str(hour)+ ':'+minute+' '+meridiem+' .'
    
    # Assistant Audio Response
    #assistantResponse(response)
    print(response)
                