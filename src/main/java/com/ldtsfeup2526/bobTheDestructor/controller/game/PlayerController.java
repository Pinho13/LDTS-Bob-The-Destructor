package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;

import java.util.List;

public class PlayerController extends Controller<PlayerModel> implements PlayerStateListener{
    private final SoundManager soundManager;

    public PlayerController(PlayerModel player, SoundManager soundManager) {
        super(player);
        getModel().addPlayerStateListener(this);
        this.soundManager = soundManager;
    }

    @Override
    public void update(Game game, List<Action> actions) {
        PlayerModel player = getModel();

        if (actions.contains(Action.RIGHT)) player.moveRight();
        if (actions.contains(Action.LEFT)) player.moveLeft();
        if (actions.contains(Action.JUMP) || actions.contains(Action.UP)) player.jump();
        if (actions.contains(Action.MINE) && player.getState().getClass() != MiningState.class) player.mine();

        if (!actions.contains(Action.LEFT) && !actions.contains(Action.RIGHT)) player.applyFriction();

        player.update();
    }

    @Override
    public void onPlayerStateEnter(PlayerState playerState) {
        if (playerState instanceof MiningState) {
            //play sound
        }
    }

    @Override
    public void onPlayerStateExit(PlayerState playerState) {

    }
}