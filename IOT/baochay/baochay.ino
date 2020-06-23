


 //int qc = 7;
 //int motor = 13;
 //int LedRed = 12;
 //int LedGreen = 11;
 //int loa = 9;
  

#include <DHT.h>

#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x3F,16,2);  
#define DHTPIN 7
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);//khai bao doi tuong
void setup() {
    dht.begin();// khởi động cảm biến
  Serial.begin(9600);
      lcd.init();                      // initialize the lcd 
  // Print a message to the LCD.
  lcd.backlight();
  
  lcd.begin(16, 2);
  lcd.setCursor(0,1);
//  pinMode(motor, OUTPUT);
 // pinMode(LedRed, OUTPUT);
 // pinMode(LedGreen, OUTPUT);
  delay(500);
  lcd.clear();
  lcd.print("Nhiet:  ");
  lcd.setCursor(0,1);
  lcd.print("Cuu Hoa: ");
}

void loop() {
    float temp = dht.readTemperature();
  lcd.setCursor(8,0);
  lcd.print(temp); 
  lcd.setCursor(11,1);
   Serial.println(temp);
// Nhiệt độ > 50
  if (temp > 50 ){
    //digitalWrite(motor, HIGH);
    //digitalWrite(LedRed, LOW);
    //digitalWrite(LedGreen, HIGH);
    lcd.print("ON ");
    //tone (loa,1000,1000);
  }
  else {
//    digitalWrite(motor, LOW);
  //  digitalWrite(LedRed, HIGH);
    //digitalWrite(LedGreen, LOW);
    lcd.print("OFF");
    //noTone (loa);
  }
  
   delay(1000);
}
