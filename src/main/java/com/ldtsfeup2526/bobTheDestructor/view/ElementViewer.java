package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;

public interface ElementViewer<T> {
    void draw(T model, GUI gui);
}
