package com.ldtsfeup2526.bobTheDestructor.model.menu;

import java.util.List;

public abstract class Menu {
    private final List<Widget> widgets;
    private int currentButton = 0;

    public Menu() {
        this.widgets = createButtons();
    }

    protected abstract List<Widget> createButtons();

    public List<Widget> getButtons() {
        return widgets;
    }

    public int getNumberOfButtons() {
        return widgets.size();
    }

    public void moveDown() {
        getCurrentButton().setButtonState(ButtonState.UNSELECTED);
        currentButton++;
        currentButton %= getNumberOfButtons();
        getCurrentButton().setButtonState(ButtonState.SELECTED);
    }

    public void moveUp() {
        getCurrentButton().setButtonState(ButtonState.UNSELECTED);
        currentButton += getNumberOfButtons() - 1;
        currentButton %= getNumberOfButtons();
        getCurrentButton().setButtonState(ButtonState.SELECTED);
    }

    public Widget getCurrentButton() {
        return widgets.get(currentButton);
    }
}
