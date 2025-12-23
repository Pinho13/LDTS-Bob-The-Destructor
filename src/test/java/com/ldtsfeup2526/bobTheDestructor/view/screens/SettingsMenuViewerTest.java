package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class SettingsMenuViewerTest {
    private SettingsMenuViewer viewer;
    private SettingsMenu menu;
    private ViewerProvider viewerProvider;

    @BeforeEach
    void setUp() {
        menu = mock(SettingsMenu.class);
        when(menu.getButtons()).thenReturn(new ArrayList<>());
        
        viewerProvider = mock(ViewerProvider.class);
        when(viewerProvider.getButtonViewer()).thenReturn(mock(ButtonViewer.class));
        when(viewerProvider.getWallpaperViewer()).thenReturn(mock(WallpaperViewer.class));
        
        viewer = new SettingsMenuViewer(menu, viewerProvider);
    }

    @Test
    void testDraw() throws IOException {
        GUI gui = mock(GUI.class);
        viewer.draw(gui, 0.1);
        
        verify(gui).clear();
        verify(viewerProvider.getWallpaperViewer()).draw(eq(gui));
        verify(gui).refresh();
    }
}
