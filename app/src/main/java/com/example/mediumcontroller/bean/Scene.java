package com.example.mediumcontroller.bean;

import java.io.Serializable;

/**
 * Created by looper on 2020/10/8.
 */
public class Scene implements Serializable {
    String uuid;
    String name;
    String color;
    String music;
    String time;
    Boolean isLamp1;
    Boolean isLamp2;
    Boolean isFan;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getLamp1() {
        return isLamp1;
    }

    public void setLamp1(Boolean lamp1) {
        isLamp1 = lamp1;
    }

    public Boolean getLamp2() {
        return isLamp2;
    }

    public void setLamp2(Boolean lamp2) {
        isLamp2 = lamp2;
    }

    public Boolean getFan() {
        return isFan;
    }

    public void setFan(Boolean fan) {
        isFan = fan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
