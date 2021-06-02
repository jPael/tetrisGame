/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author jason
 */
public class TetrisAudio {

    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    File soundFile;
    boolean playing = false;

    public TetrisAudio() {
        try {
            Path p = Paths.get("tetris_background.mp3");
            System.out.println(p);
            soundFile = new File(p.toString());
            //   System.out.println(soundFile);
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            audioFormat = audioInputStream.getFormat();
//            System.out.println(audioFormat);
        } catch (Exception e) {
            System.out.println("Can't find the audio file!");
            System.out.println(e);
        }
    }

}
