package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.view.GUI;
import com.ldtsfeup2526.bobTheDestructor.view.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.view.InputReader;
import com.ldtsfeup2526.bobTheDestructor.view.InputReaderLanterna;

import java.io.IOException;

public class Game {
    private GUI gui;
    private InputReader inputReader;

    public Game() throws IOException {
        System.out.println( "Starting GUI... ");
        gui = new GUILanterna();
        inputReader = new InputReaderLanterna(gui.getScreen());
    }

    public void run() throws IOException {

    }
}
