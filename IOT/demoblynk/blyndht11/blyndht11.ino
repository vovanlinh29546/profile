/* Comment this out to disable prints and save space */
#define BLYNK_PRINT Serial


#include <ESP8266WiFi.h>
#include <BlynkSimpleEsp8266.h>

// You should get Auth Token in the Blynk App.
// Go to the Project Settings (nut icon).
char auth[] = "al_-NvZAZvI5gkAmz6NCJe3lF02OIiR1";

// Your WiFi credentials.
// Set password to "" for open networks.
char ssid[] = "Fpoly-Students";
char pass[] = "fpolyhcm@123";


//#define DHTTYPE DHT22   // DHT 22, AM2302, AM2321
//#define DHTTYPE DHT21   // DHT 21, AM2301

#include "DHT.h"            
 
const int DHTPIN = 4;       //Đọc dữ liệu từ DHT11 ở chân 2 trên mạch Arduino
const int DHTTYPE = DHT11;  //Khai báo loại cảm biến, có 2 loại là DHT11 và DHT22
 
DHT dht(DHTPIN, DHTTYPE);
BlynkTimer timer;

// This function sends Arduino's up time every second to Virtual Pin (5).
// In the app, Widget's reading frequency should be set to PUSH. This means
// that you define how often to send data to Blynk App.
void sendSensor()
{
  float h = dht.readHumidity();    //Đọc độ ẩm
  float t = dht.readTemperature(); //Đọc nhiệt độ
  if (isnan(h) ) {
    Serial.println("Failed to read from DHT sensor!");
   return;
  }
  // You can send any value at any time.
  // Please don't send more that 10 values per second.
// or dht.readTemperature(true) for Fahrenheit
    Blynk.virtualWrite(V5, h);
  Serial.println(h);
  Serial.println(t);
  Blynk.virtualWrite(V6, t);
}

void setup()
{
  // Debug console
  Serial.begin(9600);

  Blynk.begin(auth, ssid, pass);
  // You can also specify server:
  //Blynk.begin(auth, ssid, pass, "blynk-cloud.com", 80);
  //Blynk.begin(auth, ssid, pass, IPAddress(192,168,1,100), 8080);

  dht.begin();

  // Setup a function to be called every second
 timer.setInterval(1000L, sendSensor);
}

void loop()
{
    
  
  Blynk.run();
  timer.run();
  
}
