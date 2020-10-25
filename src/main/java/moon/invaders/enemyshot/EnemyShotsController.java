package moon.invaders.enemyshot;

import abyss.lunarengine.gfx.Bob;
import moon.invaders.Level;

public class EnemyShotsController{
	private static final int MAX_SHOTS=25;
	private int shotIndex=0;
	
	private double shoot_deltaX;
	private double shoot_deltaY;
	private double shoot_r;
	
	public static EnemyShotBob[] bobs;
	
	public EnemyShotsController(Bob bob){
		bobs=new EnemyShotBob[MAX_SHOTS];
		for(int i=0;i<MAX_SHOTS;i++){
			bobs[i]=new EnemyShotBob(bob);
		}
	}

	public void vbi(){
		for(int i=0;i<MAX_SHOTS;i++){
			if(bobs[i].enabled){
				bobs[i].vbi();
			}
		}
	}

	public void render(){
		for(int i=0;i<MAX_SHOTS;i++){
			bobs[i].render();
		}
	}

	public boolean shoot(int startXshifted,int startYshiftet,int endXshifted,int endYshifted,int speed,int type){
		if(bobs[shotIndex].enabled){
			return false;
		}
		bobs[shotIndex].posXshifted=startXshifted;
		bobs[shotIndex].posYshifted=startYshiftet;
		
		if(type==Level.ENEMYSHOOT_DOWN) {
			bobs[shotIndex].posXdelta=0;
			bobs[shotIndex].posYdelta=speed;
			bobs[shotIndex].movecount=(endYshifted-startYshiftet)/speed+5;
		}else if(type==Level.ENEMYSHOOT_TOPLAYERSHIP) {
			shoot_deltaX=endXshifted-startXshifted;
			shoot_deltaY=endYshifted-startYshiftet;
			shoot_r=Math.sqrt(shoot_deltaX*shoot_deltaX+shoot_deltaY*shoot_deltaY+0.5);
			int s=(int)(shoot_r/speed);
			bobs[shotIndex].posXdelta=(int)(shoot_deltaX/s);
			bobs[shotIndex].posYdelta=(int)(shoot_deltaY/s);
			bobs[shotIndex].movecount=s+5;
		}else {
			System.err.println("illegal enemy shoot type: "+type);
			return false;
		}

		bobs[shotIndex].enabled=true;
		if(++shotIndex == MAX_SHOTS){
			shotIndex=0;
		}
		return true;	
	}
	
	public void reset(){
		for(int i=0;i<MAX_SHOTS;i++){
			bobs[i].enabled=false;
		}
	}

}
