package moon.invaders.enemy;

import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Level;
import moon.invaders.LevelStatic;
import moon.invaders.Sound;
import moon.invaders.parts.PartGame;
import moon.invaders.player.Player1Controller;
import moon.invaders.playershot.PlayerShotBob;

public class EnemyController{
	private static final int MAX_ENEMIES=200;
	private static EnemyBob[] enemyBobs;
	private static boolean active;
	private static int enemyIndex;

	private static int[] enemyShootBeginDelay;
	private static int[] enemyShootDelay;
	private static int enemiesToShoot;
	private static int[] enemyShootIndex;

	private int[] moveBeginDelay;
	private static int[] maxEnemies;
	public static boolean[] moveInLaneEnabled;
	private int[] currentMoveNextDelay;

	private Bob[] bobData;

	private static int attackDelay;
	private static int attackIndex;
	private static int attachShootDelayCount;

	private static int playerShotBobIndex;
	private static int enemyBobIndex;

	public EnemyController() {
		enemyBobs=new EnemyBob[MAX_ENEMIES];
	}

	public void reset(){
		enemyIndex=0;
		enemyShootBeginDelay=new int[LevelStatic.lanesCount];
		enemyShootDelay=new int[LevelStatic.lanesCount];
		enemyShootIndex=new int[LevelStatic.lanesCount];
		moveBeginDelay=new int[LevelStatic.lanesCount];
		currentMoveNextDelay=new int[LevelStatic.lanesCount];
		maxEnemies=new int[LevelStatic.lanesCount];
		moveInLaneEnabled=new boolean[LevelStatic.lanesCount];
		enemiesToShoot=0;
		for(int i=0;i<LevelStatic.lanesCount;i++){
			moveBeginDelay[i]=Level.movementStartDelay[i];
			currentMoveNextDelay[i]=1;
			maxEnemies[i]=Level.bobCount[i];
			enemiesToShoot+=Level.bobCount[i];
			moveInLaneEnabled[i]=true;
			enemyShootBeginDelay[i]=Level.enemyShootStartDelay[i];
			enemyShootDelay[i]=Level.enemyShootNextDelay[i];
			enemyShootIndex[i]=0;
		}
		bobData=Level.bobEnemy;
		for(int i=0;i<MAX_ENEMIES;i++){
			enemyBobs[i]=new EnemyBob(bobData[0]);
		}
		attackDelay=LevelStatic.attackStartDelay;
		attackIndex=0;
		attachShootDelayCount=LevelStatic.attackShootDelay;
	}

	public void vbi(){ 
		if(!active){
			return;
		}
		if(enemyIndex<MAX_ENEMIES){
			for(int lane=0;lane<LevelStatic.lanesCount;lane++){
				if(moveBeginDelay[lane]>0){
					moveBeginDelay[lane]--;
				}else{
					if(maxEnemies[lane]>0 && --currentMoveNextDelay[lane]==0){
						currentMoveNextDelay[lane]=Level.movementNextDelay[lane];
						maxEnemies[lane]--;
						enemyBobs[enemyIndex].lane=lane;
						enemyBobs[enemyIndex].bobdata=bobData[lane].bobdata;
						enemyBobs[enemyIndex].animType=bobData[lane].animType;
						enemyBobs[enemyIndex].animdelay=bobData[lane].animdelay;
						enemyBobs[enemyIndex].bobSizeX=bobData[lane].bobSizeX;
						enemyBobs[enemyIndex].bobSizeY=bobData[lane].bobSizeY;
						enemyBobs[enemyIndex].moveIndex=0;
						enemyBobs[enemyIndex].leftrightmoveRange=80;
						enemyBobs[enemyIndex].leftrightmovePos=enemyBobs[enemyIndex].leftrightmoveRange/2;
						enemyBobs[enemyIndex].leftrightmoveValue=(lane&1)==0?1:-1;
						enemyBobs[enemyIndex].movementtype=Level.bobMoveType[lane];
						enemyBobs[enemyIndex].bobNumber=Level.bobNumber[lane];
						enemyBobs[enemyIndex].attackPointsCount=LevelStatic.attackMovePoints;
						enemyBobs[enemyIndex++].enabled=true;
					}
				}
			}
		}

		if(--attackDelay==0) {
			attackDelay=LevelStatic.attackNextDelay;
			if(attackIndex<MAX_ENEMIES) {
				while(attackIndex<MAX_ENEMIES) {
					if(enemyBobs[attackIndex].enabled && enemyBobs[attackIndex].movementtype>=EnemyBob.MOVETYPE_SURF_LANE_THEN_ATTACK) {
						enemyBobs[attackIndex].movementtype=EnemyBob.MOVETYPE_SURF_LANE_THEN_ATTACK;
						enemyBobs[attackIndex].attack();
						attackIndex++;
						break;
					}
					attackIndex++;
				}
			}
		}
		if(--attachShootDelayCount==0) {
			attachShootDelayCount=LevelStatic.attackShootDelay;
			for(int i=0;i<MAX_ENEMIES;i++){
				if(enemyBobs[i].enabled && enemyBobs[i].movementtype>EnemyBob.MOVETYPE_SURF_LANE_THEN_ATTACK) {
					PartGame.enemyShotsController.shoot(
							enemyBobs[i].posXshifted,
							enemyBobs[i].posYshifted,
							(Player1Controller.bobPlayerShip.getPosX()+13)<<Bob.SHIFT,
							Player1Controller.bobPlayerShip.getPosY()<<Bob.SHIFT,
							LevelStatic.attackShootSpeed<<Bob.SHIFT,
							Level.ENEMYSHOOT_TOPLAYERSHIP);
					Sound.enemyShoot(enemyBobs[i].bobNumber);
					break;
				}
			}
		}

		for(int i=0;i<MAX_ENEMIES;i++){
			if(enemyBobs[i].enabled){
				enemyBobs[i].vbi();
			}
		}

		for(int lane=0;lane<LevelStatic.lanesCount;lane++){
			if(enemyShootBeginDelay[lane]!=0){
				enemyShootBeginDelay[lane]--;
			}else{
				if(--enemyShootDelay[lane]==0){
					enemyShootDelay[lane]=Level.enemyShootNextDelay[lane];
					for(int i=0;i<MAX_ENEMIES;i++){
						if(++enemyShootIndex[lane]==MAX_ENEMIES){
							enemyShootIndex[lane]=0;
						}
						if(enemyBobs[enemyShootIndex[lane]].lane==lane && enemyBobs[enemyShootIndex[lane]].enabled){
							if(PartGame.enemyShotsController.shoot(
									enemyBobs[enemyShootIndex[lane]].posXshifted,
									enemyBobs[enemyShootIndex[lane]].posYshifted,
									(Player1Controller.bobPlayerShip.getPosX()+13)<<Bob.SHIFT,
									Player1Controller.bobPlayerShip.getPosY()<<Bob.SHIFT,
									Level.enemyShootSpeed[lane],
									Level.enemyShootType[lane])){
								Sound.enemyShoot(Level.bobNumber[lane]);
							}
							break;
						}
					}
				}
			}
		}
	}

