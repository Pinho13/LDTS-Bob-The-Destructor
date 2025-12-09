package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Menu;

import java.util.List;

public abstract class MenuController<T extends Menu> extends Controller<T> {
    private final ButtonController buttonController;

    public MenuController(T model) {
        super(model);
        this.buttonController = new ButtonController();
    }

    @Override
    public void update(Game game, List<Action> actions) {
        for (Action action : actions) {
            switch (action) {
                case UP:
                    this.getModel().moveUp();
                    break;
                case DOWN:
                    this.getModel().moveDown();
                    break;
            }
        }
    }
}
