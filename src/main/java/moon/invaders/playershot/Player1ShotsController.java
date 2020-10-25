package moon.invaders.playershot;

import moon.invaders.GameController;
import moon.invaders.LevelStatic;

public class Player1ShotsController{
	public static final int MAX_SHOTS=8;
	public int shotIndex=0;

	public PlayerShotBob[] bobs=new PlayerShotBob[MAX_SHOTS];
	
	public Player1ShotsController(){
		for(int i=0;i<MAX_SHOTS;i++){
			bobs[i]=new PlayerShotBob(LevelStatic.bobPlayershot1);
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

	public boolean shoot(int startXshifted,int startYshifted){
		if(bobs[shotIndex].enabled){
			return false;
		}
		bobs[shotIndex].posXshifted=startXshifted;
		bobs[shotIndex].posYshifted=startYshifted;
		bobs[shotIndex].enabled=true;
		if(GameController.playershot_power) {
			bobs[shotIndex].bobdata=LevelStatic.bobPlayershot2.bobdata;
			bobs[shotIndex].hitCount=2;
		}else {
			bobs[shotIndex].bobdata=LevelStatic.bobPlayershot1.bobdata;
			bobs[shotIndex].hitCount=1;
		}
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