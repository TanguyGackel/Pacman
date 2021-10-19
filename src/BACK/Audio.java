package BACK;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio{
    Clip clip;
    public Audio(String source) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                new File(source));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
    }

    public void jouer(){
        clip.start();
    }

    public void pause(){
        clip.stop();
    }

    public void loop(){
        clip.loop(50);
    }


}