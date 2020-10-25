package moon.invaders;

import abyss.lunarengine.Commander;
import abyss.lunarengine.Screen;
import moon.invaders.parts.PartGame;
import moon.invaders.player.Player1Controller;
import moon.invaders.playershot.PlayerShotBob;

public class GameController {
	public static int command_Title;
	public static int command_Loading_GetReady_Game;
	public static int command_Game;
	public static int command_Loading_SwitchBackground_Game;
	public static int command_Loading;
	public static int command_BoomAndGetReady;
	public static int command_BoomAndGameOver;
	public static int command_Welldone;
	
	public static int status;
	public static int level;
	public static int galaxy;
	
	private static final int MAXLIVES=3;
	public static int lives;
	
	private static boolean playershot_faster;
	public static boolean playershot_power;
	
	private GameController(){}

	public static void initialize(){
		Sound.initialize();
		LevelService.initialize();
		Textwriter.initialize();
		GameController.level=1;
		Level level=Level.getLevel(1);
		level.prepareLevel();//prepare very first level, because title screen needs any background to be loaded
		Level.activateLevel(level);//activate level, because background scroller (LoadLevel) needs Level.screendataBackground to be set
	}
	
	public static void startGame(){
		if(level!=1){
			for(int i=0;i<LevelStatic.screendataBackground.length;i++){
				LevelStatic.screendataBackground[i]=0;
			}
		}
		level=1;
		galaxy=1;
		Commander.jump(command_Loading_GetReady_Game,1,60*10);
		Sound.musicEnd();
		Sound.prepareBackLoop(1);
		Sound.backLoopStart();
		lives=MAXLIVES;
		Player1Controller.bobPlayerShip.setPos(Screen.screenSizeX/2,PartGame.visibleBackgroundY-Player1Controller.bobPlayerShip.bobSizeX);
		PlayerShotBob.speedShifted=PlayerShotBob.SPEED1;
		playershot_faster=false;
		playershot_power=false;
		PlayerShotBob.speedShifted=PlayerShotBob.SPEED1;
	}
	
	public static void levelDone(){
		if(Level.isLastLevel()){
			Commander.jump(GameController.command_Welldone,1,1800);
			return;
		}
		Level levelData=Level.getLevel(++level);
		if(Level.isCurrentBackground(levelData)){
			Commander.jump(GameController.command_Loading_GetReady_Game,90,150);
		}else{
			Commander.jump(GameController.command_Loading_SwitchBackground_Game,90,60*10);
			galaxy++;
			if(!playershot_faster) {
				playershot_faster=true;
				PlayerShotBob.speedShifted=PlayerShotBob.SPEED2;
				PartGame.showFastshot=true;
			}else if(!playershot_power){
				playershot_power=true;
				PartGame.showPowershot=true;
			}else {
				lives++;
				PartGame.blink=true;
				PartGame.blinkbonuslive=true;
			}

		}
	}
	
	public static void playerDied(){
		Sound.playerExplode();
		if(--lives==0){
			Commander.jump(GameController.command_BoomAndGameOver,1,150);
		}else{
			Commander.jump(GameController.command_BoomAndGetReady,1,150);
			PartGame.blink=true;
		}
		playershot_faster=false;
		playershot_power=false;
		PlayerShotBob.speedShifted=PlayerShotBob.SPEED1;
		PartGame.cometsController.reset();
		PartGame.enemyShotsController.reset();
	}
	
}
