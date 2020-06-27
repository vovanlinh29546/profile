#include <SoftwareSerial.h>
#define BLYNK_PRINT Serial
//khai bao thu vien firebase vs wifi
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
//
#include <BlynkSimpleEsp8266.h>

//SoftwareSerial sim(10, 11);
BlynkTimer timer;
int _timeout;

//khai bao module
//#define loa  0
#define lua  5
#define relay  16
//#define khongkhi  2
//blynk
char auth[] = "al_-NvZAZvI5gkAmz6NCJe3lF02OIiR1";
// wifi with firebase
#define FIREBASE_HOST "fbwitharduino.firebaseio.com"
#define FIREBASE_AUTH "NfFZpvJdKEBkRBHYOttQh3ff7gMqtvxqfU6ceegl"
#define WIFI_SSID "Fpoly-Students"
#define WIFI_PASSWORD "fpolyhcm@123"
#define PATH "/control"
#define PATHs "/control/Status"
#define PATHclkk "/control/CLKK"
int sensorValue;
void setup() {
  // put your setup code here, to run once:
      pinMode(relay, OUTPUT);
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  delay(5000); //delay for 7 seconds to make sure the modules get the signal
  Serial.begin(9600);
 // _buffer.reserve(50);
  Serial.println("System Started...");
//  sim.begin(9600);
  Blynk.begin(auth, WIFI_SSID, WIFI_PASSWORD);
  Firebase.stream(PATH);
  //delay(1000);

}

void loop() {
  Blynk.run();
  // put your main code here, to run repeatedly:
  int sensorVal = digitalRead(lua);
 // sensorValue = analogRead(khongkhi);
  Serial.println(sensorVal);
 // delay(500);
 //  Serial.println(sensorValue);
 //   Firebase.setFloat(PATHs, sensorValue);
 
 //delay(1000);
  if (sensorVal == HIGH  ) {
    digitalWrite(relay, LOW);


  } else if( sensorVal == LOW){
    digitalWrite(relay, HIGH);
    delay(500);
 Firebase.setString(PATHs, "ON");
//  tone(loa,1000,1000);
 //   delay(500);

  }
  Firebase.setString(PATHs, "OFF");
}
