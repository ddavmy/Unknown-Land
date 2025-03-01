package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.HashMap;

public class Sound {
    private HashMap<Integer, Clip> activeClips = new HashMap<>();
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/background04.wav");
        soundURL[1] = getClass().getResource("/sounds/chest.wav");
        soundURL[2] = getClass().getResource("/sounds/footsteps.wav");
        soundURL[3] = getClass().getResource("/sounds/pickup01.wav");
    }

    public void setFile(int index, float volume) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            activeClips.put(index, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(int index) {
        if (activeClips.containsKey(index)) {
            activeClips.get(index).start();
        }
    }

    public void loop(int index) {
        if (activeClips.containsKey(index)) {
            activeClips.get(index).loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop(int index) {
        if (activeClips.containsKey(index)) {
            activeClips.get(index).stop();
        }
    }

    public boolean isPlaying(int index) {
        return activeClips.containsKey(index) && activeClips.get(index).isActive();
    }
}
