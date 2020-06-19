package five;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * 音乐播放器
 * @author asus
 *
 */
public class AudioPlayer {
	//游戏音乐播放器
	public Clip bgm;
	//本次游戏是否暂停音乐
	public Boolean isstop = false;
	
	
	//加载背景音乐
	private void loadBGM() {
		try {
			this.bgm = AudioSystem.getClip();
			//获取音乐的输入流
			InputStream is = getClass().getClassLoader().getResourceAsStream("resource/audio/click.wav");
			//将输入流加入音乐对象中，使其成为可以播放的音乐
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			this.bgm.open(ais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AudioPlayer() {
		loadBGM();
	}
	
	//播放音乐
	public void player(float volume) {
		
		FloatControl gainControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
		//max:6   min:-80
		gainControl.setValue(volume); // Reduce volume by 10 decibels.
		if(!isstop)bgm.start();
	}
	
	
}
