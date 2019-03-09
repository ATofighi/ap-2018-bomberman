package com.atofighi.bomberman.util;

public class Rectangle {
    private int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public boolean hasIntersectionWith(Rectangle rectangle) {
        //System.err.println(this + " " + rectangle);
        return in(rectangle.x, rectangle.y) || in(rectangle.x + rectangle.width-1, rectangle.y) ||
                in(rectangle.x, rectangle.y + rectangle.height - 1) ||
                in(rectangle.x + rectangle.width -1, rectangle.y + rectangle.height - 1) ||
                rectangle.in(x, y) || rectangle.in(x + width - 1, y) || rectangle.in(x, y + height - 1) ||
                rectangle.in(x + width - 1, y + height - 1);
    }

    public boolean in(int x, int y) {
        return (x >= this.x && x < this.x + width && y >= this.y && y < this.y + height);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
