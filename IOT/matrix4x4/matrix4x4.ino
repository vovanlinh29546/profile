/*
  Điều khiển Keypad 4x4
*/
#include <Servo.h>
#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x3F, 16, 2);

const int numRows = 4;       // Tổng số hàng của Keypad
const int numCols = 4;       // Tổng số cột của Keypad
const int debounceTime = 20; // Delay mỗi lần nhấn
// Định nghĩa bản keymap
const char keymap[numRows][numCols] = {
  { '1', '2', '3', 'a'  } ,
  { '4', '5', '6' , 'b' } ,
  { '7', '8', '9' , 'c' } ,
  { '*', '0', '#' , 'd' }
};
// Khai báo các chân Digitals kết nối với Keypad
const int rowPins[numRows] = { 6, 7, 8, 9 }; // Từ Rows 0 đến 3
const int colPins[numCols] = { 2, 3, 4, 5 };   // Từ Cols 0 đến 2
String dele = "";
String pass = "123456";
String getpass = "";
Servo myServo;
int pos = 0; 
void setup()
{
   myServo.attach(10); //servo pin
  myServo.write(0); //servo start position

  lcd.init();                      // initialize the lcd
  // Print a message to the LCD.
  lcd.backlight();
  Serial.begin(9600);
  lcd.begin(16, 2);
  lcd.setCursor(0, 1);
  //  pinMode(motor, OUTPUT);
  // pinMode(LedRed, OUTPUT);
  // pinMode(LedGreen, OUTPUT);

  for (int row = 0; row < numRows; row++)
  {
    pinMode(rowPins[row], INPUT);      // Hàng là Input
    digitalWrite(rowPins[row], HIGH);  // turn on Pull-ups
  }
  for (int column = 0; column < numCols; column++)
  {
    pinMode(colPins[column], OUTPUT);    // Cột là Output
    digitalWrite(colPins[column], HIGH); // Make all columns inactive
  }
}

void loop()
{

  char key = getKey(); // Gọi hàm ketKey();
  if ( key != 0) {      // Nếu phím đã được nhấn
    Serial.print("\nBan da nhan phim: ");
    Serial.println(key);

    dele = key;
    if ( dele == "d") {
      lcd.clear();
      dele = "";
      getpass = "";
    }
      else  if ( dele == "b") {
      lcd.clear();
      dele = "";
      getpass = "";
      ServoClose();
    }
    else if ( dele == "a") {
      if (pass == getpass) {
        lcd.clear();
        Serial.print("open door ");
        dele = "";
        getpass = "";
        lcd.print("open door ");
        ServoOpen();
      }
      else {
        lcd.clear();
        Serial.print("Wrong password ");
        dele = "";
        getpass = "";
        lcd.print("Wrong password");
      }
    }
    else {
      lcd.print(key);
      getpass += key;
      Serial.println(getpass);
      
    }
  }
}
// Trả về giá trị khi nhấn phím
char getKey()
{
  char key = 0;                                  // Khi chưa nhấn phím
  for (int column = 0; column < numCols; column++)
  {
    digitalWrite(colPins[column], LOW);        // Activate the current column.
    for (int row = 0; row < numRows; row++)    // Scan all rows for a key press.
    {
      if (digitalRead(rowPins[row]) == LOW)    // Is a key pressed?
      {
        delay(debounceTime);                   // Đợi thời gia 20ms
        while (digitalRead(rowPins[row]) == LOW) //Đang nhấn Button
        {      }                                  // Đợi nhả Button
        key = keymap[row][column];             // Lấy giá trị phím nhấn.
      }
    }
    digitalWrite(colPins[column], HIGH);    // De-activate the current column.
  }
  return key;  // Trả về giá trị của phím nhấn
}
void ServoOpen()
{
  myServo.write(180);
}

void ServoClose()
{
 myServo.write(0);
}
