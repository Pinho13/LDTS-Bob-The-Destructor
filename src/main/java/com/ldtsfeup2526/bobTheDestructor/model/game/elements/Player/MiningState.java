package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

public class MiningState extends PlayerState{
    public MiningState(PlayerModel playerModel) {
        super(playerModel);
    }

    @Override
    public PlayerState getNextState() {

        return this;
    }
}
