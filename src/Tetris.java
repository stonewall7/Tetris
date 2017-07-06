import java.awt.BorderLayout;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import sun.applet.Main;

/**
 * Homework 13.1: This class acts as a driver for Tetris.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class Tetris {
	/**
	 * Create the frame and panels for playing Tetris.
	 * @throws LineUnavailableException 
	 */
	public static void main(String[] args) throws LineUnavailableException{
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AudioInputStream inputStream;
		try {
			inputStream = AudioSystem.getAudioInputStream(Main.class.getResource("/TetrisTheme.wav"));
	        Clip clip = AudioSystem.getClip();
	        clip.open(inputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StatsPanel stats=new StatsPanel();
		frame.add(stats, BorderLayout.WEST);
		frame.add(new PlayingPanel(stats));
		frame.pack();
		frame.setVisible(true);
	}
}
