package moon.invaders;

import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;

public class Textwriter {
	private static int[] fontData;
	private static int colorRotateIndex;
	private static int colorRotateDelay;
	
	public static void initialize(){
		fontData=Resources.getFontdata();
	}
	
	public static void writeText(String text,int offsetX,int offsetY){
		if(--colorRotateDelay<0){
			colorRotateDelay=4;
			
			colorRotateIndex--;
			if(colorRotateIndex<0){
				colorRotateIndex+=24;
			}
		}
		int cursor=offsetX+offsetY*Screen.screenSizeX;
		String fontCharacters=" ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,./<>;:[]*()!#";
		int character;
		for(int i=0;i<text.length();i++){
			character=fontCharacters.indexOf(text.charAt(i));
			writeChar(LunarEngine.screendataToWork,cursor,character<0?0:character);
			cursor+=16;
		}

	}
	
	public static void writeTextDoubleY(String text,int offsetX,int offsetY){
		if(--colorRotateDelay<0){
			colorRotateDelay=4;
			
			colorRotateIndex--;
			if(colorRotateIndex<0){
				colorRotateIndex+=24;
			}
		}
		int cursor=offsetX+offsetY*Screen.screenSizeX;
		String fontCharacters=" ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,./<>;:[]*()!#";
		int character;
		for(int i=0;i<text.length();i++){
			character=fontCharacters.indexOf(text.charAt(i));
			writeChar2(LunarEngine.screendataToWork,cursor,character<0?0:character,0);
			cursor+=16;
		}

	}
	
	public static void writeTextDoubleY(char[] chars,int offsetX,int offsetY,int fadeOffset){
		if(--colorRotateDelay<0){
			colorRotateDelay=4;
			
			colorRotateIndex--;
			if(colorRotateIndex<0){
				colorRotateIndex+=24;
			}
		}
		int cursor=offsetX+offsetY*Screen.screenSizeX;
		String fontCharacters=" ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,./<>;:[]*()!#";
		int character;
		for(int i=0;i<chars.length;i++){
			character=fontCharacters.indexOf(chars[i]);
			writeChar2(LunarEngine.screendataToWork,cursor,character<0?0:character,fadeOffset);
			cursor+=16;
		}

	}
	
	private static void writeChar(int[] screendata,int cursor,int character){
		int[] colors={
				0xadadad,
				0xadadad,
				0xd4d4d4,
				0xd4d4d4,
				0xffffff,
				0xffffff,
				0xd4d4d4,
				0xd4d4d4,
				0xadadad,
				0xadadad,
				0x828282,
				0x828282,
				0x4c4c4c,
				0x4c4c4c,
				0x4c4c4c,
				0x4c4c4c};
		int fontSizeY=16;
		int fontPicSizeX=918;
		int pixel=0;
		for(int y=0;y<fontSizeY;y++){
			for(int x=0;x<16;x++){
				pixel=fontData[y*fontPicSizeX+x+(character*18)+2];
				if(character==50){
//					if(pixel!=0){
						screendata[cursor+x+Screen.screenSizeX*(y*1)]=pixel;
//					}
				}else if(pixel!=0){
					screendata[cursor+x+Screen.screenSizeX*(y*1)]=colors[y];
				}else{
					screendata[cursor+x+Screen.screenSizeX*y]=0;
				}
			}
		}
	}

