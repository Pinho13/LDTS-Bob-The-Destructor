package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Widget;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonState;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ButtonViewer implements ElementViewer<Widget> {
    private final Map<ButtonType, Map<ButtonState, Sprite>> spriteMap = new HashMap<>();
    private final Map<ButtonType, Sprite> iconMap = new HashMap<>();
    private final Sprite pickaxeIcon;

    public ButtonViewer(SpriteLoader spriteLoader) throws IOException {
        Map<ButtonState, Sprite> tempMap;
        for (ButtonType buttonType : ButtonType.values()) {
            tempMap = new HashMap<>();
            tempMap.put(ButtonState.UNSELECTED, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/button1.png"));
            tempMap.put(ButtonState.SELECTED, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/button2.png"));
            tempMap.put(ButtonState.CLICKED, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/button3.png"));
            spriteMap.put(buttonType, tempMap);

            for (Sprite s : tempMap.values()) {
                s.center();
            }

            iconMap.put(buttonType, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/icon.png"));
        }
        for (Sprite s : iconMap.values()) {
            s.center();
        }

        pickaxeIcon = spriteLoader.get("sprites/ui/buttons/selected_icon.png");
        pickaxeIcon.center();
    }

    public void draw(Widget widget, GUI gui, double deltaTime) {
        Map<ButtonState, Sprite> buttonTypeSprites = spriteMap.get(widget.getButtonType());
        Sprite sprite = buttonTypeSprites.get(widget.getButtonState());

        sprite.draw(widget.getPosition(), gui);

        iconMap.get(widget.getButtonType()).draw(new Position(
                sprite.getSize().getX()/2 + widget.getPosition().getX() + 5,
                widget.getPosition().getY()),
                gui
        );

        if (widget.getButtonState() != ButtonState.UNSELECTED) {
            pickaxeIcon.draw(new Position(
                            widget.getPosition().getX() - sprite.getSize().getX() / 2 - 4,
                            widget.getPosition().getY()),
                    gui
            );
        }
    }

}
