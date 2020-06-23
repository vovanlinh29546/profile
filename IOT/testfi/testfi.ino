#include <ArduinoJson.h>



#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

// Set these to run example.
#define FIREBASE_HOST "blockdoor-59a3c.firebaseio.com"
#define FIREBASE_AUTH "PUTAYTXbf3elG7t4jRjzDioeiUod0UWwgBZuMvve"
#define WIFI_SSID "Fpoly-Students"
#define WIFI_PASSWORD "fpolyhcm@123"
#define relayPin 4 
int relay=0;
void setup() {
  Serial.begin(9600);
pinMode(relayPin, OUTPUT);
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
}



void loop() {
 relay=Firebase.getInt("Equiqment/Door/testlinh");
 Serial.print("status: ");
 Serial.println(Firebase.getInt("qq"));
// Firebase.setInt("Equiqment/Door/Status", 1);
 if (Firebase.failed()) {
      Serial.print("setting /message failed:");
      Serial.println(Firebase.error());  
      return;
  }
  
 
  if(relay == 1){
    digitalWrite(relayPin, LOW);
     delay(3000);
    Firebase.setInt("Equiqment/Door/testlinh", 0);
    relay=Firebase.getInt("Equiqment/Door/testlinh");
    //Serial.println(relay);
    }
    else if (relay==0){
      
       digitalWrite(relayPin, HIGH);
      }
  delay(1000);
}
