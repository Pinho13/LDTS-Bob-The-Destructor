package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import javax.swing.*;
import java.io.IOException;

public class InputReaderLanterna implements InputReader {
    private final Screen screen;

    public InputReaderLanterna(Screen screen) {
        this.screen = screen;
    }

    public INPUT readNextInput() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return INPUT.NONE;

        return switch (keyStroke.getKeyType()) {
            case KeyType.ArrowRight -> INPUT.RIGHT;
            case KeyType.ArrowUp -> INPUT.UP;
            case KeyType.ArrowDown -> INPUT.DOWN;
            case KeyType.ArrowLeft -> INPUT.LEFT;
            case KeyType.Escape, KeyType.EOF -> INPUT.QUIT;
            case KeyType.Enter -> INPUT.SELECT;
            default -> INPUT.NONE;
        };
    }
}
