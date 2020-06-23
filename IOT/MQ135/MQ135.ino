int sensorValue = A0;
int led = 8;
void setup()
{
  Serial.begin(9600);      // sets the serial port to 9600
  pinMode(led, OUTPUT);
}
void loop()
{
  sensorValue = analogRead(A0);
  Serial.print("\n\rGia tri Analog:");
  Serial.print(sensorValue, DEC);
  Serial.println("ppm");
  if (sensorValue > 500)// đối với khí gas
  {
    digitalWrite(led, HIGH);
  }
  else 
  {
    digitalWrite(led, LOW);
  }
  delay(1000);
}

