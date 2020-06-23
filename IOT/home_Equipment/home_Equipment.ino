#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>

#include <FirebaseArduino.h>
#include <IRremote.h>
#include <OneWire.h>
#include <DNSServer.h>
#include <WiFiManager.h>
WiFiManager wifiManager;
IRsend irsend;
#define FIREBASE_HOST "https://blockdoor-59a3c.firebaseio.com/"
//p3fiOyJhb8uZXehJB34YqOd5NlDNiupqtlkofNoB
#define FIREBASE_AUTH ""
#define WIFI_SSID "Fpoly NhanVien"
#define WIFI_PASSWORD "nhanvien@391"
#define Den_1 = 12; //D6 
#define Den_2 = 13; //D7
#define Door = ;

#define PATH "/Equipment/Door"
int FDen_1 = 0;
int FDen_2 = 0;
int CDoor = ;

int ODoor = , ODen_1 = 0, ODen_2 =0;



#define TRIGGER_PIN 9 //SD2

void setup()
{
  Serial.begin(115200);


  
}

void loop()
{

  
}
