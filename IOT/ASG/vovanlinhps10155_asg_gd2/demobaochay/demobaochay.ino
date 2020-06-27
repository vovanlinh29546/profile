#define BLYNK_PRINT Serial

#include <FirebaseArduino.h>
#include <ESP8266WiFi.h>
#include <BlynkSimpleEsp8266.h>
#include <ArduinoJson.h>
// You should get Auth Token in the Blynk App.
// Go to the Project Settings (nut icon).
char auth[] = "al_-NvZAZvI5gkAmz6NCJe3lF02OIiR1";

// Your WiFi credentials.
// Set password to "" for open networks.
char ssid[] = "linhvv";
char pass[] = "linhvv10155";
#define WIFI_SSID "linhvv"
#define WIFI_PASSWORD "linhvv10155"
#define FIREBASE_HOST "fbwitharduino.firebaseio.com"
#define FIREBASE_AUTH "NfFZpvJdKEBkRBHYOttQh3ff7gMqtvxqfU6ceegl"

#define PATH "/control"
#define PATHs "/control/Status"
#define PATHclkk "/control/CLKK"
//khai bao module
#define relay  16
#define lua  5
#define loa  0
void setup()
{
  pinMode(relay, OUTPUT);
  // Debug console
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
  //delay(500);
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  //delay(500);
  Serial.print("connected firebase ");
  Blynk.begin(auth, ssid, pass);
 // delay(500);
  Serial.println("System Started...");
//  Firebase.stream(PATH);
  // You can also specify server:
  //Blynk.begin(auth, ssid, pass, "blynk-cloud.com", 80);
  //Blynk.begin(auth, ssid, pass, IPAddress(192,168,1,100), 8080);
  digitalWrite(relay, LOW);
}

void loop()
{
  Blynk.run();
  // You can inject your own code or combine it with other sketches.
  // Check other examples on how to communicate with Blynk. Remember
  // to avoid delay() function!
    int sensorVal = digitalRead(lua);
  Serial.println(sensorVal);
    if (sensorVal == HIGH  ) {
   // digitalWrite(relay, LOW);


  } else if( sensorVal == LOW){
    digitalWrite(relay, HIGH);
 Firebase.setString(PATHs, "ON");
  tone(loa,1000,1000);
  delay(3000);
 digitalWrite(relay, LOW);
  }
  
  Firebase.setString(PATHs, "OFF");
 // Firebase.stream(PATH);
}
