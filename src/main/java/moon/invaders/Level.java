package moon.invaders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import abyss.lunarengine.gfx.Bob;
import abyss.lunarengine.tools.SSKPFilterInputStream;
import moon.invaders.parts.PartTitle;

public class Level {
	public static final String PROPERTYNAME_BACKGROUND = "Background";
	public static final String PROPERTYNAME_COMET_FALL_START_DELAY = "CometFallStartDelay";
	public static final String PROPERTYNAME_COMET_FALL_NEXT_DELAY = "CometFallNextDelay";
	public static final String PROPERTYNAME_COMET_SPEED = "CometSpeed";
	public static final String PROPERTYNAME_ATTACK_START_DELAY = "AttackStartDelay";
	public static final String PROPERTYNAME_ATTACK_NEXT_DELAY = "AttackNextDelay";
	public static final String PROPERTYNAME_ATTACK_SHOOT_DELAY = "AttackShootDelay";
	public static final String PROPERTYNAME_ATTACK_SHOOT_SPEED = "AttackShootSpeed";
	public static final String PROPERTYNAME_ATTACK_MOVE_POINTS = "AttackMovePoints";
	
	public static final String PROPERTYNAMEPREFIX_LANE = "Lane";
	public static final String PROPERTYNAMEPREFIX_MOVESTARTDELAY = "MovestartDelay";
	public static final String PROPERTYNAMEPREFIX_MOVENEXTDELAY = "MovenextDelay";
	public static final String PROPERTYNAMEPREFIX_BOBMOVETYPE = "MoveType";
	public static final String PROPERTYNAMEPREFIX_BOBNUMBER = "BobNumber";
	public static final String PROPERTYNAMEPREFIX_BOBCOUNT = "BobCount";
	public static final String PROPERTYNAMEPREFIX_ENEMY_SHOOT_NEXT_DELAY = "EnemyShootNextDelay";
	public static final String PROPERTYNAMEPREFIX_ENEMY_SHOOT_START_DELAY = "EnemyShootStartDelay";
	public static final String PROPERTYNAMEPREFIX_ENEMY_SHOOT_SPEED = "EnemyShootSpeed";
	public static final String PROPERTYNAMEPREFIX_ENEMY_SHOOT_TYPE = "EnemyShootType";

	public static final int ENEMYSHOOT_DOWN=1;
	public static final int ENEMYSHOOT_TOPLAYERSHIP=2;
	
	private static Level currentLevel;
	
	/** Delay [frames] for enemies to start shooting */
	public static int[] enemyShootStartDelay;

	/** Delay [frames] for next enemies to shoot */
	public static int[] enemyShootNextDelay;

	/** Speed [pixel shifted] for bullet */
	public static int[] enemyShootSpeed;

	/** Type of shoot */
	public static int[] enemyShootType;


	public int leveldata_Background;
	public int[] leveldata_BackgroundImagedata;
	private int leveldata_lanes=-88888;
	private int leveldata_CometFallStartDelay;
	private int leveldata_CometFallNextDelay;
	private int leveldata_CometFallSpeed;
	private int leveldata_AttackStartDelay;
	private int leveldata_AttackNextDelay;
	private int leveldata_AttackShootDelay;
	private int leveldata_AttackShootSpeed;
	private int leveldata_AttackMovePoints;
	private String[] leveldata_EnemyMoveCodes;
	private int[][] leveldata_movementXLane;//[Lane][pos]
	private int[][] leveldata_movementYLane;
	private MovementCreator[] leveldata_movement;
	private int[] leveldata_movementStartDelay;
	private int[] leveldata_movementNextDelay;
	private int[] leveldata_bobCount;
	private int[] leveldata_bobNumber;
	private int[] leveldata_bobMoveType;
	private Bob[] leveldata_bobEnemy;
	private int[] leveldata_EnemyShootStartDelay;
	private int[] leveldata_EnemyShootNextDelay;
	private int[] leveldata_EnemyShootSpeed;
	private int[] leveldata_EnemyShootType;
	

	
	public static Bob[] bobEnemy;
	private static MovementCreator enemyMovementCreator=new MovementCreator(0);
	
