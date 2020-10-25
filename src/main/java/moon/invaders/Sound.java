package moon.invaders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import abyss.lunarengine.tools.SSKPFilterInputStream;

public class Sound {
	private static final int CLIPCOUNT_ENEMYSHOOT=5;
	private static Clip[][] clip_EnemyShot;
	private static final int CLIPCOUNT_ENEMYEXPLODE=5;
	private static Clip[] clip_EnemyExplode;
	private static final int CLIPCOUNT_PLAYERSHOOT=5;
	private static Clip[] clip_PlayerShot;
	private static Clip[] clip_PlayerExplode;
	private static Clip clip_BackLoop;
	private static Clip clip_BackLoopPreloaded;
	private static Clip clip_Music;
	private static Clip clip_GalaxyFlight;
	
	public static void initialize(){
		System.out.println("initializing sound...");
		clip_EnemyShot=new Clip[9][];
		for(int i=0;i<9;i++) {
			String filename="data/sound/EnemyShot0"+(i+1)+".wav";
			//System.out.println("Loading: "+filename);
			clip_EnemyShot[i]=createClips(CLIPCOUNT_ENEMYSHOOT,filename);
		}
		clip_EnemyExplode=createClips(CLIPCOUNT_ENEMYEXPLODE,"data/sound/EnemyExplode1.wav");
		clip_PlayerShot=createClips(CLIPCOUNT_PLAYERSHOOT,"data/sound/PlayerShot1.wav");
		clip_PlayerExplode=new Clip[3];
		clip_PlayerExplode[0]=createClips(1,"data/sound/PlayerExplode1.wav")[0];
		clip_PlayerExplode[1]=createClips(1,"data/sound/PlayerExplode2.wav")[0];
		clip_PlayerExplode[2]=createClips(1,"data/sound/PlayerExplode3.wav")[0];
		clip_Music=createClips(1,"data/sound/visions.wav")[0];
		clip_GalaxyFlight=createClips(1,"data/sound/GalaxyFlight.wav")[0];
		prepareBackLoop(1);
		clip_BackLoop=clip_BackLoopPreloaded;
	}

	private static Clip[] createClips(int clipCount,String soundfilename){
		Clip[] clip=new Clip[clipCount];
		try {
			URL url = getResourceUrl(new Sound(),soundfilename);
			InputStream inputStream=null;
			if(url!=null){
				inputStream=url.openStream();
			}else {
				soundfilename=soundfilename.substring(0, soundfilename.lastIndexOf('.'))+".sskp";
				url = getResourceUrl(new Sound(),soundfilename);
				if(url!=null) {
					inputStream=new SSKPFilterInputStream(url.openStream());
				}
			}
			byte[] buffer=new byte[1024];
			int len=0;
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			while((len=inputStream.read(buffer))>0) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			baos.close();
			inputStream.close();
			for(int i=0;i<clipCount;i++){
				inputStream=new ByteArrayInputStream(baos.toByteArray());
				AudioInputStream ais = AudioSystem.getAudioInputStream(inputStream);
				clip[i] = AudioSystem.getClip();
				clip[i].open(ais);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error creating soundclip: "+soundfilename,e);
		}
		return clip;
	}
	
	public static void enemyShoot(int enemyNumber){
		int sound=enemyNumber % 9;
		for(int i=0;i<CLIPCOUNT_ENEMYSHOOT;i++){
			if(!clip_EnemyShot[sound][i].isRunning()){
				clip_EnemyShot[sound][i].setFramePosition(0);
				clip_EnemyShot[sound][i].start();
				break;
			}
		}
	}

	public static void enemyExplode(){
		for(int i=0;i<CLIPCOUNT_ENEMYEXPLODE;i++){
			if(!clip_EnemyExplode[i].isRunning()){
				clip_EnemyExplode[i].setFramePosition(0);
				clip_EnemyExplode[i].start();
				break;
			}
		}
	}

	public static void playerShoot(){
		for(int i=0;i<CLIPCOUNT_PLAYERSHOOT;i++){
			if(!clip_PlayerShot[i].isRunning()){
				clip_PlayerShot[i].setFramePosition(0);
				clip_PlayerShot[i].start();
				break;
			}
		}
	}

	public static void playerExplode(){
		int i=GameController.lives%3;
		clip_PlayerExplode[i].setFramePosition(0);
		clip_PlayerExplode[i].start();
	}

	public static void galaxyFlight(){
		clip_GalaxyFlight.setFramePosition(0);
		clip_GalaxyFlight.start();
	}
	
	public static void prepareBackLoop(int index){
		clip_BackLoopPreloaded=createClips(1,"data/sound/BackLoop"+(index<10?"0"+index:index)+".wav")[0];
	}
	
	public static void backLoopStart(){
		if(clip_BackLoopPreloaded!=null) {
			if(clip_BackLoop.isActive()) {
				clip_BackLoop.stop();
			}
			clip_BackLoop=clip_BackLoopPreloaded;
			clip_BackLoopPreloaded=null;
		}
		
		if(!clip_BackLoop.isActive()) {
			clip_BackLoop.setLoopPoints(0,-1);
			clip_BackLoop.setFramePosition(0);
			clip_BackLoop.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public static void backLoopEnd(){
		clip_BackLoop.stop();
	}

	public static void musicStart(){
		clip_Music.setLoopPoints(113650,-1);//89083 
		clip_Music.setFramePosition(0);
		clip_Music.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void musicEnd(){
		clip_Music.stop();
	}
	
	private static URL getResourceUrl(Object object,String relativeFilePathToImage){
		ClassLoader loader=object.getClass().getClassLoader();
		URL url=loader.getResource(relativeFilePathToImage);
		return url;
	}

}
