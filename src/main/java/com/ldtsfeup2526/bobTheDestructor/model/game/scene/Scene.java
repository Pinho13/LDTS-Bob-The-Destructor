package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final String caveFilePath;
    private final PlayerModel playerModel;
    private List<Collider> blockColliders;
    private final List<MineralModel> mineralModels;

    public Scene(String caveFilePath, PlayerModel playerModel, List<MineralModel> mineralModels) {
        this.caveFilePath = caveFilePath;
        this.playerModel = playerModel;
        this.playerModel.setScene(this);
        this.mineralModels = mineralModels;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public String getCaveFilePath() {
        return caveFilePath;
    }

    public void setBlockColliders(List<Collider> blockColliders) {
        this.blockColliders = blockColliders;
    }

    public List<Collider> getBlockColliders() {
        return blockColliders;
    }

    public boolean checkCollision(Collider collider) {
        for (Collider c : blockColliders) {
            if (c.isColliderOver(collider)) {
                return true;
            }
        }
        return false;
    }

    public List<MineralModel> getMineralModels() {
        return mineralModels;
    }
}
