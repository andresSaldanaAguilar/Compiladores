package com.compilers.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.compilers.interpreter.Parser;

public class Window extends JFrame {

    JTextArea textArea;
    JScrollPane scroll;
    Canvas canvas;
    JButton executeButton;
    Parser parser;
    boolean debugMode;

    public Window() {

        super("Logos");
        debugMode = false;
        parser = new Parser();
        parser.insertarInstrucciones();
        textArea = new JTextArea(20, 20);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setTabSize(4);
        scroll = new JScrollPane(textArea);
        scroll.setBounds(630, 5, 500, 500);
        add(scroll);

        canvas = new Canvas();
        canvas.setBounds(5, 5, Properties.HEIGHT, Properties.WEIGHT);
        canvas.setBackground(new Color(201, 206, 214));
        add(canvas);

        executeButton = new JButton("Execute");
        executeButton.setBounds(800, 550, 170, 40);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parser.limpiar();
                if (parser.compilar(textArea.getText())) {
                    canvas.setConfiguration(parser.ejecutar());
                } else {
                    parser = new Parser();
                    parser.insertarInstrucciones();
                    canvas.setConfiguration(parser.getConfiguracion());
                }
                canvas.repaint();
            }
        });
        add(executeButton);
        setLayout(null);
        setBounds(500, 1, 10 + scroll.getWidth() + canvas.getWidth() + 30, 649);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
