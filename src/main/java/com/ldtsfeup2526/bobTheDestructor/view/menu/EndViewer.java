package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;

public class EndViewer {
    private final Sprite sprite;

    public EndViewer(SpriteLoader spriteLoader) throws IOException {
        this.sprite = spriteLoader.get("sprites/EndGameScreen.png");
        this.sprite.center();
    }

    public void draw(GUI gui) {
        sprite.draw(new Position(Game.resolution.getWidth()/2, Game.resolution.getHeight()/2), gui);
    }
}
