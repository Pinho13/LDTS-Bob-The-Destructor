package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.screens.EndGameViewer;

import javax.sound.sampled.Control;
import java.io.IOException;
import java.util.List;

public class EndGameController extends Controller<MainMenu> {
    public EndGameController(MainMenu elementModel) {
        super(elementModel);
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        if (actions.contains(Action.QUIT) || actions.contains(Action.SELECT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
        }
    }
}