	public static int[][] movementXLane;//[Lane][pos]
	public static int[][] movementYLane;
	public static MovementCreator[] movement;
	public static int[] movementStartDelay;
	public static int[] movementNextDelay;
	public static int[] bobCount;
	public static int[] bobNumber;
	public static int[] bobMoveType;

	
	public Level(Properties properties) {
		leveldata_Background=Integer.parseInt(properties.getProperty(PROPERTYNAME_BACKGROUND));
		leveldata_CometFallStartDelay=	Integer.parseInt(properties.getProperty(PROPERTYNAME_COMET_FALL_START_DELAY));
		leveldata_CometFallNextDelay=	Integer.parseInt(properties.getProperty(PROPERTYNAME_COMET_FALL_NEXT_DELAY));
		leveldata_CometFallSpeed=		Integer.parseInt(properties.getProperty(PROPERTYNAME_COMET_SPEED));
		if(properties.getProperty(PROPERTYNAME_ATTACK_START_DELAY)==null || properties.getProperty(PROPERTYNAME_ATTACK_START_DELAY).length()==0) {
			properties.setProperty(PROPERTYNAME_ATTACK_START_DELAY, "72000");
		};
		if(properties.getProperty(PROPERTYNAME_ATTACK_NEXT_DELAY)==null || properties.getProperty(PROPERTYNAME_ATTACK_NEXT_DELAY).length()==0) {
			properties.setProperty(PROPERTYNAME_ATTACK_NEXT_DELAY, "480");
		};
		if(properties.getProperty(PROPERTYNAME_ATTACK_SHOOT_DELAY)==null || properties.getProperty(PROPERTYNAME_ATTACK_SHOOT_DELAY).length()==0) {
			properties.setProperty(PROPERTYNAME_ATTACK_SHOOT_DELAY, "240");
		};
		if(properties.getProperty(PROPERTYNAME_ATTACK_SHOOT_SPEED)==null || properties.getProperty(PROPERTYNAME_ATTACK_SHOOT_SPEED).length()==0) {
			properties.setProperty(PROPERTYNAME_ATTACK_SHOOT_SPEED, "5");
		};
		if(properties.getProperty(PROPERTYNAME_ATTACK_MOVE_POINTS)==null || properties.getProperty(PROPERTYNAME_ATTACK_MOVE_POINTS).length()==0) {
			properties.setProperty(PROPERTYNAME_ATTACK_MOVE_POINTS, "2");
		};
		
		leveldata_AttackStartDelay=		Integer.parseInt(properties.getProperty(PROPERTYNAME_ATTACK_START_DELAY));
		leveldata_AttackNextDelay=		Integer.parseInt(properties.getProperty(PROPERTYNAME_ATTACK_NEXT_DELAY));
		leveldata_AttackShootDelay=		Integer.parseInt(properties.getProperty(PROPERTYNAME_ATTACK_SHOOT_DELAY));
		leveldata_AttackShootSpeed=		Integer.parseInt(properties.getProperty(PROPERTYNAME_ATTACK_SHOOT_SPEED));
		leveldata_AttackMovePoints=		Integer.parseInt(properties.getProperty(PROPERTYNAME_ATTACK_MOVE_POINTS));

		ArrayList<String> arrayList=new ArrayList<String>();
		int index=1;
		while(properties.containsKey(PROPERTYNAMEPREFIX_LANE+index)){
			arrayList.add(properties.getProperty(PROPERTYNAMEPREFIX_LANE+index++));
		}
		leveldata_EnemyMoveCodes=arrayList.toArray(new String[]{});
	
		leveldata_lanes=leveldata_EnemyMoveCodes.length;
		leveldata_movementStartDelay=new int[leveldata_lanes];
		leveldata_movementNextDelay=new int[leveldata_lanes];
		leveldata_bobMoveType=new int[leveldata_lanes];
		leveldata_bobNumber=new int[leveldata_lanes];
		leveldata_bobCount=new int[leveldata_lanes];
		leveldata_bobEnemy=new Bob[leveldata_lanes];
		leveldata_EnemyShootStartDelay=new int[leveldata_lanes];
		leveldata_EnemyShootNextDelay=new int[leveldata_lanes];
		leveldata_EnemyShootSpeed=new int[leveldata_lanes];
		leveldata_EnemyShootType=new int[leveldata_lanes];
		for(int i=0;i<leveldata_EnemyMoveCodes.length;i++){
			String sStartDelay=properties.getProperty(Level.PROPERTYNAMEPREFIX_MOVESTARTDELAY+(i+1));
			String sNextDelay=properties.getProperty(Level.PROPERTYNAMEPREFIX_MOVENEXTDELAY+(i+1));
			String sBobMoveType=properties.getProperty(Level.PROPERTYNAMEPREFIX_BOBMOVETYPE+(i+1));
			String sBobCount=properties.getProperty(Level.PROPERTYNAMEPREFIX_BOBCOUNT+(i+1));
			String sBobNumber=properties.getProperty(Level.PROPERTYNAMEPREFIX_BOBNUMBER+(i+1));
			String sEnemyShootStartDelay=properties.getProperty(Level.PROPERTYNAMEPREFIX_ENEMY_SHOOT_START_DELAY+(i+1));
			String sEnemyShootNextDelay=properties.getProperty(Level.PROPERTYNAMEPREFIX_ENEMY_SHOOT_NEXT_DELAY+(i+1));
			String sEnemyShootSpeed=properties.getProperty(Level.PROPERTYNAMEPREFIX_ENEMY_SHOOT_SPEED+(i+1));
			String sEnemyShootType=properties.getProperty(Level.PROPERTYNAMEPREFIX_ENEMY_SHOOT_TYPE+(i+1));
			leveldata_movementStartDelay[i]=sStartDelay.length()==0?0:Integer.parseInt(sStartDelay);
			leveldata_movementNextDelay[i]=sNextDelay.length()==0?15:Integer.parseInt(sNextDelay);
			leveldata_bobMoveType[i]=sBobMoveType==null || sBobMoveType.length()==0?1:Integer.parseInt(sBobMoveType);
			leveldata_bobNumber[i]=sBobNumber.length()==0?0:Integer.parseInt(sBobNumber);
			leveldata_bobCount[i]=sBobCount.length()==0?0:Integer.parseInt(sBobCount);
			leveldata_bobEnemy[i]=Resources.getEnemyImageData(leveldata_bobNumber[i]);
			leveldata_EnemyShootStartDelay[i]=sEnemyShootStartDelay==null?15:Integer.parseInt(sEnemyShootStartDelay);
			leveldata_EnemyShootNextDelay[i]=sEnemyShootNextDelay==null?15:Integer.parseInt(sEnemyShootNextDelay);
			leveldata_EnemyShootSpeed[i]=sEnemyShootSpeed==null?1:Integer.parseInt(sEnemyShootSpeed)<<Bob.SHIFT;
			leveldata_EnemyShootType[i]=sEnemyShootType==null || sEnemyShootType.length()==0?2:Integer.parseInt(sEnemyShootType);
		}

	}
	
