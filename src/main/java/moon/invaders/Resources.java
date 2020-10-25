package moon.invaders;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import abyss.lunarengine.APart;
import abyss.lunarengine.LunarEngineTools;
import abyss.lunarengine.gfx.Bob;
import abyss.lunarengine.gfx.BobFactory;

public class Resources {
	public static final int BACKGROUND_WIDTH = 1024;
	public static final int BACKGROUND_HEIGHT = 5760;
	
	public static int[] getBackground(int backgroundNumber) {
		Image imageBackground=getBackgroundImage(backgroundNumber);
		BufferedImage bufferedImageBackground=new BufferedImage(BACKGROUND_WIDTH,BACKGROUND_HEIGHT,BufferedImage.TYPE_INT_RGB);
		bufferedImageBackground.getGraphics().drawImage(imageBackground,0,0,BACKGROUND_WIDTH,BACKGROUND_HEIGHT,APart.jFrame);
		return ((DataBufferInt)bufferedImageBackground.getRaster().getDataBuffer()).getData();
	}

	private static Image getBackgroundImage(int i){
		return LunarEngineTools.createImage(APart.jFrame,"data/background/Background_1024_"+(i>9?i:"0"+i)+".jpg");
	}

	public static Bob getEnemyImageData(int i){
		return BobFactory.createBob(Resources.class,"/data/enemies/enemy"+(i>9?i:"0"+i)+"/bob.properties");
	}
	
	public static Bob getCometImageData(int i){
		return BobFactory.createBob(Resources.class,"/data/comet"+(i>9?i:"0"+i)+"/bob.properties");
	}
	
	public static Bob getExplosionImageData(int i) {
		return BobFactory.createBob(Resources.class,"/data/explosion"+(i>9?i:"0"+i)+"/bob.properties");
	}

	public static Bob getEnemyShotImageData(int i) {
		return BobFactory.createBob(Resources.class,"/data/enemyShot"+(i>9?i:"0"+i)+"/bob.properties");
	}

	public static Bob getPlayerShotImageData(int i) {
		return BobFactory.createBob(Resources.class,"/data/playerShot"+(i>9?i:"0"+i)+"/bob.properties");
	}

	public static Bob getPlayerShipImageData() {
		return BobFactory.createBob(Resources.class,"/data/playerShip"+"/bob.properties");
	}
	
	public static int[] getFontdata(){
		Image imageFont=LunarEngineTools.createImage(APart.jFrame,"data/Topaz16_pixelfade.png");
		BufferedImage bufferedImageFont=new BufferedImage(imageFont.getWidth(null),imageFont.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		int[] screendataFont=((DataBufferInt)bufferedImageFont.getRaster().getDataBuffer()).getData();
		bufferedImageFont.getGraphics().drawImage(imageFont,0,0,APart.jFrame);
		return screendataFont;
	}

}
