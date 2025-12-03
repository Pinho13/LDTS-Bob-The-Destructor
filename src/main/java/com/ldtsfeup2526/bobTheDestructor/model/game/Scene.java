package com.ldtsfeup2526.bobTheDestructor.model.game;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.BlockModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;

public class Scene {
    private PlayerModel player;
    private BlockModel[][] blocks;

    public Scene() {
        player = new PlayerModel(new Position(50, 50));
        blocks = new BlockModel[10][10];
    }
    public PlayerModel getPlayer() { return player; }

    public BlockModel[][] getBlocks() { return blocks; }
}
