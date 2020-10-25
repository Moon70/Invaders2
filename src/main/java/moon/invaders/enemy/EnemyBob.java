package moon.invaders.enemy;

import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.Level;
import moon.invaders.LevelStatic;
import moon.invaders.player.Player1Controller;

public class EnemyBob extends Bob {
	public static final int MOVETYPE_NOMOVE=0;
	public static final int MOVETYPE_SURF_LANE_THEN_STOP=1;
	public static final int MOVETYPE_SURF_LANE_THEN_LEFTRIGHT=2;
	private static final int MOVETYPE_LEFTRIGHT=8;
	public static final int MOVETYPE_SURF_LANE_THEN_ATTACK=9;
	public static final int MOVETYPE_SURF_LANE_THEN_LEFTRIGHT_THEN_ATTACK=10;
	private static final int MOVETYPE_LEFTRIGHT_THEN_ATTACK=11;
	private static final int MOVETYPE_WAIT_THEN_ATTACK=12;
	private static final int MOVETYPE_TOSHIP_TOSHIP=13;
	private static final int MOVETYPE_TOSHIP_TOPOINT=14;
	
	public int bobNumber;
	public int moveIndex;
	public int lane;
	public int movementtype=0;
	
	public int posXdelta;
	public int posYdelta;
	public int movecount;
	
	private int moveEnemyToShipStep;

	public int leftrightmoveRange=0;
	public int leftrightmovePos=leftrightmoveRange/2;
	public int leftrightmoveValue=1;
	
	private int[] randomAttackPositionX = new int[] { 445, 305, 615, 720, 388, 872, 714, 296, 432, 691, 288, 155, 538,
			925, 748, 975, 575, 837, 633, 932, 680, 404, 108, 311, 732, 887, 729, 553, 774, 672, 801, 947, 760, 914,
			648, 457, 924, 709, 938, 689, 862, 615, 413, 745, 338, 153, 346, 604, 498, 919, 507, 694, 804, 391, 668,
			844, 644, 318, 149, 249, 493, 966, 545, 765, 435, 687, 356, 548, 961, 637, 900, 600, 785, 642, 954 };
	private int[] randomAttackPositionY = new int[] { 220, 83, 222, 62, 294, 61, 207, 75, 254, 63, 232, 98, 275, 127, 9,
			110, 272, 84, 202, 27, 162, 48, 255, 27, 267, 24, 283, 143, 27, 234, 113, 213, 35, 286, 112, 257, 27, 211,
			78, 284, 171, 13, 129, 286, 14, 200, 73, 195, 65, 254, 74, 269, 49, 163, 273, 35, 184, 1, 183, 71, 277, 70,
			235, 45, 278, 69, 252, 134, 295, 7, 247, 33, 273, 107, 242 };
	private static int randomIndex;

	private static int attackspeed = 150;
	public int attackPointsCount;
	
	public EnemyBob(Bob bob) {
		super(bob);
		for(int i=0;i<randomAttackPositionX.length;i++) {
			randomAttackPositionX[i]=randomAttackPositionX[i]<<SHIFT;
			randomAttackPositionY[i]=randomAttackPositionY[i]<<SHIFT;
		}
	}

	@Override
	public void vbi(){
		super.vbi();
		switch(movementtype){
		case MOVETYPE_NOMOVE:
			break;
		case MOVETYPE_SURF_LANE_THEN_STOP:
		case MOVETYPE_SURF_LANE_THEN_LEFTRIGHT:
		case MOVETYPE_SURF_LANE_THEN_ATTACK:
		case MOVETYPE_SURF_LANE_THEN_LEFTRIGHT_THEN_ATTACK:
			if(EnemyController.moveInLaneEnabled[lane]){
				if((moveIndex=Level.movement[lane].nextPosition(moveIndex))>=0){//goto next movement position
					posXshifted=Level.movement[lane].getX();
					posYshifted=Level.movement[lane].getY();
				}else{//if there's no new position (no loop) then stop movement of all bobs in this lane
					EnemyController.moveInLaneEnabled[lane]=false;
				}
			}else{//if lane movement disabled, check other movement types
				if(movementtype==MOVETYPE_SURF_LANE_THEN_LEFTRIGHT){
					movementtype=MOVETYPE_LEFTRIGHT;
				}else if(movementtype==MOVETYPE_SURF_LANE_THEN_LEFTRIGHT_THEN_ATTACK) {
					movementtype=MOVETYPE_LEFTRIGHT_THEN_ATTACK;
				}else if(movementtype==MOVETYPE_SURF_LANE_THEN_ATTACK){
					movementtype=MOVETYPE_WAIT_THEN_ATTACK;
				}else{
					movementtype=MOVETYPE_NOMOVE;
				}
			}
			break;
		case MOVETYPE_TOSHIP_TOSHIP:
			if(--moveEnemyToShipStep>0){
				posXshifted+=posXdelta;
				posYshifted+=posYdelta;
			}else {
				int endX=Player1Controller.bobPlayerShip.posXshifted;
				int endY=(Screen.screenSizeY-150)<<Bob.SHIFT;
				posXdelta=(endX-posXshifted)/attackspeed;
				posYdelta=(endY-posYshifted)/attackspeed;
				movementtype=MOVETYPE_TOSHIP_TOPOINT;
				moveEnemyToShipStep=attackspeed;
			}
			break;
		case MOVETYPE_TOSHIP_TOPOINT:
			if(--moveEnemyToShipStep>0){
				posXshifted+=posXdelta;
				posYshifted+=posYdelta;
			}else {
				if(++randomIndex==randomAttackPositionX.length) {
					randomIndex=0;
				}
				int endX=randomAttackPositionX[randomIndex];
				int endY=randomAttackPositionY[randomIndex];
				posXdelta=(endX-posXshifted)/attackspeed;
				posYdelta=(endY-posYshifted)/attackspeed;
				moveEnemyToShipStep=attackspeed;
				if(--attackPointsCount==0) {
					attackPointsCount=LevelStatic.attackMovePoints;
					movementtype=MOVETYPE_TOSHIP_TOSHIP;
				}
			}
			break;
		case MOVETYPE_LEFTRIGHT:
		case MOVETYPE_LEFTRIGHT_THEN_ATTACK:
			posXshifted+=leftrightmoveValue << Bob.SHIFT;
			leftrightmovePos+=leftrightmoveValue;
			if(leftrightmovePos==0 || leftrightmovePos==leftrightmoveRange){
				leftrightmoveValue=0-leftrightmoveValue;
			}
			break;
		}
	}

	public void attack() {
		movementtype=EnemyBob.MOVETYPE_TOSHIP_TOSHIP;
	}
	
}
