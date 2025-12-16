package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;

import java.util.List;

public class Scene {
    private final String caveFilePath;
    private final PlayerModel playerModel;
    private List<Collider> blockColliders;
    private final List<MineralModel> mineralModels;
    private SoundPlayer soundtrackPlayer;
    private SoundPlayer walkingSoundPlayer;
    private SoundPlayer miningSoundPlayer;
    private SoundPlayer fallingSoundPlayer;

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

    public void setSoundtrackPlayer(SoundPlayer soundtrackPlayer) {this.soundtrackPlayer = soundtrackPlayer;}

    public SoundPlayer getSoundtrackPlayer() {return soundtrackPlayer;}

    public void setWalkingSoundPlayer(SoundPlayer walkingSoundPlayer) {this.walkingSoundPlayer = walkingSoundPlayer;}

    public SoundPlayer getWalkingSoundPlayer() {return walkingSoundPlayer;}

    public void setMiningSoundPlayer(SoundPlayer miningSoundPlayer) {this.miningSoundPlayer = miningSoundPlayer;}

    public SoundPlayer getMiningSoundPlayer() {return miningSoundPlayer;}

    public void setFallingSoundPlayer(SoundPlayer fallingSoundPlayer) {this.fallingSoundPlayer = fallingSoundPlayer;}

    public SoundPlayer getFallingSoundPlayer() {return fallingSoundPlayer;}

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
