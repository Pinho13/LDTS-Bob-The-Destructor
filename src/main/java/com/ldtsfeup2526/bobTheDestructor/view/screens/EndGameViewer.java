package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.EndViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class EndGameViewer extends ScreenViewer<MainMenu> {
    private final WallpaperViewer wallpaperViewer;
    private final EndViewer endViewer;

    public EndGameViewer(MainMenu model, ViewerProvider viewerProvider) {
        super(model);
        this.wallpaperViewer = viewerProvider.getWallpaperViewer();
        this.endViewer = viewerProvider.getEndViewer();
    }

    public void draw(GUI gui, double deltaTime) throws IOException {
        gui.clear();

        gui.drawBackground(new TextColor.RGB(57, 53, 74));
        wallpaperViewer.draw(gui);
        endViewer.draw(gui);


        gui.refresh();
    }
}
