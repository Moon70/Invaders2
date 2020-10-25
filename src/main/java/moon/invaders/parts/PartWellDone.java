package moon.invaders.parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import abyss.lunarengine.APart;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Invaders2Main;

public class PartWellDone extends PartGame{
	private BufferedImage bufferedImageGameOver;
	protected static int[] screendataJetMasc;
	private int textblockWidth=190;
	private int textblockHeight=44;
	private int[] screendataWork;
	private int textOffsetX=(Screen.screenSizeX-textblockWidth)/2;
	private int textOffsetY=(Screen.screenSizeY-textblockHeight)/2;
	private static int fireworkDelay;
	
 	public static void main(String[] args){
		try{
			Screen.setScreensize(Invaders2Main.screenWidth,Invaders2Main.screenHeight);
			LunarEngine.initializeEngine();
			GameController.initialize();
			APart wellDonePart=new PartWellDone();
			new PartGame(true).precalc();
			wellDonePart.precalc();
			LunarEngine.setActivePart(wellDonePart);
			LunarEngine.startEngine();
		}catch(Throwable throwable){
			LunarEngine.throwableHandler(throwable);
		}
	}

	@Override
	public void precalc(){
		bufferedImageGameOver=new BufferedImage(textblockWidth,textblockHeight,BufferedImage.TYPE_INT_RGB);
		screendataJetMasc=((DataBufferInt)bufferedImageGameOver.getRaster().getDataBuffer()).getData();
		Font font=new Font("SansSerif",Font.BOLD,30);
		Graphics graphics=bufferedImageGameOver.getGraphics();
		int textposX=8;
		int textposY=30;
		graphics.setColor(new Color(0xff9900));
		graphics.setFont(font);
		graphics.drawString("WELL DONE",textposX,textposY);
		fireworkDelay=1;
	}

	@Override
	public void vbi(){
		if(--fireworkDelay==0) {
			fireworkDelay=3;
			int x=(int)(Math.random()*(Invaders2Main.screenWidth-32));
			int y=(int)(Math.random()*(Invaders2Main.screenHeight-32));
			enemyExplosionController.explode(x<<Bob.SHIFT,y<<Bob.SHIFT);
		}
		enemyExplosionController.vbi();
	}

	@Override
	public void worker1(){
		screendataWork=LunarEngine.screendataToWork;
		Bob.screendataToWork=LunarEngine.screendataToWork;
		player1Controller.render();
		enemyExplosionController.render();
		int pixel;
		for(int y=0;y<textblockHeight;y++){
			for(int x=0;x<textblockWidth;x++){
				pixel=screendataJetMasc[y*textblockWidth+x];
				if(pixel!=0){
					screendataWork[textOffsetX+x+Screen.screenSizeX*(textOffsetY+y)]=pixel;
				}
			}
		}
		refreshBottomTextline();
		writeBottomTextline();
	}
	
}
