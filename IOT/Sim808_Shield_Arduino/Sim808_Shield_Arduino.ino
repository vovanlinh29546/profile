#include "SoftwareSerial.h"
/*
Tac gia: To ky thuat Hshop.vn
link san pham: http://hshop.vn/products/mach-simgsmgprs-gps-bluetooth-sim808

-----So do day:----

sim808(780k)					arduino (uno)
Vcc(5v, >1A)
GND
Tx						3
Rx						2
link san pham: http://hshop.vn/products/mach-simgsmgprs-gps-bluetooth-sim808


sim900, sim800L, sim800DS		arduino (uno)
Vcc(5v: sim 900,800DS, >1A)	,	4.1V: sim800L(do chua co diod)
GND
Tx						2
Rx						3
link san pham:
	http://hshop.vn/products/module-sim800l
	http://hshop.vn/products/mach-gsm-gprs-sim900a-mini-v2
	http://hshop.vn/products/mach-gsmgprs-sim900a
______________________________________________________________________________________



Luu y:
	vi chuong trinh bat dau khi vua cap dien,
	luc nay sim808 co the chua khoi dong xong (led xanh la chop cham la khoi dong xong)
	nen khoan hay cam TX, RX vao Arduino.
	khi nao led xanh la tren sim808 chop cham lai, cac ban ket noi 2 day tin hieu lai roi
	nhan Reset Arduino de bat dau lai chuong trinh.
	
Chuc cac ban thanh cong!
*/

SoftwareSerial sim808(8,9);		

int _timeout;
String _buffer;
String SDT="+84898931866";

void setup() {
  // put your setup code here, to run once:
 
//  // Test call
//  at("AT",1000);
//  at("ATD"+SDT+";",15000);
//  at("ATH",1000);
//  
//  //test sms
//  at("AT",1000);
//  at("AT+CMGF=1",1000);
//  at("AT+CSCS=\"GSM\"",1000);
//  at("AT+CMGS=\"" + SDT+"\"",2000);
//  at("Hshop test sim",1000);
//  sim808.write(26);     // ctlrZ

  delay(7000); //delay for 7 seconds to make sure the modules get the signal
  Serial.begin(9600);
  _buffer.reserve(50);
  Serial.println("System Started...");
 sim808.begin(9600);
  delay(1000);
  Serial.println("Type s to send an SMS, r to receive an SMS, and c to make a call");
}

void loop() {
  // put your main code here, to run repeatedly:
    if (Serial.available() > 0)
    switch (Serial.read())
    {
      case 's':
        SendMessage();
        break;
      case 'r':
      //  RecieveMessage();
        break;
      case 'c':
      //  callNumber();
        break;
    }
  if (sim808.available() > 0)
    Serial.write(sim808.read());

}
void at(String _atcm,unsigned long _dl){
  sim808.print(_atcm+"\r\n");
  delay(_dl);
}
void SendMessage()
{
  //Serial.println ("Sending Message");
//  sim808.println("AT+CMGF=1");    //Sets the GSM Module in Text Mode
//  delay(1000);
//  //Serial.println ("Set SMS Number");
//  sim808.println("AT+CMGS=\"" + SDT + "\"\r"); //Mobile phone number to send message
//  delay(1000);
//  String SMS = " HOW ARE Y0U";
//  sim808.println(SMS);
//  delay(100);
//  sim808.println((char)26);// ASCII code of CTRL+Z
//  delay(1000);

  //test sms
  at("AT",1000);
  at("AT+CMGF=1",1000);
  at("AT+CSCS=\"GSM\"",1000);
  at("AT+CMGS=\"" + SDT+"\"",2000);
  at("Hshop test sim",1000);
  sim808.write(26);     // ctlrZ
    _buffer = _readSerial();
}
String _readSerial() {
  _timeout = 0;
  while  (!sim808.available() && _timeout < 12000  )
  {
    delay(13);
    _timeout++;
  }
  if (sim808.available()) {
    return sim808.readString();
  }
}
