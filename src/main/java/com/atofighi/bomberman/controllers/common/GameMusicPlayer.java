package com.atofighi.bomberman.controllers.common;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class GameMusicPlayer {
    public void play() {
        try {
            URL url = this.getClass().getClassLoader().getResource("game.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            clip.loop(Integer.MAX_VALUE);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
