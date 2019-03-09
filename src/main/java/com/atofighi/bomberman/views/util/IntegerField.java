package com.atofighi.bomberman.views.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IntegerField extends JTextField {
    public IntegerField(String placeHolder, int size) {
        super(placeHolder, size);
        setToolTipText(placeHolder);

        setForeground(Color.decode("#cccccc"));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (IntegerField.this.getText().equals(placeHolder)) {
                    IntegerField.this.setText("");
                    setForeground(Color.decode("#000000"));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (IntegerField.this.getText().equals("")) {
                    IntegerField.this.setText(placeHolder);
                    setForeground(Color.decode("#cccccc"));
                }
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public int getValue() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}