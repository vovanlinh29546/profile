
#include <Adafruit_MQTT.h>            //Adafruit MQTT Libraries
#include <Adafruit_MQTT_Client.h>     //
#include <ESP8266WiFi.h>              //ESP8266 Wi-Fi libraries

#define relayPin 4                 //declare the relay pin
#define WLAN_SSID "linhvv"          //enter your WiFi network name within the double quotes & 
#define WLAN_PASS "linhvv10155"          //password here

#define AIO_SERVER       "io.adafruit.com"
#define AIO_SERVERPORT   1883               // use 8883 is used for SSL
#define AIO_USERNAME     "vovanlinh29546"       //Username of Adafruit IO (for this click the Golden Key in Adafruit IO Dashboard)
#define AIO_KEY          "aio_zCkA89XKOWjJGbOMVRRArsW0b6qj"       //key of Adafruit IO (for this click the Golden Key in Adafruit IO Dashboard)

WiFiClient client;                                                                                  // Create an ESP8266 WiFiClient class to connect to the MQTT server.
Adafruit_MQTT_Client mqtt(&client, AIO_SERVER, AIO_SERVERPORT, AIO_USERNAME, AIO_KEY);              // Setup the MQTT client class by passing in the WiFi client and MQTT server and login details.
Adafruit_MQTT_Subscribe onoffbutton = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/baochay");  // Setup a feed called 'onoff' for subscribing to changes.
void MQTT_connect();
void setup() 
{
  pinMode(relayPin, OUTPUT);
  digitalWrite(relayPin, LOW);  
  Serial.begin(9600);
  delay(10);
  Serial.println(); 
  Serial.println();
  Serial.print("Connecting to ");       // Connect to WiFi access point.
  Serial.println(WLAN_SSID);
  WiFi.begin(WLAN_SSID, WLAN_PASS);
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.println("WiFi connected");
  Serial.println("IP address: "); 
  Serial.println(WiFi.localIP());
  mqtt.subscribe(&onoffbutton);        // Setup MQTT subscription for onoff feed.
}

uint32_t x=0;
void loop() 
{
  // Ensure the connection to the MQTT server is alive (this will make the first
  // connection and automatically reconnect when disconnected).  See the MQTT_connect
  // function definition further below.
  MQTT_connect();
  // this is our 'wait for incoming subscription packets' busy subloop
  // try to spend your time here
  Adafruit_MQTT_Subscribe *subscription;
//  while ((subscription = mqtt.readSubscription(5000))) 
//  {
//    if (subscription == &onoffbutton) {
//      Serial.print(F("Got: "));
//      Serial.println((char *)onoffbutton.lastread);
//      String response = (char*)onoffbutton.lastread; //converts the received 1 or 0 to string to compare in the if-else statement
//      if (response == "1")
//      {
//        digitalWrite(relayPin, HIGH);
// 
//      }
//      else if (response == "0")
//      {
//        digitalWrite(relayPin, LOW);
//      }
//    }
//  }

}

void MQTT_connect() 
{
  int8_t ret;
  if (mqtt.connected())     // Stop if already connected.
  {
    return;
  }
  Serial.print("Connecting to MQTT... ");
  uint8_t retries = 3;
  while ((ret = mqtt.connect()) != 0) 
  { 
       Serial.println(mqtt.connectErrorString(ret));                  // connect will return 0 for connected
       Serial.println("Retrying MQTT connection in 5 seconds...");
       mqtt.disconnect();
       delay(5000);              // wait 5 seconds
       retries--;
       if (retries == 0) 
       {
         while (1);              // basically die and wait for WDT to reset me
       }
  }
  Serial.println("MQTT Connected!");
}