	public static Level initializeLeveleditor(Properties properties){
		try {
			LevelService.vecLeveldata=new Vector<Level>();
			Level level=new Level(properties);
			LevelService.vecLeveldata.addElement(level);
			level.prepareLevel();
			activateLevel(level);
			return level;
		} catch (Exception e) {
			throw new RuntimeException("Exception while reading leveleditor leveldata properties",e);
		}
	}
	
	public static void loadLeveldataFromPropertyFiles(){
		try {
			System.out.println("loading leveldata...");
			LevelService.vecLeveldata=new Vector<Level>();
			int index=1;
			InputStream inputStream=null;
			while(true) {
//			while((inputStream=Level.class.getClassLoader().getResourceAsStream("data/level/Level"+(index<10?"0"+index:index)+".properties"))!=null){
				inputStream=Level.class.getClassLoader().getResourceAsStream("data/level/Level"+(index<10?"0"+index:index)+".sskp");
				if(inputStream!=null) {
					inputStream=new SSKPFilterInputStream(inputStream);
				}else {
					inputStream=Level.class.getClassLoader().getResourceAsStream("data/level/Level"+(index<10?"0"+index:index)+".properties");
				}
				if(inputStream==null) {
					break;
				}
				//System.out.println("Loading Leveldata "+index);
				Properties properties=new Properties();
				properties.load(inputStream);
				inputStream.close();
				LevelService.vecLeveldata.addElement(new Level(properties));
				index++;
			}
		} catch (Exception e) {
			throw new RuntimeException("Exception while reading leveldata properties",e);
		}
	}
	
