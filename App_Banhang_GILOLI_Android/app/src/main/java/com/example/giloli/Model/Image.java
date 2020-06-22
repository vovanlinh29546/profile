package com.example.giloli.Model;

public class Image {
    String nameImage;
    String nameURL;

    public Image(String nameImage, String nameURL) {
        if (nameImage.trim().equals("")){
            nameImage =  "Chua co ten";
        }
        this.nameImage = nameImage;
        this.nameURL = nameURL;
    }
    public Image(){
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public String getNameURL() {
        return nameURL;
    }

    public void setNameURL(String nameURL) {
        this.nameURL = nameURL;
    }
}
