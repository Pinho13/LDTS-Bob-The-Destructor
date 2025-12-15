package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class JumpingState extends PlayerState {
    public JumpingState(PlayerModel playerModel) {
        super(playerModel);
    }

    public PlayerState getNextState() {

        if (getPlayerModel().getRigidBody().getVelocity().getY() > 0.5) {
            return new FallingState(getPlayerModel());
        }
        Collider blockUnder = getPlayerModel().getCollider().colPosCheck(
                new Position(getPlayerModel().getPosition().getX(), getPlayerModel().getPosition().getY()+1));
        if (getPlayerModel().getScene().checkCollision(blockUnder)) {
            return new IdleState(getPlayerModel() );
        }

        return this;
    }
}
