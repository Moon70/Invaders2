package moon.invaders.enemyshot;

import abyss.lunarengine.gfx.Bob;

public class EnemyShotBob extends Bob {
	public int posXdelta;
	public int posYdelta;
	public int movecount;
	
	public EnemyShotBob(Bob bob) {
		super(bob);
	}

	@Override
	public void vbi(){
		super.vbi();
		posXshifted+=posXdelta;
		posYshifted+=posYdelta;
		if(--movecount==0){
			enabled=false;
		}
	}
	
}
