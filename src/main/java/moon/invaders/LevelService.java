package moon.invaders;

import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

public class LevelService {
	public static Vector<Level> vecLeveldata;

	public static void initialize(){
		LevelStatic.bobComets=Resources.getCometImageData(1);
		LevelStatic.bobExplosion1=Resources.getExplosionImageData(1);
		LevelStatic.bobExplosion2=Resources.getExplosionImageData(2);
		LevelStatic.bobEnemyshot=Resources.getEnemyShotImageData(1);
		LevelStatic.bobPlayershot1=Resources.getPlayerShotImageData(1);
		LevelStatic.bobPlayershot2=Resources.getPlayerShotImageData(2);
		Level.loadLeveldataFromPropertyFiles();
	}

	public static void loadLeveldataFromPropertyFiles(){
		try {
			vecLeveldata=new Vector<Level>();
			int index=1;
			InputStream inputStream=null;
			while((inputStream=Level.class.getClassLoader().getResourceAsStream("data/level/Level"+(index<10?"0"+index:index)+".properties"))!=null){
				Properties properties=new Properties();
				properties.load(inputStream);
				inputStream.close();
				vecLeveldata.addElement(new Level(properties));
				index++;
			}
		} catch (Exception e) {
			throw new RuntimeException("Exception while reading leveldata properties",e);
		}
	}

}
