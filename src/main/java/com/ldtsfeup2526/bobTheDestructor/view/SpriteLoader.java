package com.ldtsfeup2526.bobTheDestructor.view;

import java.io.IOException;

public interface SpriteLoader {
    Sprite get(String spriteFilePath) throws IOException;
}
