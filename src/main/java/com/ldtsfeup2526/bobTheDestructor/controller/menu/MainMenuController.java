package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class MainMenuController extends MenuController<MainMenu> {
    public MainMenuController(MainMenu menu) {
        super(menu);
    }

    @Override
    protected void onQuit(Game game) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        game.setState(null);
    }
}
