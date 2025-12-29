package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;

import java.io.IOException;

public class CreditsViewer extends ScreenViewer<Credits> {
    private final TitleViewer titleViewer;

    public CreditsViewer(Credits credits, ViewerProvider viewerProvider) {
        super(credits);
        titleViewer = viewerProvider.getTitleViewer();
    }

    @Override
    public void draw(GUI gui, double deltaTime) throws IOException {
        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, 10), "credits", gui);

        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, Game.resolution.height()/2 - 15), getModel().getCredits()[0], gui);
        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, Game.resolution.height()/2 - 5), getModel().getCredits()[1], gui);
        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, Game.resolution.height()/2 + 5), getModel().getCredits()[2], gui);
        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, Game.resolution.height()/2 + 15), getModel().getCredits()[3], gui);
    }
}
