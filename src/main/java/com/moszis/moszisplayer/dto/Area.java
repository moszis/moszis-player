package com.moszis.moszisplayer.dto;

import java.io.Serializable;

public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    public double x;

    public double y;

    public double centerX;

    public double centerY;

    public double width;

    public double height;

    public double percentMatch;

    public boolean isMatch;

    public double calculateCenterX(){
        return x+width/2;
    }

    public double calculateCenterY(){
        return y+height/2;
    }

    public void generateCenterX(){
        centerX = calculateCenterX();
    }

    public void generateCenterY(){
        centerY = calculateCenterY();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public double getPercentMatch() {
        return percentMatch;
    }

    public void setPercentMatch(double percentMatch) {
        this.percentMatch = percentMatch;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean isMatch) {
        this.isMatch = isMatch;
    }

    @Override
    public String toString() {
        return "Area [x=" + x + ", y=" + y + ", centerX=" + centerX + ", centerY=" + centerY + ", width=" + width
                + ", height=" + height + ", percentMatch=" + percentMatch + ", isMatch=" + isMatch + "]";
    }



}
