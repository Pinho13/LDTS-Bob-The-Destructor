package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MainMenuViewerTest {
    private MainMenuViewer viewer;
    private MainMenu menu;
    private ViewerProvider viewerProvider;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(MainMenu.class);
        when(menu.getButtons()).thenReturn(new ArrayList<>());
        
        viewerProvider = mock(ViewerProvider.class);
        when(viewerProvider.getButtonViewer()).thenReturn(mock(ButtonViewer.class));
        when(viewerProvider.getWallpaperViewer()).thenReturn(mock(WallpaperViewer.class));
        
        viewer = new MainMenuViewer(menu, viewerProvider);
    }

    @Test
    void testDraw() throws IOException {
        GUI gui = mock(GUI.class);
        List<com.ldtsfeup2526.bobTheDestructor.model.menu.Button> buttons = new ArrayList<>();
        com.ldtsfeup2526.bobTheDestructor.model.menu.Button button = mock(com.ldtsfeup2526.bobTheDestructor.model.menu.Button.class);
        buttons.add(button);
        when(menu.getButtons()).thenReturn(buttons);

        viewer.draw(gui, 0.1);
        
        verify(gui).clear();
        verify(gui).drawBackground(any());
        verify(viewerProvider.getWallpaperViewer()).draw(eq(gui));
        verify(viewerProvider.getButtonViewer()).draw(eq(button), eq(gui), anyDouble());
        verify(gui).refresh();
    }
}
