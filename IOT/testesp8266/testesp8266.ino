  #include <ESP8266WiFi.h>
  // Thong so WiFi
  const char* ssid = "Fpoly-Students";          //Thay  ten_wifi hiện tại
  const char* password = "fpolyhcm@123"; // mat_khau_wifi hiện tại
  void setup(void)
  {
  // Khoi dong serial de debug
    Serial.begin(115200);
  // Ket noi voi WiFi
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) { //Kiem tra xem trang thai da ket noi chua neu chua thi in ra dau .
      delay(500);
      Serial.print(".");
    }
    Serial.println("");
    Serial.println("WiFi connected");
  // Neu da ket noi duoc voi wifi se in ra dia chi IP
    Serial.println(WiFi.localIP());
  }
  void loop() {
  }
