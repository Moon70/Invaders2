package moon.invaders.playershot;

import abyss.lunarengine.gfx.Bob;

public class PlayerShotBob extends Bob {
	public static final int SPEED1=10<<SHIFT;
	public static final int SPEED2=15<<SHIFT;
	public static int speedShifted;
	public int hitCount;
	
	public PlayerShotBob(Bob bob) {
		super(bob);
	}

	@Override
	public void vbi(){
		super.vbi();
		if(posYshifted>speedShifted){
			posYshifted-=speedShifted;
		}else{
			enabled=false;
		}
	}
	
}
