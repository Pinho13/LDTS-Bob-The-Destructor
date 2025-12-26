package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameSoundLoader implements SoundLoader{
    private final Map<String, AudioInputStream> streamMap = new HashMap<>();

    @Override
    public Clip get(String soundFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = streamMap.get(soundFilePath);

        if (audioInputStream == null) {
            URL resource = getClass().getClassLoader().getResource(soundFilePath);
            audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(resource));
            streamMap.put(soundFilePath, audioInputStream);
        }

        Clip clip = AudioSystem.getClip();
        AudioInputStream clipStream = AudioSystem.getAudioInputStream(audioInputStream.getFormat(), audioInputStream);
        clip.open(clipStream);
        return clip;
    }
}
