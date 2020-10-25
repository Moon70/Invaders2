package moon.invaders.parts;

import abyss.lunarengine.APart;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Invaders2Main;
import moon.invaders.Level;
import moon.invaders.LevelStatic;
import moon.invaders.Resources;
import moon.invaders.Textwriter;
import moon.invaders.comet.CometController;
import moon.invaders.enemy.EnemyController;
import moon.invaders.enemyexplosion.EnemyExplosionController;
import moon.invaders.enemyshot.EnemyShotsController;
import moon.invaders.player.Player1Controller;
import moon.invaders.playershot.Player1ShotsController;

public class PartGame extends APart{
	public static int visibleBackgroundY;

	private static int backgroundOffset=0;

	public static Player1ShotsController player1ShotsController;
	public static EnemyShotsController enemyShotsController;
	public static CometController cometsController;
	public static EnemyController enemyController;
	public static Player1Controller player1Controller;
	public static EnemyExplosionController enemyExplosionController;

	public int counterBegin;
	public static int backgroundScrollSpeed=1;
	private StringBuffer sbBottomText;
	public static boolean blink=false;
	public static boolean blinkbonuslive=false;
	public static boolean showFastshot=false;
	public static boolean showPowershot=false;

	
	public static void main(String[] args){
		try{
			Screen.setScreensize(Invaders2Main.screenWidth,Invaders2Main.screenHeight);
			LunarEngine.initializeEngine();
			GameController.initialize();
			APart partGame=new PartGame(false);
			partGame.precalc();
			LunarEngine.setActivePart(partGame);
			LunarEngine.startEngine();
		}catch(Throwable throwable){
			LunarEngine.throwableHandler(throwable);
		}
	}

	public PartGame() {}
	
	public PartGame(boolean dummy) {
		visibleBackgroundY=Screen.screenSizeY-16;
		enemyController=new EnemyController();
		player1Controller=new Player1Controller();
		player1ShotsController=new Player1ShotsController();
		enemyShotsController=new EnemyShotsController(LevelStatic.bobEnemyshot);
		cometsController=new CometController(LevelStatic.bobComets);
		enemyExplosionController=new EnemyExplosionController(LevelStatic.bobExplosion1,LevelStatic.bobExplosion2);

		Bob.setClippingArea(0, 0, Screen.screenSizeX, visibleBackgroundY);
	}
	
	@Override
	public void precalc(){
		cometsController.reset();
		enemyShotsController.reset();
		enemyController.reset();
		player1ShotsController.reset();
		enemyExplosionController.reset();
		
		Level.activateLevel(Level.getLevel(GameController.level));

		counterBegin=120;
		blink=false;
		blinkbonuslive=false;
		showFastshot=false;
		showPowershot=false;
		refreshBottomTextline();
		writeBottomTextline();
		Level.activateLevel(Level.getLevel(GameController.level));
	}

	@Override
	public void vbi(){
		player1Controller.vbi();
		player1ShotsController.vbi();
		if(counterBegin==0){
			enemyShotsController.vbi();
			enemyController.vbi();
			cometsController.vbi();
			enemyExplosionController.vbi();
			enemyController.detectEnemyShot();
		}else{
			if(--counterBegin==0){
				enemyController.launch();
			}
		}
	}

	@Override
	public void worker1(){
		Bob.screendataToWork=LunarEngine.screendataToWork;
		player1Controller.render();
		player1ShotsController.render();
		cometsController.render();
		enemyShotsController.render();
		enemyController.render();
		enemyExplosionController.render();
		writeBottomTextline();
	}

	private int liveChangeBlinkDelay=15;
	private int liveChangeBlinkDelayCount=liveChangeBlinkDelay;
	private int liveBlinkDelta=1;
	
	public void refreshBottomTextline(){
		sbBottomText=new StringBuffer();
		sbBottomText.append(" LEVEL "+(GameController.level<10?"0":"")+GameController.level);
		sbBottomText.append("    GALAXY "+(GameController.galaxy<10?"0":"")+GameController.galaxy);
		sbBottomText.append("      INVADERS 2.0");
		sbBottomText.append("                                                                ".substring(sbBottomText.length()));
		if(sbBottomText.length()!=64){
			throw new RuntimeException("Bottom textline length overflow: "+sbBottomText.length());
		}
		
		int lives=GameController.lives;
		if(blink){
			if(!blinkbonuslive){
				lives++;
			}
			lives-=liveBlinkDelta;
			if(--liveChangeBlinkDelayCount==0){
				liveChangeBlinkDelayCount=liveChangeBlinkDelay;
				liveBlinkDelta=1-liveBlinkDelta;
			}
		}
		
		for(int i=64-(lives-1);i<64;i++){
			sbBottomText.setCharAt(i,'#');
		}
	}
	
	public void writeBottomTextline(){
		if(sbBottomText!=null) {
			Textwriter.writeText(sbBottomText.toString(), 0, visibleBackgroundY);
		}
	}
	
	@Override
	public void worker2(){
		final int[] render2_screendataToReset=LunarEngine.screendataToReset;
		int part1End=backgroundOffset+visibleBackgroundY;
		int render2_part1EndIndex=visibleBackgroundY;

		if(part1End>=Resources.BACKGROUND_HEIGHT){
			render2_part1EndIndex=Resources.BACKGROUND_HEIGHT-backgroundOffset;
		}
		int pixel;
		int offsetBackground;
		int offsetScreen;
		int y=0;
		for(;y<render2_part1EndIndex;y++){
			offsetBackground=(backgroundOffset+y)*Resources.BACKGROUND_WIDTH;
			offsetScreen=y*Screen.screenSizeX;
			for(int x=0;x<Resources.BACKGROUND_WIDTH;x++){
				pixel=LevelStatic.screendataBackground[offsetBackground+x];
				render2_screendataToReset[offsetScreen+x]=pixel;
			}
		}
		for(;y<visibleBackgroundY;y++){
			offsetBackground=(backgroundOffset+y-Resources.BACKGROUND_HEIGHT)*Resources.BACKGROUND_WIDTH;
			offsetScreen=y*Screen.screenSizeX;
			for(int x=0;x<Resources.BACKGROUND_WIDTH;x++){
				pixel=LevelStatic.screendataBackground[offsetBackground+x];
				render2_screendataToReset[offsetScreen+x]=pixel;
			}
		}
		backgroundOffset-=backgroundScrollSpeed;
		if(backgroundOffset<0){
			backgroundOffset+=Resources.BACKGROUND_HEIGHT;
		}
	}

}
