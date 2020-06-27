
#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include "DHT.h"
#include <FirebaseArduino.h>
#include <Ticker.h>
#include <OneWire.h>
#include <DNSServer.h>
#include <WiFiManager.h>
Ticker blinker, maybom;
#define DHTPIN 15 // D8

#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
#define TRIGGER_PIN 9 //SD2
float h;
float t;
float doamdat;
bool nhaovo, bomon = false;
// Set these to run example.
#define FIREBASE_HOST "pitfarmv1.firebaseio.com"
//p3fiOyJhb8uZXehJB34YqOd5NlDNiupqtlkofNoB
#define FIREBASE_AUTH ""
//#define WIFI_SSID "DDV"
//#define WIFI_PASSWORD "ddv@1234"
#define WIFI_SSID "THH-TTVH"
#define WIFI_PASSWORD "h415201718"
//#define WIFI_SSID "Dua thui"
//#define WIFI_PASSWORD "matkhauynhucu"
#define thiet_bi_1 5 //D1
#define thiet_bi_2 4 //D2
#define thiet_bi_3 15 //D5
#define thiet_bi_4 12 //D6
#define thiet_bi_5 16 //D0
#define PATH "/0969394936/control"
int Fthiet_bi_1 = 0;
int Fthiet_bi_2 = 0;
int Fthiet_bi_3 = 0;
int Fthiet_bi_4 = 0;
int Othiet_bi_1 = 0, Othiet_bi_2 = 0, Othiet_bi_3 = 0, Othiet_bi_4 = 0;
int streamTimeouts = 0;
OneWire  ds(13);  // on D7 (a 4.7K resistor is necessary)
WiFiManager wifiManager;
void setup() {
  pinMode(thiet_bi_1, OUTPUT);
  pinMode(thiet_bi_2, OUTPUT);
  pinMode(thiet_bi_3, OUTPUT);
  pinMode(thiet_bi_4, OUTPUT);
  pinMode(thiet_bi_5, OUTPUT);
  pinMode(13, INPUT);
  pinMode(15, INPUT);
  Serial.begin(115200);
  //Serial1.begin(9600);

  //pinMode(TRIGGER_PIN, INPUT);
  // connect to wifi.
  //WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  Serial.print("vai ca lo");
  //  if ( digitalRead(TRIGGER_PIN) == LOW ) {
  //    cauhinhwifi();
  //  }
  int count = 0;
  wifiManager.autoConnect();
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
    Serial.print(count);
    count++;
    if (count >= 6)
    {
      cauhinhwifi();
      count = 0;
      wifiManager.autoConnect();
      delay(1000);
    }
  }
  //UpdateCamBien();
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  //start streaming the data for the updating value
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.stream(PATH);
  blinker.attach(3550, nhietdo);
  maybom.attach(3, switchmb);
  Serial.print("attach");
  

}
void cauhinhwifi()
{

  //WiFiManager
  //Local intialization. Once its business is done, there is no need to keep it around


  //reset settings - for testing
  //wifiManager.resetSettings();

  //sets timeout until configuration portal gets turned off
  //useful to make it all retry or go to sleep
  //in seconds
  //wifiManager.setTimeout(120);

  //it starts an access point with the specified name
  //here  "AutoConnectAP"
  //and goes into a blocking loop awaiting configuration

  //WITHOUT THIS THE AP DOES NOT SEEM TO WORK PROPERLY WITH SDK 1.5 , update to at least 1.5.1
  //WiFi.mode(WIFI_STA);
  //wifiManager.startConfigPortal("LILOTECH-FARM", "ngusaonoi");
  if (!wifiManager.startConfigPortal("LILOTECH-FARM", "ngusaonoi")) {
    Serial.println("failed to connect and hit timeout");
    delay(3000);
    //reset and try again, or maybe put it to deep sleep
    ESP.reset();
    delay(5000);
    //if you get here you have connected to the WiFi
    //  Serial.println("connected...yeey :)");
  }
}
void switchmb()
{
  if (bomon)
  {
    bomon = false;
    digitalWrite(thiet_bi_5, LOW);
    maybom.attach(900, switchmb);
    Serial.println("tat");
  } else
  {
    bomon = true;
    digitalWrite(thiet_bi_5, HIGH);
    maybom.attach(300, switchmb);
    Serial.println("mo");
  }
}
void digitalWriteOver(int pin, int state)
{
  if (state == 0)
  {
    digitalWrite(pin, LOW);
  } else
  {
    digitalWrite(pin, HIGH);
  }
}
//////
void nhietdo() {
  nhaovo = true;
}
//////////////////////////////////
void loop() {

  //Serial.println("loop");
  //blink();
  //check if streaming failed and try to restart
  if (nhaovo)
  {
    getCamBien();
    //  delay(300);
//    UpdateCamBien();
    Firebase.stream(PATH);
    nhaovo = false;
    delay(100);
  }
  if (Firebase.available()) {
    F_GetData();
    streamTimeouts--;
  }
  //Serial.println("not available");
  if (Firebase.failed()) {
    if (streamTimeouts == 5)
    {
      streamTimeouts = 0;
      ESP.reset();
    }
    streamTimeouts++;
    Serial.println("streaming error");
    //Serial.println(Firebase.error());
    Firebase.stream(PATH);
    delay(200);
    return;
  }
}
void F_GetData()
{
  Serial.println("available");
  FirebaseObject event = Firebase.readEvent();
  JsonVariant payload = event.getJsonVariant("data");
  JsonVariant payload2 = Firebase.get(PATH).getJsonVariant("data");
  //payload.printTo(Serial);
  //Serial.println(" --888----");
  String path = event.getString("path");
  int data = event.getInt("data");
  //Serial.print("path ");
  //Serial.println(path);
  //Serial.print("data ");
  //Serial.println(data);
  if (path == "/device_1")
  {
    Fthiet_bi_1 = data;
  }
  if (path == "/device_2")
  {
    Fthiet_bi_2 = data;
  }
  if (path == "/device_3")
  {
    Fthiet_bi_3 = data;
  }
  if (path == "/device_4")
  {
    Fthiet_bi_4 = data;
  }

  for ( const auto& kv : payload.as<JsonObject>() ) {
    //       Serial.print(kv.as<String>());
    int value = kv.value.as<int>();
    String key = kv.key;
    if (key == "device_1")
    {
      Fthiet_bi_1 = kv.value.as<int>();
    }
    if (key == "device_2")
    {
      Fthiet_bi_2 = kv.value.as<int>();
    }
    if (key == "device_3")
    {
      Fthiet_bi_3 = kv.value.as<int>();
    }
    if (key == "device_4")
    {
      Fthiet_bi_4 = kv.value.as<int>();
    }
    Serial.print(kv.key);
    Serial.print(" ");
    Serial.println(kv.value.as<char*>());
  }

  //Serial.println("data --888----");
  //Serial.println(Fthiet_bi_1);
  //Serial.println(Fthiet_bi_2);
  //Serial.println(Fthiet_bi_3);
  //Serial.println(Fthiet_bi_4);
  //Serial.println("data --999----");
  if (Othiet_bi_1 != Fthiet_bi_1)
  {
    Serial.print("Fthiet_bi_1 " + Fthiet_bi_1);
    digitalWrite(thiet_bi_1, Fthiet_bi_1);
    Othiet_bi_1 = Fthiet_bi_1;
  }
  if (Othiet_bi_2 != Fthiet_bi_2)
  {
    Serial.print("Fthiet_bi_2 " + Fthiet_bi_2);
    digitalWrite(thiet_bi_2, Fthiet_bi_2);
    Othiet_bi_2 = Fthiet_bi_2;

  }
  if (Othiet_bi_3 != Fthiet_bi_3)
  {
    Serial.print("Fthiet_bi_3 " + Fthiet_bi_3);
    digitalWrite(thiet_bi_3, Fthiet_bi_3);
    Othiet_bi_3 = Fthiet_bi_3;
  }
  if (Othiet_bi_4 != Fthiet_bi_4)
  {
    Serial.print("Fthiet_bi_4 " + Fthiet_bi_4);
    digitalWrite(thiet_bi_4, Fthiet_bi_4);
    Othiet_bi_4 = Fthiet_bi_4;

  }

  if (Firebase.failed()) {
    if (streamTimeouts == 5)
    {
      streamTimeouts = 0;
      ESP.reset();
    }
    streamTimeouts++;
    Serial.print("setting /number failed:");
    Serial.println(Firebase.error());
    Firebase.stream(PATH);
  }
  Serial.println("stream");
  //set streaming again
  delay(200);
  // return;
  // ESP.getFreeHeap();
  //Serial.println("ESP.getFreeHeap()");
}
void setDevice(String key, int value)
{
  Serial.println("aaaa");
  Serial.println(key);
  Serial.println(value);
  Serial.println("aaaa");
  //  digitalWrite(key,value);
}
void getCamBien()
{
  dht.begin();
  Serial.print("vainoi ");
  h = dht.readHumidity();
  t = dht.readTemperature();  // Đọc nhiệt độ theo độ C
  delay(200);
  //ds18B20
  doamdat = getTemp();
  //doamdat = map(analogRead(A0), 0, 1023, 0, 100);
  Serial.print("Nhiet do: ");
  Serial.print(t);
  Serial.print("*C  ");
  Serial.print("Do am: ");
  Serial.print(h);
  Serial.println("%  ");
  Serial.print("nhiet do dung dich: ");
  Serial.print(doamdat);
  Serial.println("oC  ");
  // gui len api server
  //    Firebase.setFloat("nhietdo", t);
  //    Firebase.setFloat("doam", h);
  //    Firebase.setFloat("doamdat", doamdat);
  //end gui len server
  //h = h + 1;

}
float getTemp()
{
  byte data[12];
  byte addr[8];

  ds.search(addr);
  ds.reset();
  ds.select(addr);
  ds.write(0x44, 1);
  byte present = ds.reset();
  ds.select(addr);
  ds.write(0xBE);
  for (int i = 0; i < 9; i++)
  {
    data[i] = ds.read();
  }
  ds.reset_search();
  float TRead = ((data[1] << 8) | data[0]);
  float Temperature = TRead / 16;
  return Temperature;
}

void UpdateCamBien()
{
  String Url = "http://apifarm.lilotech.com/api/Test/InsertSenor?UserId=1&Humidity=" + String(doamdat) + "&Temperature=" + String(t) + "&air=" + String(h) + "&Light=" + Othiet_bi_1 + "&pump=" + Othiet_bi_2 + "&status=0";
  //String Url = "http://apifarm.lilotech.com/api/Test/InsertSenor?UserId=1&Humidity=doamdat&Temperature=t&air=h&Light=1&pump=0&status=0";

  //Serial.println("aaaaa");
  Serial.println(Url);
  WiFiClient client;

  HTTPClient http;

  Serial.print("[HTTP] begin...\n");
  //    http.begin(client, "http://api-crm2.lilotech.com/api/zalo/GetProductDetailbyProductCode");
  //    delay(1000);
  //http://apifarm.lilotech.com/api/Test/InsertSenor?userid=1&Humidity=9.0&Temperature=9.0&Light=0&pump=0&status=1
  if (http.begin(client, Url)) {  // HTTP


    //Serial.print("[HTTP] GET...\n");
    // start connection and send HTTP header
    int httpCode = http.GET();
    delay(300);
    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }

  delay(100);
}
