//int port8= 8;
int port7 = 7;
char state;
void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);
  //pinMode(port8, OUTPUT);
  pinMode(port7, OUTPUT);
  //digitalWrite(port8,LOW);
  digitalWrite(port7, LOW);
}

void loop() {
  if (
    Serial.available() > 0) {

    state = Serial.read();
    delay(2);
    if(state=='1'){
      digitalWrite(port7, HIGH);
      Serial.println("on");}
    if(state=='a'){
         digitalWrite(port7, LOW);
      Serial.println("off");}
  }

}
