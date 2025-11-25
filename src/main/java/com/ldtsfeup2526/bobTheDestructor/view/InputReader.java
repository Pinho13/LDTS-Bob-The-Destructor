package com.ldtsfeup2526.bobTheDestructor.view;

import java.io.IOException;

public abstract class InputReader {
    public enum INPUT {SELECT, UP, RIGHT, DOWN, LEFT, NONE, QUIT}

    public abstract INPUT readNextInput() throws IOException;
}
