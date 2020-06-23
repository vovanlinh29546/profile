#include <ArduinoJson.h>



#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

// Set these to run example.
#define FIREBASE_HOST "fbwitharduino.firebaseio.com"
#define FIREBASE_AUTH "NfFZpvJdKEBkRBHYOttQh3ff7gMqtvxqfU6ceegl"
#define WIFI_SSID "Fpoly-Students"
#define WIFI_PASSWORD "fpolyhcm@123"

#include "DHT.h"            
 
const int DHTPIN = 4;       //Đọc dữ liệu từ DHT11 ở chân 2 trên mạch Arduino
const int DHTTYPE = DHT11;  //Khai báo loại cảm biến, có 2 loại là DHT11 và DHT22
 
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);

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
    dht.begin();
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

int n = 0;

void loop() {
  // set value
  float h = dht.readHumidity();    //Đọc độ ẩm
  float t = dht.readTemperature(); //Đọc nhiệt độ
  Firebase.setFloat("nhietdo", t);
  
  Firebase.setFloat("doam", h);

 delay(1000);
 
}
