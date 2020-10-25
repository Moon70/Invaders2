package moon.invaders.comet;

import abyss.lunarengine.gfx.Bob;
import moon.invaders.LevelStatic;

public class CometController{
	private static final int MAX_COMETS=10;
	public static CometBob[] bobs;
	
	private int cometBeginDelay;
	private int cometFallDelay;
	private int cometFallIndex;
	private int[] randomCometStartPositionX = new int[] { 111, 897, 787, 899, 446, 762, 224, 245, 359, 871, 800, 773,
			600, 872, 204, 693, 57, 823, 721, 478, 277, 819, 817, 712, 664, 511, 411, 701, 740, 524, 766, 521, 148, 498,
			48, 621, 597, 733, 714, 714, 185, 753, 527, 633, 991, 963, 852, 117, 447, 424, 727, 949, 212, 173, 184, 783,
			106, 4, 943, 814, 5, 531, 46, 199, 188, 395, 210, 366, 452, 969, 668, 634, 637, 586, 302, 455, 231, 215,
			729, 309, 94, 858, 654, 953, 451, 476, 143, 781, 684, 830, 459, 325, 614, 207, 633, 686, 522, 740, 628, 455,
			270, 372, 859, 809, 535, 744, 527, 148, 358, 237, 12, 43, 874, 172, 545, 657, 464, 643, 184, 757, 115, 934,
			931, 208, 361, 233, 97, 667, 792, 682, 512, 369, 563, 90, 767, 135, 253, 220, 131, 737, 853, 790, 290, 502,
			625, 369, 11, 336, 936, 859, 834, 60, 509, 390, 364, 551, 675, 983, 534, 486, 710, 421, 896, 699, 605, 645,
			826, 54, 17, 725, 88, 416, 681, 950, 433, 862, 925, 668, 325, 455, 416, 826, 83, 925, 837, 656, 415, 700,
			861, 831, 581, 835, 29, 532, 900, 381, 175, 292, 954, 335, 434, 124, 235, 775, 588, 508, 632, 92, 248, 913,
			808, 21, 157, 234, 960, 402, 558, 130, 359, 649, 518, 654, 578, 704, 737, 551, 196, 125, 544, 584, 320, 55,
			289, 10, 320, 374, 682, 627, 746, 968, 251, 284, 429, 139, 273, 476, 513, 402, 520, 846, 238, 694, 219, 643,
			45, 536, 590, 779, 735, 153, 760, 269, 337, 820, 547, 631, 290, 473, 251, 788, 873, 464, 417, 221, 640, 879,
			108, 656, 866, 117, 141, 550, 289, 335, 462, 349, 362, 666, 760, 369, 772, 155, 126, 353, 959, 309, 667, 26,
			839, 95, 802, 931, 193, 218, 877, 650, 529, 365, 849, 794, 676, 607, 785, 895, 621, 417, 863, 954, 759, 20,
			852, 625, 67, 492, 932, 482, 201, 948, 116, 360, 228, 264, 415, 883, 18, 388, 514, 422, 151, 71, 433, 313,
			729, 700, 467, 5, 141, 543, 589, 840, 497, 5, 111, 602, 363, 674, 519, 533, 851, 970, 143, 617, 289, 565,
			261, 15, 153, 703, 290, 985, 141, 446, 624, 745, 858, 85, 700, 175, 701, 714, 37, 351, 315, 727, 863, 901,
			161, 62, 454, 465, 566, 617, 832, 455, 765, 895, 443, 143, 461, 762, 852, 376, 587, 746, 438, 749, 85, 666,
			705, 936, 568, 235, 770, 831, 152, 425, 304, 763, 664, 808, 86, 421, 75, 639, 389, 332, 845, 500, 63, 727,
			696, 586, 400, 359, 142, 120, 734, 306, 974, 239, 784, 299, 52, 841, 746, 951, 89, 111, 386, 372, 52, 131,
			262, 89, 714, 55, 633, 901, 951, 230, 506, 278, 577, 426, 727, 919, 354, 914, 120, 199, 348, 290, 2, 228,
			440, 149, 702, 217, 560, 530, 103, 429, 911, 691, 361, 288, 224, 120, 828, 662, 798, 87, 50, 980, 120, 821,
			623, 948, 319, 327 };
	private int cometStartPositionIndex;
	
	public CometController(Bob bob){
		bobs=new CometBob[MAX_COMETS];
		for(int i=0;i<MAX_COMETS;i++){
			bobs[i]=new CometBob(bob);
		}
		for(int i=0;i<randomCometStartPositionX.length;i++) {
			randomCometStartPositionX[i]=randomCometStartPositionX[i]<<Bob.SHIFT;
		}
	}
	
	public void reset(){
		for(int i=0;i<MAX_COMETS;i++){
			bobs[i].enabled=false;
		}
		cometBeginDelay=LevelStatic.cometStartDelay;
		cometFallDelay=LevelStatic.cometNextDelay;
		cometFallIndex=0;
		cometStartPositionIndex=0;
	}

	public void vbi(){
		for(int i=0;i<MAX_COMETS;i++){
			if(bobs[i].enabled){
				bobs[i].vbi();
			}
		}

		if(cometBeginDelay!=0){
			cometBeginDelay--;
		}else if(--cometFallDelay==0){
			cometFallDelay=LevelStatic.cometNextDelay;
			if(++cometFallIndex==MAX_COMETS){
				cometFallIndex=0;
			}

			if(++cometStartPositionIndex==randomCometStartPositionX.length) {
				cometStartPositionIndex=0;
			}
			dropNewComet();
		}
	}

	public void render(){
		for(int i=0;i<MAX_COMETS;i++){
			bobs[i].render();
		}
	}
	
	private boolean dropNewComet(){
		if(bobs[cometFallIndex].enabled){
			return false;
		}
		bobs[cometFallIndex].posXshifted=randomCometStartPositionX[cometStartPositionIndex];
		bobs[cometFallIndex].posYshifted=-32<<Bob.SHIFT;
		bobs[cometFallIndex].setSpeed(LevelStatic.cometSpeed);
		bobs[cometFallIndex].enabled=true;
		return true;	
	}
}
