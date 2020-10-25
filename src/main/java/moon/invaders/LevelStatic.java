package moon.invaders;

import abyss.lunarengine.gfx.Bob;

public class LevelStatic {
	
	public static Bob bobComets;
	
	public static Bob bobExplosion1;
	
	public static Bob bobExplosion2;
	
	public static Bob bobEnemyshot;
	
	public static Bob bobPlayershot1;

	public static Bob bobPlayershot2;

	public static int[] screendataBackground;
	
	public static int lanesCount;

	/** Delay [frames] for comets to start falling */
	public static int cometStartDelay;

	/** Delay [frames] for next comet to fall */
	public static int cometNextDelay;

	/** Comet speed in pixel/frame */
	public static int cometSpeed;
	
	public static int attackStartDelay;
	public static int attackNextDelay;
	public static int attackShootDelay;
	public static int attackShootSpeed;
	public static int attackMovePoints;

}
