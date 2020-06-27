
#include <FirebaseArduino.h>
#include <ESP8266WiFi.h>

// Set these to your WIFI settings
#define WIFI_SSID "Fpoly-Students"
#define WIFI_PASSWORD "fpolyhcm@123"

//Set these to the Firebase project settings
#define FIREBASE_URL "homeequipment-553c7.firebaseio.com"
#define FIREBASE_DB_SECRET ""
#define PATH "/0967991025/Control"
#define PATHdoor "/0967991025/Control/Door"
int Fled1 = 0;
int Fled2 = 0;
int Fthiet_bi_3 = 0;
int FDoor = 0;
int Oled1 = 0, Oled2 = 0, Othiet_bi_3 = 0, ODoor = 0;
int streamTimeouts = 0;
//set the pin where the buzzer is connected
//#define BUZZER D2

int last_read = 0;
int diff = 0;
#define led1 5 //D1
#define led2 4 //D2
#define thiet_bi_3 15 //D5
#define Door 12 //D6
#define thiet_bi_5 16 //
void setup() {
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(thiet_bi_3, OUTPUT);
  pinMode(Door, OUTPUT);
  pinMode(thiet_bi_5, OUTPUT);
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

  //begin Firebase
  Firebase.begin(FIREBASE_URL, FIREBASE_DB_SECRET);

  Firebase.stream(PATH);
}


void loop() {
    if (Firebase.available()) {
    F_GetData();
    streamTimeouts--;
  }

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
//}

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
  if (path == "/Den_1")
  {
    Fled1 = data;
  }
  if (path == "/Den_2")
  {
    Fled2 = data;
  }
  if (path == "/Den_3")
  {
    Fthiet_bi_3 = data;
  }
  if (path == "/Door")
  {
    FDoor = data;
  }

  for ( const auto& kv : payload.as<JsonObject>() ) {
    //       Serial.print(kv.as<String>());
    int value = kv.value.as<int>();
    String key = kv.key;
    if (key == "Den_1")
    {
      Fled1 = kv.value.as<int>();
    }
    if (key == "Den_2")
    {
      Fled2 = kv.value.as<int>();
    }
    if (key == "Den_3")
    {
      Fthiet_bi_3 = kv.value.as<int>();
    }
    if (key == "Door")
    {
      FDoor = kv.value.as<int>();
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
  if (Oled1 != Fled1)//so sanh data neu khac thi bat hoặc tắt
  {
    Serial.print("Den 1 " + Fled1);
    digitalWriteOver(led1, Fled1);
    Oled1 = Fled1;
  }
  if (Oled2 != Fled2)
  {
    Serial.print("Den 2 " + Fled2);
    digitalWriteOver(led2, Fled2);
    Oled2 = Fled2;

  }
  if (Othiet_bi_3 != Fthiet_bi_3)
  {
    Serial.print("Den 3 " + Fthiet_bi_3);
    digitalWriteOver(thiet_bi_3, Fthiet_bi_3);
    Othiet_bi_3 = Fthiet_bi_3;
  }
  if (ODoor != FDoor)
  {
    Serial.print("Door" + FDoor);
    digitalWriteOver(Door, FDoor);
    ODoor = FDoor;
    delay(3000);
    Firebase.setInt(PATHdoor, 0);
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
