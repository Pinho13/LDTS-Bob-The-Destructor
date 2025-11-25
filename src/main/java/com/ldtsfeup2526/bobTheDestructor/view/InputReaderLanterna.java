package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import javax.swing.*;
import java.io.IOException;

public class InputReaderLanterna extends InputReader {
    private final Screen screen;

    public InputReaderLanterna(Screen screen) {
        this.screen = screen;
    }

    public INPUT readNextInput() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return INPUT.NONE;

        if (keyStroke.getKeyType() == KeyType.ArrowUp) return INPUT.UP;
        if (keyStroke.getKeyType() == KeyType.ArrowRight) return INPUT.RIGHT;
        if (keyStroke.getKeyType() == KeyType.ArrowDown) return INPUT.DOWN;
        if (keyStroke.getKeyType() == KeyType.ArrowLeft) return INPUT.LEFT;

        if (keyStroke.getKeyType() == KeyType.EOF) return INPUT.QUIT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') return INPUT.QUIT;

        if (keyStroke.getKeyType() == KeyType.Enter) return INPUT.SELECT;

        return INPUT.NONE;
    }
}
