
"""
Created on Tue Jul 21 18:54:25 2020

@author: DELL
"""
import pyttsx3
from pyowm import OWM
from gtts import gTTS
import os
import speech_recognition as sr
from pygame import mixer
import random
import datetime
import warnings
import calendar
#led
import RPi.GPIO as GPIO
from time import sleep

warnings.filterwarnings('ignore')
path = os.getenv('PATH')
GPIO.setmode(GPIO.BCM)
GPIO.setup(8, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(21, GPIO.OUT)
#p = GPIO.PWM(servoPIN, 50) # GPIO 11 for PWM with 50Hz
#p.start(0) # Initialization
#p.ChangeDutyCycle(0)
def talk(audio):
    "speaks audio passed as argument"

    print(audio)
    for line in audio.splitlines():
        text_to_speech = gTTS(text=audio, lang='vi')
    # Save the converted audio to a file
        text_to_speech.save('/home/pi/Desktop/bot/audiovirtual.mp3')
    # Play the converted file
        mixer.init()
        mixer.music.load("/home/pi/Desktop/bot/audiovirtual.mp3")
        mixer.music.play()

       

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

def thoitiet():
    owm = OWM('3b88b3dd0f5bf2ab4108d1e4f367b034')  # You MUST provide a valid API key

        # Search for current weather in London (Great Britain)
    mgr = owm.weather_manager()
    observation = mgr.weather_at_place('Ho Chi Minh City, VN')
    weather = observation.weather
    temper =weather.temperature('celsius')['temp']
    t2=int(temper)
    t3=str(t2)
    doam=weather.humidity
    t4=int(doam)
    t5=str(t4)
    ai="nhiệt độ hiện tại của thành phố là"+t3+"độ C độ ẩm là"+t5+"%"
    return ai
def xinchao():
    now = datetime.datetime.now()
    gio=now.hour
    loichao=""
    if now.hour < 12:
        loichao="Chào buổi sáng bạn. Chúc bạn một ngày tốt lành."
    elif 12 <= now.hour < 18:
        loichao="Chào buổi chiều bạn. Bạn đã dự định gì cho chiều nay chưa."
    else:
        loichao="Chào buổi tối bạn. Chúc bạn ngủ ngon."
    return loichao
def myCommand():
    "listens for commands"
    #Initialize the recognizer
    #The primary purpose of a Recognizer instance is, of course, to recognize speech. 
    r = sr.Recognizer()

    with sr.Microphone() as source:
        print('TARS đang bắt đầu...')
        #r.pause_threshold = 1
        #wait for a second to let the recognizer adjust the  
        #energy threshold based on the surrounding noise level 
        r.adjust_for_ambient_noise(source, duration=2)
        #listens for the user's input
        audio = r.listen(source)
        print('đang thực hiện...')

    try:
        command = r.recognize_google(audio,language='vi-VN').lower()
        print('Bạn: ' + command + '\n')
        #time.sleep(1)

    #loop back to continue to listen for commands if unrecognizable speech is received
    except sr.UnknownValueError:
        print('Your last command couldn\'t be heard')
        
        command = myCommand();

    return command


def tars(command):
    errors=[
        "Tôi không hiểu bạn nói gì",
        "Excuse me?",
        "Bạn có thể nói lại không?",
    ]
    "if statements for executing commands"

    # Search on Google
    
    response = '' #Empty response string
   

    if 'xin chào' in command:
        getchao=xinchao()
        response=getchao
        
        
    elif 'ngày' in command:
        get_date = getDate()
        response = response + ' ' + get_date
        
    
    elif 'bạn là ai' in command:
        #talk('I am one of four former U.S. Marine Corps tactical robots')
        response='tôi là rô bót đến từ việt nam'
        #time.sleep(1)
        
        
    elif 'mở đèn' in command:
        #talk('I am one of four former U.S. Marine Corps tactical robots')
        GPIO.output(8, True)
        response='oke đèn đã được mở'
        
        
        #time.sleep(1)
    elif 'mở quạt' in command:
        GPIO.output(21,GPIO.HIGH)
        response='oke quạt đã được mở'
        
        
    elif 'tắt quạt' in command:
        GPIO.output(21,GPIO.LOW)
        response='oke quạt đã được tắt'
        
        
        
    elif 'tắt đèn' in command:
        #talk('I am one of four former U.S. Marine Corps tactical robots')
        GPIO.output(8, False)
        response='oke đèn đã được tắt'
        

    elif 'giờ' in command:
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
        
        
    elif 'thời tiết' in command:
        response=thoitiet()
        
    else:
        error = random.choice(errors)
        response=error
        
      
    talk(response) 

talk('bắt đầu nào')
#loop to continue executing multiple commands
while True:
    #time.sleep(2)
    tars(myCommand())
