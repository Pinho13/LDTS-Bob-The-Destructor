package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.states.game.PlayerState;

import java.util.HashMap;

public class PlayerModel extends ElementModel {
    private PlayerState state = PlayerState.IDLE;

    public PlayerModel(Position position, HashMap<Position, Boolean> occupancy) {
        super(position, occupancy);
    }



    public PlayerState getState() {return state;}

    public void setState(PlayerState state) {this.state = state;}
}

