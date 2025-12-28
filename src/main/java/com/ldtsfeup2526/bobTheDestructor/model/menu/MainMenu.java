package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends Menu{
    @Override
    protected List<Widget> createButtons() {
        Widget start = new Widget(ButtonType.PLAY, ButtonState.SELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2)-15));
        Widget settings = new Widget(ButtonType.CONFIG, ButtonState.UNSELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2)-5));
        Widget credits = new Widget(ButtonType.CREDITS, ButtonState.UNSELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2)+5));
        Widget exit = new Widget(ButtonType.EXIT, ButtonState.UNSELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2)+15));
        return Arrays.asList(start, settings, credits, exit);
    }
}
