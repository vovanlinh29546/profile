
#include <DHT_U.h>

#include <DHT.h>
//int loa = 12;
#include <Stepper.h>
 #define DHTPIN 6
#define DHTTYPE DHT11
const int stepsPerRevolution = 4096;  // hehe
 
DHT dht(DHTPIN, DHTTYPE);//khai bao doi tuong
Stepper myStepper(stepsPerRevolution, 8, 9, 10, 11);
 
void setup() {
   dht.begin();// khởi động cảm biến
  myStepper.setSpeed(5);
  Serial.begin(9600);
}
 
void loop() {
 
float temp = dht.readTemperature(); 
             //Xuất nhiệt đ
             if(temp>25){
               Serial.print("Nhiet do: ");
             //Xuất độ ẩ
 //tone (loa,1000,1000);

 
  Serial.println(temp);            //Xuất độ ẩ
  Serial.println(); 
    
 Serial.println("clockwise");
myStepper.step(stepsPerRevolution);
  delay(500);

              }


 
}
