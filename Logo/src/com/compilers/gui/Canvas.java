package com.compilers.gui;

import com.compilers.configuration.Configuracion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JPanel;
import com.compilers.configuration.Configuration;
import com.compilers.configuration.Line;

public class Canvas extends JPanel {

    Configuracion configuration;

    public Canvas() {
        configuration = new Configuracion();
    }

    public void setConfiguration(Configuracion configuration) {
        this.configuration = configuration;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ArrayList<Line> lines = configuration.getLines();
        for (int i = 0; i < lines.size(); i++) {
            int x0 = (Properties.HEIGHT / 2) + lines.get(i).getX0();
            int y0 = (Properties.WEIGHT / 2) - lines.get(i).getY0();
            int x1 = (Properties.HEIGHT / 2) + lines.get(i).getX1();
            int y1 = (Properties.WEIGHT / 2) - lines.get(i).getY1();
            g.setColor(lines.get(i).getColor());
            g.drawLine(x0, y0, x1, y1);
        }
        g.setColor(Color.RED);
        Polygon triangle = turtle(configuration.getX(), configuration.getY(), configuration.getAngle());
        g.drawPolygon(triangle);
        g.fillPolygon(triangle);
    }

    public Polygon turtle(double x, double y, int angle) {
        Polygon polygon;
        int xs[] = new int[3];
        int ys[] = new int[3];
        xs[0] = (Properties.HEIGHT / 2) + (int) x;
        ys[0] = (Properties.WEIGHT / 2) - (int) y;
        xs[1] = (Properties.HEIGHT / 2) + (int) (x - 10 * Math.cos(Math.toRadians(angle + 20)));
        ys[1] = (Properties.WEIGHT / 2) - (int) (y - +10 * Math.sin(Math.toRadians(angle + 20)));
        xs[2] = (Properties.HEIGHT / 2) + (int) (x - 10 * Math.cos(Math.toRadians(angle - 20)));
        ys[2] = (Properties.WEIGHT / 2) - (int) (y - +10 * Math.sin(Math.toRadians(angle - 20)));
        polygon = new Polygon(xs, ys, 3);
        return polygon;
    }

}
