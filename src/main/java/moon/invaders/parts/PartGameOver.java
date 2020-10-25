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

public class PartGameOver extends PartGame{
	private BufferedImage bufferedImageGameOver;
	protected static int[] screendataJetMasc;
	private int textblockWidth=170;
	private int textblockHeight=44;
	private int[] screendataWork;
	private int textOffsetX=(Screen.screenSizeX-textblockWidth)/2;
	private int textOffsetY=(Screen.screenSizeY-textblockHeight)/2;
	
 	public static void main(String[] args){
		try{
			Screen.setScreensize(Invaders2Main.screenWidth,Invaders2Main.screenHeight);
			LunarEngine.initializeEngine();
			GameController.initialize();
			APart gameOverPart=new PartGameOver();
			new PartGame(true).precalc();
			gameOverPart.precalc();
			LunarEngine.setActivePart(gameOverPart);
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
		graphics.setColor(new Color(0xff0000));
		graphics.setFont(font);
		graphics.drawString("Game over",textposX,textposY);
	}

	public void vbi(){}

	@Override
	public void worker1(){
		screendataWork=LunarEngine.screendataToWork;
		Bob.screendataToWork=LunarEngine.screendataToWork;
		player1Controller.render();
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
