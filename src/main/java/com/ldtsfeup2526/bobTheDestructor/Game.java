package com.ldtsfeup2526.bobTheDestructor;

import java.io.IOException;

public class Game {
    public Game() throws IOException {
        try {
            System.out.println( "Starting GUI... ");
            GUI gui = new GUI();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

    }
}
