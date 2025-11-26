package com.ldtsfeup2526.bobTheDestructor.view;

import java.io.IOException;

public interface InputReader {
    enum INPUT {SELECT, UP, RIGHT, DOWN, LEFT, NONE, QUIT}

    INPUT readNextInput() throws IOException;
}
