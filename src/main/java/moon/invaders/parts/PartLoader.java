package moon.invaders.parts;

import abyss.lunarengine.Commander;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Level;

public class PartLoader extends PartGame{
	public static Level levelToPrepare;
	private static Thread thread;
	
	@Override
	public void precalc(){
		//empty method to avoid execution of superclass method
	}

	@Override
	public void vbi(){
		if(thread==null){
			thread=new Thread(){
				public void run(){
					Level.getLevel(GameController.level).prepareLevel();
				}
			};
			thread.start();
		}else{
			if(!thread.isAlive()){
				Commander.skip();
				thread=null;
			}
		}
	}

	@Override
	public void worker1(){
		Bob.screendataToWork=LunarEngine.screendataToWork;
		player1Controller.render();
		refreshBottomTextline();
		writeBottomTextline();
	}

}
