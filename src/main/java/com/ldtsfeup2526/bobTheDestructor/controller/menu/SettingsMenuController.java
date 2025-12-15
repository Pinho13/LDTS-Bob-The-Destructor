package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.game.MainMenuState;

import javax.sound.sampled.FloatControl;
import java.io.IOException;
import java.util.List;

public class SettingsMenuController extends MenuController<SettingsMenu> {
    private final float[] levels = new float[]{-30f, -20f, -10f, -5f, 0f};

    public SettingsMenuController(SettingsMenu menu) {
        super(menu);
    }

    @Override
    protected void onQuit(Game game) {
        try {
            game.setState(new MainMenuState(new com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu(), game.getSpriteLoader()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getModel().getSoundPlayer().stop();
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        for (Action action : actions) {
            switch (action) {
                case UP:
                case DOWN:
                    break;
                case QUIT:
                    onQuit(game);
                    break;
                case SELECT:
                    if (getModel().getCurrentButton().getButtonType() == ButtonType.VOLUME) {
                        if (getModel().getSoundPlayer().getSound().isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                            FloatControl gain = (FloatControl) getModel().getSoundPlayer().getSound().getControl(FloatControl.Type.MASTER_GAIN);
                            float current = gain.getValue();
                            int idx = 0;
                            for (int i = 0; i < levels.length; i++) {
                                if (current <= levels[i] + 0.1f) { idx = i; break; }
                            }
                            idx = (idx + 1) % levels.length;
                            gain.setValue(levels[idx]);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
