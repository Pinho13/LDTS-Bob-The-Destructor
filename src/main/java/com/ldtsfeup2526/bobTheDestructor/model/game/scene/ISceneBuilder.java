package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;

import java.io.IOException;

public interface ISceneBuilder {
    Scene createScene(String caveFilePath, PlayerModel playerModel) throws IOException;
}
