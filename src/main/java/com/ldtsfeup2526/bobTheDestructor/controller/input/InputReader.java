package com.ldtsfeup2526.bobTheDestructor.controller.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputReader implements KeyListener {
    private List<Input> InputDown = new ArrayList<Input>();
    private List<Input> InputPressed = new ArrayList<>();
    private List<Input> InputUp = new ArrayList<>();


    public List<Input> getInputDown() {
        return InputDown;
    }

    public List<Input> getInputPressed() {
        return InputPressed;
    }

    public List<Input> getInputUp() {
        return InputUp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e);
    }
}
