package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GameStateTest {
    @Test
    void testConstructor() throws IOException {
        SceneManager model = mock(SceneManager.class);
        Scene scene = mock(Scene.class);
        when(model.getScene()).thenReturn(scene);
        when(scene.getPlayerModel()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel.class));
        when(scene.getMineralModels()).thenReturn(Collections.emptyList());
        when(scene.getSoundPlayer()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer.class));
        
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        GameState state = new GameState(model, spriteLoader);
        assertNotNull(state.createController());
        assertNotNull(state.createScreenViewer(new com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider(spriteLoader)));
    }
}
