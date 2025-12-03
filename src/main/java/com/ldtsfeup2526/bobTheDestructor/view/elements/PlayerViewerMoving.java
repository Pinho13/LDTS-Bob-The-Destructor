package com.ldtsfeup2526.bobTheDestructor.view.elements;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class PlayerViewerMoving implements PlayerViewer {
    private final Map<String, Sprite> spriteMap;

    public PlayerViewerMoving(SpriteLoader spriteLoader) throws IOException {
        spriteMap = new HashMap<>();
        spriteMap.put("sprites/player/player_walk_1.png", spriteLoader.get("sprites/player/player_walk4.png"));
        spriteMap.put("sprites/player/player_walk_2.png", spriteLoader.get("sprites/player/player_walk3.png"));
        spriteMap.put("sprites/player/player_walk_3.png", spriteLoader.get("sprites/player/player_walk2.png"));
        spriteMap.put("sprites/player/player_walk_4.png", spriteLoader.get("sprites/player/player_walk1.png"));    }
    @Override public void draw(Position pos, GUI gui) {
        Sprite sprite = spriteMap.get("sprites/player/player1.png");
        sprite.draw(gui, pos);
    }
}
