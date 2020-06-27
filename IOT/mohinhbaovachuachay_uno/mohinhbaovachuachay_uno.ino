#include <SoftwareSerial.h>
//SoftwareSerial sim(10, 11);

int _timeout;
String _buffer;
String number = "+84336682601"; //;.
//khai bao module
//int sensorValue = A0;
int loa = 7;
int sensorAir;
void setup() {
  // put your setup code here, to run once:
      pinMode(2, INPUT_PULLUP);
  pinMode(13, OUTPUT);
  delay(7000); //delay for 7 seconds to make sure the modules get the signal
  Serial.begin(9600);
  _buffer.reserve(50);
  Serial.println("System Started...");
//  sim.begin(9600);
  delay(1000);

}

void loop() {
  // put your main code here, to run repeatedly:
  int sensorVal = digitalRead(2);
//  sensorValue = analogRead(A0);
   
  Serial.println(sensorVal);
//  Serial.println(sensorValue);
  if (sensorVal == HIGH) {
    digitalWrite(13, LOW);

  } else if( sensorVal == LOW ){
    digitalWrite(13, HIGH);
    tone(loa,1000,1000);
//SendMessage();
    delay(1000);

  }
}
//void SendMessage()
//{
//  //Serial.println ("Sending Message");
//  sim.println("AT+CMGF=1");    //Sets the GSM Module in Text Mode
//  delay(1000);
//  //Serial.println ("Set SMS Number");
//  sim.println("AT+CMGS=\"" + number + "\"\r"); //Mobile phone number to send message
//  delay(1000);
//  String SMS = " CHAY NHA ";
//  sim.println(SMS);
//  delay(100);
//  sim.println((char)26);// ASCII code of CTRL+Z
//  delay(1000);
//  _buffer = _readSerial();
//}
//String _readSerial() {
//  _timeout = 0;
//  while  (!sim.available() && _timeout < 12000  )
//  {
//    delay(13);
//    _timeout++;
//  }
//  if (sim.available()) {
//    return sim.readString();
//  }
//}
