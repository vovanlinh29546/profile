#include <Servo.h>
Servo myservo;  // create servo object to control a servo


void setup() {
  pinMode(A0, INPUT);
  Serial.begin(9600);
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object
  


}

void loop() {
  int i = analogRead(A0);
  Serial.print("light=");
  Serial.println(i);
  
  myservo.write(70);
    if (i > 150)
  {
                     // waits 100ms for the servo to reach the position
    myservo.write(0);
    delay(100);
  }
}
