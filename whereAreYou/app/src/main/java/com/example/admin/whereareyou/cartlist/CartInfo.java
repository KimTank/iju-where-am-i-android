package com.example.admin.whereareyou.cartlist;

public class CartInfo {
    int list_image, list_category;
    String list_name, list_pricetext;//가격같은경우 나중에 100,000-> , 표현식 들어가야됨//int값으로 놓으니 터져서 String으롷 변환(가격부분)

    public int getList_image() {
        return list_image;
    }

    public void setList_image(int list_image) {
        this.list_image = list_image;
    }

    public int getList_category() {
        return list_category;
    }

    public void setList_category(int list_category) {
        this.list_category = list_category;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getList_pricetext() {
        return list_pricetext;
    }

    public void setList_pricetext(String list_pricetext) {
        this.list_pricetext = list_pricetext;
    }

    public CartInfo(int list_image, int list_category, String list_pricetext, String list_name) {
        this.list_image = list_image;
        this.list_category = list_category;
        this.list_pricetext = list_pricetext;
        this.list_name = list_name;

    }

}

