package com.example.admin.whereareyou;

public class SearchInfo {

    String  list;
    int x, y;

    public SearchInfo(String list, int x, int y) {
        this.list = list;
        this.x = x;
        this.y = y;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