	private static void writeChar2(int[] screendata,int cursor,int character,int fadeOffset){
		int[] colors={
				0x553333,
				0x775555,
				0x997777,
				0xbb9999,
				0xddbbbb,
				0xffdddd,
				
				0x335533,
				0x557755,
				0x779977,
				0x99bb99,
				0xbbddbb,
				0xddffdd,
				
				0x333355,
				0x555577,
				0x777799,
				0x9999bb,
				0xbbbbdd,
				0xddddff,
				
				0x555533,
				0x777755,
				0x999977,
				0xbbbb99,
				0xddddbb,
				0xffffdd,

				
				
				0x553333,
				0x775555,
				0x997777,
				0xbb9999,
				0xddbbbb,
				0xffdddd,
				
				0x335533,
				0x557755,
				0x779977,
				0x99bb99,
				0xbbddbb,
				0xddffdd,
				
				0x333355,
				0x555577,
				0x777799,
				0x9999bb,
				0xbbbbdd,
				0xddddff,
				
				0x555533,
				0x777755,
				0x999977,
				0xbbbb99,
				0xddddbb,
				0xffffdd,

				
				

				0x553333,
				0x775555,
				0x997777,
				0xbb9999,
				0xddbbbb,
				0xffdddd,
				
				0x335533,
				0x557755,
				0x779977,
				0x99bb99,
				0xbbddbb,
				0xddffdd,
				
				0x333355,
				0x555577,
				0x777799,
				0x9999bb,
				0xbbbbdd,
				0xddddff,
				
				0x555533,
				0x777755,
				0x999977,
				0xbbbb99,
				0xddddbb,
				0xffffdd,

				
				

				
				
				
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,
				0xff0000,

				
				
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,

				
				
				
				
				
				
				
				
				
				
				
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				
//				0xffffff,
//				0xdddddd,
//				0xbbbbbb,
//				0x999999,
//				0x777777,
//				0x555555,

				
				
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				
//				0xffffff,
//				0xdddddd,
//				0xbbbbbb,
//				0x999999,
//				0x777777,
//				0x555555,

				
				
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				
				0xffffff,
				0xdddddd,
				0xbbbbbb,
				0x999999,
				0x777777,
				0x555555,

				
				
				0x333333,
				0x555555,
				0x777777,
				0x999999,
				0xbbbbbb,
				0xdddddd,
				0xffffff,
				0xffffff,
				0xdddddd,
				0xbbbbbb,
				0x999999,
				0x777777,
				0x555555,
				0x333333};
		int fontSizeY=16;
		int fontPicSizeX=918;
		int pixel=0;
		
		int shadowDelta1=3;
		int shadowDelta2=2;
		int pixOffset=fontPicSizeX*fontSizeY*fadeOffset;
		for(int y=0;y<fontSizeY;y++){
			for(int x=0;x<16;x++){
				pixel=fontData[pixOffset+y*fontPicSizeX+x+(character*18)+2];
				if(pixel!=0){
					if(character==50){
						screendata[cursor+x+Screen.screenSizeX*(y*2)]=pixel;
						screendata[cursor+x+Screen.screenSizeX*(y*2+1)]=pixel;
					}else{
						screendata[cursor+x-shadowDelta1+Screen.screenSizeX*(y*2-shadowDelta1)]=0x0;
						screendata[cursor+x-shadowDelta1+Screen.screenSizeX*(y*2+1-shadowDelta1)]=0x0;
						
						screendata[cursor+x-shadowDelta2+Screen.screenSizeX*(y*2-shadowDelta1)]=0x0;
						screendata[cursor+x-shadowDelta2+Screen.screenSizeX*(y*2+1-shadowDelta1)]=0x0;
						
						screendata[cursor+x-shadowDelta1+Screen.screenSizeX*(y*2-shadowDelta2)]=0x0;
						screendata[cursor+x-shadowDelta1+Screen.screenSizeX*(y*2+1-shadowDelta2)]=0x0;
						
						screendata[cursor+x-shadowDelta2+Screen.screenSizeX*(y*2-shadowDelta2)]=0x0;
						screendata[cursor+x-shadowDelta2+Screen.screenSizeX*(y*2+1-shadowDelta2)]=0x0;
					}
				}
			}
		}
		for(int y=0;y<fontSizeY;y++){
			for(int x=0;x<16;x++){
				pixel=fontData[pixOffset+y*fontPicSizeX+x+(character*18)+2];
				if(pixel!=0){
					if(character==50){
						screendata[cursor+x+Screen.screenSizeX*(y*2)]=pixel;
						screendata[cursor+x+Screen.screenSizeX*(y*2+1)]=pixel;
					}else{
						screendata[cursor+x+Screen.screenSizeX*(y*2)]=colors[colorRotateIndex+(y*2)];
						screendata[cursor+x+Screen.screenSizeX*(y*2+1)]=colors[colorRotateIndex+(y*2+1)];
					}
				}
			}
		}
	}

}
