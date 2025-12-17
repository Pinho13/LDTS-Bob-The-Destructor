package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.game.SceneController;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.EndGameController;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.EndViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.EndGameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.GameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import com.sun.tools.javac.Main;

import java.io.IOException;

public class EndState extends State<MainMenu> {
    public EndState(MainMenu model, SpriteLoader spriteLoader) throws IOException {
        super(model, spriteLoader);
    }

    public ScreenViewer<MainMenu> createScreenViewer(ViewerProvider viewerProvider) throws IOException {
        return new EndGameViewer(getModel(), viewerProvider);
    }

    public Controller<MainMenu> createController() {
        return new EndGameController(getModel());
    }
}
