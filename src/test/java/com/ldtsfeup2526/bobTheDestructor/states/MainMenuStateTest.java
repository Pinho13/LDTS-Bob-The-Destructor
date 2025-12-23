package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MainMenuStateTest {
    @Test
    void testConstructor() throws IOException {
        MainMenu model = mock(MainMenu.class);
        when(model.getSoundPlayer()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer.class));
        
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        MainMenuState state = new MainMenuState(model, spriteLoader);
        assertNotNull(state.createController());
        assertNotNull(state.createScreenViewer(new com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider(spriteLoader)));
    }
}
