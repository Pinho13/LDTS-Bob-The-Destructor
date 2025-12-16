package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class MiningState extends PlayerState{
    public MiningState(PlayerModel playerModel) {
        super(playerModel);
        playerModel.getRigidBody().setAcceleration(new Vector(0, playerModel.getRigidBody().getAcceleration().getY()));
        playerModel.getRigidBody().setVelocity(new Vector(0, playerModel.getRigidBody().getVelocity().getY()));
    }

    @Override
    public void movePlayerLeft() {}

    @Override
    public void movePlayerRight() {}

    @Override
    public void jump() {}

    @Override
    public PlayerState getNextState() {
        return this;
    }
}
