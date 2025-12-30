package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Widget;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.SliderViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SettingsMenuViewerTest {
    private SettingsMenuViewer viewer;
    private SettingsMenu menu;
    private ViewerProvider viewerProvider;

    @BeforeEach
    void setUp() {
        menu = mock(SettingsMenu.class);
        when(menu.getWidgets()).thenReturn(new ArrayList<>());
        
        viewerProvider = mock(ViewerProvider.class);
        when(viewerProvider.getSliderViewer()).thenReturn(mock(SliderViewer.class));
        when(viewerProvider.getWallpaperViewer()).thenReturn(mock(WallpaperViewer.class));
        when(viewerProvider.getTitleViewer()).thenReturn(mock(TitleViewer.class));
        
        viewer = new SettingsMenuViewer(menu, viewerProvider);
    }

    @Test
    void testDrawVerifiably() throws IOException {
        GUI gui = mock(GUI.class);
        List<Widget> widgets = new ArrayList<>();
        Widget widget = mock(Widget.class);
        widgets.add(widget);
        when(menu.getWidgets()).thenReturn(widgets);

        viewer.draw(gui, 0.1);
        
        verify(gui).drawBackground(argThat(color -> color.getRed() == 57 && color.getGreen() == 53 && color.getBlue() == 74));
        verify(viewerProvider.getWallpaperViewer()).draw(eq(gui));
        verify(viewerProvider.getSliderViewer()).draw(eq(widget), eq(gui), eq(0.1));
        verify(viewerProvider.getTitleViewer()).draw(argThat(p -> p.getX() == 83 && p.getY() == 10), eq("settings"), eq(gui));
        verify(gui).refresh();
    }
}
