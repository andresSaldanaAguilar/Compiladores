package com.compilers.configuration;

import java.awt.Color;
import java.util.ArrayList;

public class Configuration {

    ArrayList<Line> lines;
    double x;
    double y;
    int angle;
    Color color;

    public Configuration() {
        x = 0.0;
        y = 0.0;
        lines = new ArrayList<>();
        color = Color.WHITE;
    }

    public void addLine(Line linea) {
        lines.add(linea);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void clean() {
        lines.clear();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angulo) {
        this.angle = angulo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String valor = "";
        for (int i = 0; i < lines.size(); i++) {
            valor += lines.get(i) + ", ";
        }
        valor += "x:" + x + " y:" + y + " angulo: " + angle;
        return valor;
    }

}
