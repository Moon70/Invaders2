package moon.invaders.player;

import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Resources;
import moon.invaders.Sound;
import moon.invaders.comet.CometController;
import moon.invaders.enemyshot.EnemyShotsController;
import moon.invaders.parts.PartGame;

public class Player1Controller {
	public static Player1Bob bobPlayerShip;

	public Player1Controller() {
		bobPlayerShip=new Player1Bob(Resources.getPlayerShipImageData());
		reset();
		bobPlayerShip.enabled=true;
	}
	
	public void vbi() {
		bobPlayerShip.move();	
		detectPlayerShot();//LED
		if(LunarEngine.button1){
			LunarEngine.button1=false;
			if(PartGame.player1ShotsController.shoot((bobPlayerShip.getPosX()+13)<<Bob.SHIFT,(bobPlayerShip.getPosY()-18)<<Bob.SHIFT)) {
				Sound.playerShoot();
			}
		}
	}
	
	public void render() {
		bobPlayerShip.render();
	}
	
	public void reset() {
		bobPlayerShip.setPos(Screen.screenSizeX/2,Screen.screenSizeY-16-bobPlayerShip.bobSizeX);
	}
	
	private void detectPlayerShot(){
		if(detectPlayerCollision(CometController.bobs)){
			return;
		}
		if(detectPlayerCollision(EnemyShotsController.bobs)){
			return;
		}
	}

	private boolean detectPlayerCollision(Bob[] bobs){
		final int playerXcenterShifted=bobPlayerShip.posXshifted+bobPlayerShip.bobHalfSizeXshifted;
		final int playerYcenterShifted=bobPlayerShip.posYshifted+bobPlayerShip.bobHalfSizeYshifted;
		for(int i=0;i<bobs.length;i++){
			if(bobs[i].enabled){
				int bobXhalfsizeShifted=bobs[i].bobHalfSizeXshifted;
				final int maxDeltaX=bobPlayerShip.bobHalfSizeXshifted+bobXhalfsizeShifted;
				int bobXcenterShifted=bobs[i].posXshifted+bobXhalfsizeShifted;
				int deltaX=playerXcenterShifted-bobXcenterShifted;
				if(deltaX>maxDeltaX || deltaX<-maxDeltaX){
					continue;
				}
				int bobYhalfsizeShifted=bobs[i].bobHalfSizeYshifted;
				final int maxDeltaY=bobPlayerShip.bobHalfSizeYshifted+bobYhalfsizeShifted;
				int bobYcenterShifted=bobs[i].posYshifted+bobYhalfsizeShifted;
				int deltaY=playerYcenterShifted-bobYcenterShifted;
				if(deltaY>maxDeltaY || deltaY<-maxDeltaY){
					continue;
				}
				GameController.playerDied();
				return true;
			}
		}
		return false;
	}

}
