package com.ldtsfeup2526.bobTheDestructor;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GUI {
    public GUI() throws IOException {
        TerminalSize terminalSize = new TerminalSize(1280, 720);

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();

        Screen screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);   // we will not use the cursor yet
        screen.startScreen();
        screen.doResizeIfNecessary();
    }
}
