package moon.invaders.parts;

import abyss.lunarengine.APart;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import moon.invaders.GameController;
import moon.invaders.Invaders2Main;
import moon.invaders.Level;
import moon.invaders.LevelStatic;
import moon.invaders.MovementCreator;
import moon.invaders.Sound;
import moon.invaders.Textwriter;

public class PartSwitchBackground extends PartGame{
	private int[] scrollSpeedMovement;
	private int scrollIndex;

	public static void main(String[] args){
		try{
			Screen.setScreensize(Invaders2Main.screenWidth,Invaders2Main.screenHeight);
			LunarEngine.initializeEngine();
			GameController.initialize();
			APart partSwitchBackground=new PartSwitchBackground();
			new PartGame(true).precalc();
			partSwitchBackground.precalc();
			
			Level level=Level.getLevel(2);
			level.prepareLevel();
			Level.activateLevel(level);

			level=Level.getLevel(1);
			level.prepareLevel();

			LunarEngine.setActivePart(partSwitchBackground);
			LunarEngine.startEngine();
		}catch(Throwable throwable){
			LunarEngine.throwableHandler(throwable);
		}
	}

	public PartSwitchBackground() {
		MovementCreator movementCreator=new MovementCreator(0);
		movementCreator.parse("start:0,1#splineCV:310,-9,300,200,1#splineCV:300,-9,360,1,2");
		scrollSpeedMovement=movementCreator.createYArray();
	}
	
	@Override
	public void precalc(){
		scrollIndex=0;
		Sound.galaxyFlight();
	}

	@Override
	public void vbi(){
		if(scrollIndex<scrollSpeedMovement.length){
			backgroundScrollSpeed=scrollSpeedMovement[scrollIndex++];
		}
		if(scrollIndex==382){
			Level level=Level.getLevel(GameController.level);
			LevelStatic.screendataBackground=level.leveldata_BackgroundImagedata;
			Level.activateLevel(Level.getLevel(GameController.level));
			Sound.backLoopStart();
		}
	}

	@Override
	public void worker1(){
		String s="PREPARE FOR NEXT GALAXY";
		Textwriter.writeTextDoubleY(s, (Screen.screenSizeX-(s.length()*16))/2, 300);
		if(blinkbonuslive){
			String text="BONUS LIVE #";
			Textwriter.writeTextDoubleY(text, (Screen.screenSizeX-(text.length()*16))/2, 350);
		}else if(showFastshot) {
			String text="BONUS: SHOT SPEED";
			Textwriter.writeTextDoubleY(text, (Screen.screenSizeX-(text.length()*16))/2, 350);
		}else if(showPowershot) {
			String text="BONUS: POWER SHOT";
			Textwriter.writeTextDoubleY(text, (Screen.screenSizeX-(text.length()*16))/2, 350);
		}
		refreshBottomTextline();
		writeBottomTextline();
	}

}