	public void prepareLevel(){
		setMovement(leveldata_EnemyMoveCodes);
		leveldata_BackgroundImagedata=Resources.getBackground(leveldata_Background);
		int rgb;
		int imagedatasize=leveldata_BackgroundImagedata.length;
		PartTitle.screendataTitleBackground=new int[imagedatasize*2];
		for(int i=0;i<imagedatasize;i++){
			rgb=leveldata_BackgroundImagedata[i];
			leveldata_BackgroundImagedata[i]=(rgb&0xfefefe)>>1;
			PartTitle.screendataTitleBackground[i]=rgb;
			PartTitle.screendataTitleBackground[i+imagedatasize]=rgb;
		}
		Sound.prepareBackLoop(leveldata_Background);
	}
	
	public static void activateLevel(Level levelData){
		if(levelData.leveldata_lanes<0){
			System.err.println("Warnung: preparelevel fehlt!");
			Thread.dumpStack();
			levelData.prepareLevel();
		}
		currentLevel=levelData;
		LevelStatic.lanesCount=levelData.leveldata_lanes;
		movementXLane=levelData.leveldata_movementXLane;
		movementYLane=levelData.leveldata_movementYLane;
		movement=levelData.leveldata_movement;
		movementStartDelay=levelData.leveldata_movementStartDelay;
		movementNextDelay=levelData.leveldata_movementNextDelay;
		bobNumber=levelData.leveldata_bobNumber;
		bobCount=levelData.leveldata_bobCount;
		bobEnemy=levelData.leveldata_bobEnemy;
		bobMoveType=levelData.leveldata_bobMoveType;
		
		enemyShootStartDelay=levelData.leveldata_EnemyShootStartDelay;
		enemyShootNextDelay=levelData.leveldata_EnemyShootNextDelay;
		enemyShootSpeed=levelData.leveldata_EnemyShootSpeed;
		enemyShootType=levelData.leveldata_EnemyShootType;
		LevelStatic.cometStartDelay=levelData.leveldata_CometFallStartDelay;
		LevelStatic.cometNextDelay=levelData.leveldata_CometFallNextDelay;
		LevelStatic.cometSpeed=levelData.leveldata_CometFallSpeed;
		LevelStatic.attackStartDelay=levelData.leveldata_AttackStartDelay;
		LevelStatic.attackNextDelay=levelData.leveldata_AttackNextDelay;
		LevelStatic.attackShootDelay=levelData.leveldata_AttackShootDelay;
		LevelStatic.attackShootSpeed=levelData.leveldata_AttackShootSpeed;
		LevelStatic.attackMovePoints=levelData.leveldata_AttackMovePoints;

		if(levelData.leveldata_BackgroundImagedata!=null){
			LevelStatic.screendataBackground=levelData.leveldata_BackgroundImagedata;
		}
		levelData.leveldata_BackgroundImagedata=null;
	}
	
	public static void setMovementFromMovementEditor(String[] movementCodes){
		currentLevel.setMovement(movementCodes);
		LevelStatic.lanesCount=currentLevel.leveldata_lanes;
		movementXLane=currentLevel.leveldata_movementXLane;
		movementYLane=currentLevel.leveldata_movementYLane;
	}
	
	public static Level getLevel(int level){
		return LevelService.vecLeveldata.get(level-1);
	}
	
	public static boolean isLastLevel(){
		return LevelService.vecLeveldata.get(LevelService.vecLeveldata.size()-1)==currentLevel;
	}
	
	private void setMovement(String[] movementCodes){
		leveldata_movementXLane=new int[leveldata_lanes][];
		leveldata_movementYLane=new int[leveldata_lanes][];
		leveldata_movement=new MovementCreator[leveldata_lanes];
		for(int i=0;i<leveldata_lanes;i++){
			enemyMovementCreator.parse(movementCodes[i]);
			leveldata_movementXLane[i]=enemyMovementCreator.createXArray();
			leveldata_movementYLane[i]=enemyMovementCreator.createYArray();
			leveldata_movement[i]=MovementCreator.getInstance(movementCodes[i], 0);
		}
	}

	public static boolean isCurrentBackground(Level levelData) {
		return levelData.leveldata_Background==currentLevel.leveldata_Background;
	}

}
