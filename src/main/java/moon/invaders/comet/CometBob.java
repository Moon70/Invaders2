package moon.invaders.comet;

import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;

public class CometBob extends Bob {

	public CometBob(Bob bob) {
		super(bob);
	}

	private int speed;
	
	@Override
	public void vbi(){
		super.vbi();
		if(posYshifted<(Screen.screenSizeY<<SHIFT)){
			posYshifted+=speed;
		}else{
			enabled=false;
		}
	}
	
	public void setSpeed(int speedInPixelPerFrame){
		this.speed=speedInPixelPerFrame<<SHIFT;
	}
	
}
