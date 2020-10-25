package moon.invaders.player;

import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;

public class Player1Bob extends Bob{
	private static final int ANIMDELAY=3;
	private static final int CENTERFRAME=4;
	private int animDelayCount=ANIMDELAY;
	
	public Player1Bob(Bob bob) {
		super(bob);
		frame=CENTERFRAME;
	}

	public void move(){
		if(LunarEngine.left1){
			if(frame>0 && --animDelayCount==0){
				frame--;
				animDelayCount=ANIMDELAY;
			}
			posXshifted=posXshifted-(6 << SHIFT);
			if(posXshifted>=((Screen.screenSizeX-bobSizeX)<< SHIFT) || posXshifted<0){
				posXshifted=posXshifted+(6 << SHIFT);
			}
		}else if(LunarEngine.right1){
			if(frame<bobdata.length-1 && --animDelayCount==0){
				frame++;
				animDelayCount=ANIMDELAY;
			}
			posXshifted=posXshifted+(6 << SHIFT);
			if(posXshifted>=((Screen.screenSizeX-bobSizeX) << SHIFT) || posXshifted<0){
				posXshifted=posXshifted-(6 << SHIFT);
			}
		}else{
			if(frame<CENTERFRAME && --animDelayCount==0){
				frame++;
				animDelayCount=ANIMDELAY;
			}else if(frame>CENTERFRAME && --animDelayCount==0){
				frame--;
				animDelayCount=ANIMDELAY;
			}
		}
		
	}
	
}
