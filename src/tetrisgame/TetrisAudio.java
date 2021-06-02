/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jason
 */
public class TetrisAudio {

    private Clip clip;

    public TetrisAudio() {

    }

    private void SoundEffect(URL url) {
        try {
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

// Play or Re-play the sound effect from the beginning, by rewinding.
    public void playTheSound() {

        URL url = getClass().getResource("tetris_background.wav");//You can change this to whatever other sound you have
        SoundEffect(url);//this method will load the sound

//        if (clip.isRunning()) {
//            clip.stop();   // Stop the player if it is still running
//        }
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();     // Start playing
        clip.loop(-1);
    }
    public void stop() {
        clip.stop();
    }

}