	public void render(){
		for(int i=0;i<MAX_ENEMIES;i++){
			enemyBobs[i].render();
		}
	}

	public void launch() {
		active=true;
	}

	public void detectEnemyShot(){
		final PlayerShotBob[] playerShotBobs=PartGame.player1ShotsController.bobs;
		Bob bobPlayerShot;
		Bob bobEnemy;
		checkShots:
			for(playerShotBobIndex=0;playerShotBobIndex<playerShotBobs.length;playerShotBobIndex++){
				if(playerShotBobs[playerShotBobIndex].enabled){
					bobPlayerShot=playerShotBobs[playerShotBobIndex];
					final int playerShotXcenterShifted=bobPlayerShot.posXshifted+bobPlayerShot.bobHalfSizeXshifted;
					final int playerShotYcenterShifted=bobPlayerShot.posYshifted+bobPlayerShot.bobHalfSizeYshifted;
					for(enemyBobIndex=0;enemyBobIndex<enemyBobs.length;enemyBobIndex++){
						bobEnemy=enemyBobs[enemyBobIndex];
						if(bobEnemy.enabled) {
							int bobEnemyXhalfsizeShifted=bobEnemy.bobHalfSizeXshifted;
							final int maxDeltaX=bobPlayerShot.bobHalfSizeXshifted+bobEnemyXhalfsizeShifted;
							int bobEnemyXcenterShifted=bobEnemy.posXshifted+bobEnemyXhalfsizeShifted;
							int deltaX=playerShotXcenterShifted-bobEnemyXcenterShifted;
							if(deltaX>maxDeltaX || deltaX<-maxDeltaX){
								continue;
							}
							int bobEnemyYhalfsizeShifted=bobEnemy.bobHalfSizeYshifted;
							final int maxDeltaY=bobPlayerShot.bobHalfSizeYshifted+bobEnemyYhalfsizeShifted;
							int bobEnemyYcenterShifted=bobEnemy.posYshifted+bobEnemyYhalfsizeShifted;
							int deltaY=playerShotYcenterShifted-bobEnemyYcenterShifted;
							if(deltaY>maxDeltaY || deltaY<-maxDeltaY){
								continue;
							}
							PartGame.enemyExplosionController.explode(enemyBobs[enemyBobIndex].posXshifted,enemyBobs[enemyBobIndex].posYshifted);
							if(--playerShotBobs[playerShotBobIndex].hitCount==0) {
								playerShotBobs[playerShotBobIndex].enabled=false;
							}
							enemyBobs[enemyBobIndex].enabled=false;
							Sound.enemyExplode();
							if(--enemiesToShoot==0){
								GameController.levelDone();
							}
							continue checkShots;
						}
					}
				}
			}
	}

}
