package moon.invaders;
import abyss.lunarengine.APart;
import abyss.lunarengine.Commander;
import abyss.lunarengine.ICommanderAction;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import moon.invaders.parts.PartGameOver;
import moon.invaders.parts.PartBoom;
import moon.invaders.parts.PartGame;
import moon.invaders.parts.PartGetReady;
import moon.invaders.parts.PartLoader;
import moon.invaders.parts.PartSwitchBackground;
import moon.invaders.parts.PartTitle;
import moon.invaders.parts.PartWellDone;

/*
 * Invaders 2.
 * Version 1 created in 1991/1992 on Amiga 500 (68k assembly).
 * This Java version 2 created in summer 2017.
 * 
 * Credits:
 * --------
 * Music by Ewald Winkler
 *    created for Amiga version
 *    sampled via WinUAE by Thomas Mattel, sorry could not get a perfect loop
 * 
 * Backgrounds by NASA/Hubble Space Telescope
 * 
 * Title picture by Ewald Winkler
 *    created for Amiga version, using a black background
 *    poorly cut-out for this Java version by Thomas Mattel
 *    
 * Some spaceships by Ewald Winkler
 * Some spaceships by Michael Kopf (rest in peace, Mike)
 * Some spaceships by Alexander Mattel
 * Most spaceships and remaining graphics by Thomas Mattel
 * 
 * Sounds collected by unknown people, thanks for putting them on public domain! :-)
 * 
 * Game code and LunarEngine code by Thomas Mattel
 */
public class Invaders2Main{
	public static int screenWidth=1024;
	public static int screenHeight=768;

	public static void main(String[] args){
		try{
			LunarEngine.clientName="Invaders 2.0.7 beta";
			System.out.println(LunarEngine.clientName);
			Screen.setScreensize(screenWidth, screenHeight);
			LunarEngine.hideMousePointer=true;
			LunarEngine.initializeEngine();
			GameController.initialize();
			final APart partTitle=new PartTitle();
			final APart partLoader=new PartLoader();
			final APart partGetReady=new PartGetReady();
			final APart partGame=new PartGame(false);
			final APart partBoom=new PartBoom();
			final APart partSwitchBackground=new PartSwitchBackground();
			final APart partWellDone=new PartWellDone();

			GameController.command_Title=Commander.addCommanderAction(0,new ICommanderAction(){
				public void doAction(Object object){
					partTitle.precalc();
					LunarEngine.setActivePart(partTitle);
					Sound.backLoopEnd();
					Sound.musicStart();
				}
			});
			GameController.command_Loading_GetReady_Game=			createCommandSequence_Loading_GetReady_Game(partLoader,partGetReady, partGame);
			GameController.command_Loading_SwitchBackground_Game=	createCommandSequence_Loading_SwitchBackground_Game(partLoader,partSwitchBackground, partGame);
			GameController.command_BoomAndGetReady=					createCommandSequence_Boom_GetReady_Game(partBoom, partGetReady, partGame);
			GameController.command_BoomAndGameOver=					createCommandSequence_Boom_GameOver_Title(partBoom, partGetReady, partTitle);
			GameController.command_Welldone=						createCommandSequence_Welldone_Title(partWellDone, partTitle);
			GameController.command_Loading=Commander.addCommanderAction(0,new ICommanderAction(){
				public void doAction(Object object){
					LunarEngine.setActivePart(partLoader);
				}
			});

			Commander.start(0);
			LunarEngine.startEngine();
		}catch(Throwable throwable){
			LunarEngine.throwableHandler(throwable);
		}
	}

	private static int createCommandSequence_Boom_GetReady_Game(final APart partBoom,final APart partGetReady,final APart partGame){
		int firstCommand=Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partBoom);
				partBoom.precalc();
			}
		});
		Commander.addCommanderAction(150,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partGetReady);
				partGetReady.precalc();
				PartGame.player1Controller.reset();
			}
		});
		Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partGame);
				partGame.precalc();
			}
		});
		return firstCommand;
	}

	private static int createCommandSequence_Boom_GameOver_Title(final APart partBoom,final APart partGetReady,final APart partTitle){
		int firstCommand=Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partBoom);
				partBoom.precalc();
				Sound.backLoopEnd();
			}
		});
		final APart partGameOver=new PartGameOver();
		Commander.addCommanderAction(300,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partGameOver);
				partGameOver.precalc();
			}
		});
		Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				partTitle.precalc();
				LunarEngine.setActivePart(partTitle);
				Sound.musicStart();
			}
		});
		return firstCommand;
	}

	private static int createCommandSequence_Welldone_Title(final APart partWelldone,final APart partTitle){
		int firstCommand=Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partWelldone);
				partWelldone.precalc();
			}
		});
		Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partTitle);
				Sound.backLoopEnd();
				Sound.musicStart();
			}
		});
		return firstCommand;
	}

	private static int createCommandSequence_Loading_GetReady_Game(final APart partLoading,final APart partGetReady,final APart partGame){
		int firstCommand=Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partLoading);
				partLoading.precalc();
			}
		});
		Commander.addCommanderAction(120,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partGetReady);
				partGetReady.precalc();
				PartGame.cometsController.reset();
				PartGame.enemyShotsController.reset();
				PartGame.enemyController.reset();
			}
		});
		Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partGame);
				partGame.precalc();
			}
		});
		return firstCommand;
	}

	private static int createCommandSequence_Loading_SwitchBackground_Game(final APart partLoading,final APart partSwitchBackground,final APart partGame){
		int firstCommand=Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partLoading);
				partLoading.precalc();
			}
		});
		Commander.addCommanderAction(60*10,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partSwitchBackground);
				partSwitchBackground.precalc();
			}
		});
		Commander.addCommanderAction(0,new ICommanderAction(){
			public void doAction(Object object){
				LunarEngine.setActivePart(partGame);
				partGame.precalc();
			}
		});
		return firstCommand;
	}

}
