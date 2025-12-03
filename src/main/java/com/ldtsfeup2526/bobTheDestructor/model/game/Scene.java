package com.ldtsfeup2526.bobTheDestructor.model.game;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.BlockModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;

import java.util.HashMap;
import java.util.Map;

public class Scene {
    private static HashMap<Position, Boolean> occupancy;
    private PlayerModel player;
    private BlockModel[][] blocks;

    public Scene() {
        occupancy = new HashMap<Position, Boolean>();
        player = new PlayerModel(new Position(50, 50), occupancy);
        blocks = new BlockModel[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                blocks[i][j] = new BlockModel(new Position(i, j), BlockModel.Type.IRON, occupancy);
            }
        }
    }
    public PlayerModel getPlayer() { return player; }

    public BlockModel[][] getBlocks() { return blocks; }

    public Map<Position, Boolean> getOccupancy() { return occupancy; }

    public void setOccupancy(Position position, boolean value) { occupancy.put(position, value); }
}