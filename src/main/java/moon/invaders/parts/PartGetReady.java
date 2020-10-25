package moon.invaders.parts;

import abyss.lunarengine.APart;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Invaders2Main;
import moon.invaders.Level;
import moon.invaders.Textwriter;
import moon.invaders.player.Player1Controller;

public class PartGetReady extends PartGame{
	
	public static void main(String[] args){
		try{
			Screen.setScreensize(Invaders2Main.screenWidth,Invaders2Main.screenHeight);
			LunarEngine.initializeEngine();
			GameController.initialize();
			APart getReadyPart=new PartGetReady();
			new PartGame(true).precalc();
			getReadyPart.precalc();
			LunarEngine.setActivePart(getReadyPart);
			LunarEngine.startEngine();
		}catch(Throwable throwable){
			LunarEngine.throwableHandler(throwable);
		}
	}

	@Override
	public void precalc(){
		Level.activateLevel(Level.getLevel(GameController.level));
		Player1Controller.bobPlayerShip.enabled=true;
	}

	public void vbi(){
		player1Controller.vbi();
	}

	@Override
	public void worker1(){
		Bob.screendataToWork=LunarEngine.screendataToWork;
		player1Controller.render();
		String text="GET READY";
		Textwriter.writeTextDoubleY(text, (Screen.screenSizeX-(text.length()*16))/2, 300);
		refreshBottomTextline();
		writeBottomTextline();
	}
	
}
