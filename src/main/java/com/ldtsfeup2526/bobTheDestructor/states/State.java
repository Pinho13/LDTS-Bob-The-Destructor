package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;
import java.util.List;

public abstract class State<T> {
    private final T model;
    private final Controller<T> controller;
    private final ScreenViewer<T> screenViewer;
    private ActionParser actionParser;

    public State(T model, SpriteLoader spriteLoader) throws IOException {
        this.model = model;
        this.screenViewer = createScreenViewer(new ViewerProvider(spriteLoader));
        this.controller = createController();
        actionParser = new ActionParser();
    }

    public abstract ScreenViewer<T> createScreenViewer(ViewerProvider viewerProvider);
    public abstract Controller<T> createController();

    public T getModel() {
        return model;
    }

    public void update(GUI gui) throws IOException {
        controller.update(actionParser.get());
        screenViewer.draw(gui);
    }
}
