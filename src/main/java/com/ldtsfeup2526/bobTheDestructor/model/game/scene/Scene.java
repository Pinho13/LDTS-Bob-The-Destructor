package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;

public class Scene {
    private final String caveFilePath;
    private PlayerModel playerModel = new PlayerModel(new Position(50, 50));

    public Scene(String caveFilePath) {
        this.caveFilePath = caveFilePath;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public String getCaveFilePath() {
        return caveFilePath;
    }
}
