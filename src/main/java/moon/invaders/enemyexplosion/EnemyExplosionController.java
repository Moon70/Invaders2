package moon.invaders.enemyexplosion;

import abyss.lunarengine.gfx.Bob;

public class EnemyExplosionController{
	public static final int MAX_EXPLOSIONS=25;
	private int explosionIndex=0;
	
	public Bob[] bobs=new Bob[MAX_EXPLOSIONS];
	
	public EnemyExplosionController(Bob bobAnimation1,Bob bobAnimation2){
		for(int i=0;i<MAX_EXPLOSIONS;i++){
			if((i&1)==1){
				bobs[i]=new Bob(bobAnimation1);
			}else{
				bobs[i]=new Bob(bobAnimation2);
			}
		}
	}

	public void vbi() {
		for(int i=0;i<MAX_EXPLOSIONS;i++){
			if(bobs[i].enabled) {
				bobs[i].vbi();
			}
		}
	}
	
	public void render(){
		for(int i=0;i<MAX_EXPLOSIONS;i++){
			bobs[i].render();
		}
	}

	public boolean explode(int posXshifted,int posYshifted){
		if(bobs[explosionIndex].enabled){
			return false;
		}
		bobs[explosionIndex].posXshifted=posXshifted;
		bobs[explosionIndex].posYshifted=posYshifted;
		bobs[explosionIndex].setAnimType(Bob.ANIMTYPE_FORWARD);
		bobs[explosionIndex].enabled=true;
		if(++explosionIndex == MAX_EXPLOSIONS){
			explosionIndex=0;
		}
		return true;	
	}
	
	public void reset(){
		for(int i=0;i<MAX_EXPLOSIONS;i++){
			bobs[i].enabled=false;
		}
	}

}
