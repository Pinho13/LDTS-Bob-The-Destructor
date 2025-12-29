package com.ldtsfeup2526.bobTheDestructor.controller.credits;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.util.List;

public class CreditsController extends Controller<Credits> {
    public CreditsController(Credits model) {
        super(model);
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        if (actions.contains(Action.QUIT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader(), game.getSoundManager()));
        }
    }
}
