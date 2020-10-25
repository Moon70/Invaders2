package moon.invaders.parts;

import abyss.lunarengine.APart;
import abyss.lunarengine.LunarEngine;
import abyss.lunarengine.Screen;
import abyss.lunarengine.gfx.Bob;
import moon.invaders.GameController;
import moon.invaders.Invaders2Main;
import moon.invaders.LevelStatic;
import moon.invaders.enemyshot.EnemyShotBob;
import moon.invaders.player.Player1Controller;

public class PartBoom extends PartGame{
	private static EnemyShotBob[] bobsMex;
	private static int[][] mex={{52,-595},{-260,-150},{-185,-334},{-590,-368},{449,-377},{-174,-358},{-145,-507},{444,-288},{274,-246},{114,-648},{75,-387},{246,-213},{116,-78},{-321,-495},{58,-330},{218,-468},{95,-86},{-221,-399},{42,-104},{-77,-83},{-180,-426},{242,-455},{154,-317},{-32,-625},{189,-425},{-141,-462},{199,-229},{153,-133},{-300,-371},{-222,-523},{193,-454},{-67,-427},{-342,-330},{143,-104},{-226,-348},{-205,-317},{-15,-108},{79,-132},{-102,-109},{459,-321},{-49,-98},{83,-123},{140,-524},{-79,-179},{-195,-338},{-99,-234},{250,-241},{211,-433},{53,-509},{201,-619},{126,-228},{-147,-513},{-35,-507},{-123,-231},{-399,-359},{-19,-282},{-71,-250},{24,-470},{-219,-603},{64,-224},{408,-308},{146,-151},{138,-483},{-526,-426},{-157,-371},{-95,-131},{101,-84},{20,-169},{-18,-347},{134,-252},{-390,-283},{-66,-379},{496,-310},{115,-286},{-102,-170},{-230,-570},{255,-255},{-48,-308},{308,-298},{-51,-159},{-141,-349},{-396,-332},{-171,-640},{169,-590},{-18,-525},{52,-123},{32,-468},{150,-605},{60,-693},{-256,-603},{170,-556},{111,-251},{347,-399},{338,-465},{-122,-490},{453,-453},{51,-224},{-279,-299},{-191,-110},{18,-263},{286,-286},{-16,-484},{87,-412},{42,-406},{-128,-318},{-244,-423},{-48,-179},{-125,-591},{-114,-163},{-272,-228},{-429,-444},{206,-193},{127,-147},{-6,-349},{-30,-431},{118,-265},{171,-115},{-122,-213},{-31,-163},{423,-454},{-276,-208},{-349,-447},{-78,-638},{295,-285},{86,-204},{373,-553},{-426,-546},{-294,-363},{-110,-110},{207,-489},{-345,-322},{178,-205},{378,-420},{-109,-126},{-451,-484},{-123,-157},{563,-394},{139,-112},{-493,-296},{-218,-601},{-127,-315},{-491,-474},{53,-198},{508,-369},{-174,-104},{-387,-533},{407,-274},{412,-412},{30,-430},{463,-375},{-399,-386},{-479,-462},{308,-456},{537,-335},{42,-304},{92,-217},{51,-159},{155,-319},{145,-155},{-374,-243},{95,-122},{-426,-287},{-293,-264},{-105,-101},{-241,-453},{41,-589},{-72,-92},{63,-601},{-118,-277},{-134,-503},{-21,-111},{-466,-314},{-153,-276},{-29,-208},{-122,-98},{-226,-348},{-256,-327},{301,-618},{-343,-472},{177,-253},{-202,-209},{-92,-154},{-160,-466},{-341,-353},{508,-383},{249,-650},{-177,-398},{-248,-275},{-34,-663},{-35,-682},{-160,-120},{118,-71},{355,-471},{-46,-439},{-125,-409},{202,-455},{434,-339},{229,-293},{42,-131},{-366,-296}};
	private static int mexCount=mex.length;

	public static void main(String[] args){
		try{
			Screen.setScreensize(Invaders2Main.screenWidth,Invaders2Main.screenHeight);
			LunarEngine.initializeEngine();
			GameController.initialize();
			APart boomPart=new PartBoom();
			new PartGame(true).precalc();
			boomPart.precalc();
			LunarEngine.setActivePart(boomPart);
			LunarEngine.startEngine();
		}catch(Throwable throwable){
			LunarEngine.throwableHandler(throwable);
		}
	}

	@Override
	public void precalc(){
		bobsMex=new EnemyShotBob[mexCount];
		for(int i=0;i<mexCount;i++){
			bobsMex[i]=new EnemyShotBob(LevelStatic.bobExplosion2);
		}
		int movecount=80;
		for(int i=0;i<mexCount;i++){
			bobsMex[i].posXshifted=Player1Controller.bobPlayerShip.posXshifted;
			bobsMex[i].posYshifted=Player1Controller.bobPlayerShip.posYshifted;
			bobsMex[i].movecount=movecount;
			bobsMex[i].posXdelta=(mex[i][0] << Bob.SHIFT)/movecount;
			bobsMex[i].posYdelta=(mex[i][1] << Bob.SHIFT)/movecount;
			bobsMex[i].setAnimType(Bob.ANIMTYPE_FORWARD);
			bobsMex[i].enabled=true;
		}
		Player1Controller.bobPlayerShip.enabled=false;
	}

	public void vbi(){
		for(int i=0;i<mexCount;i++){
			bobsMex[i].vbi();
		}
	}

	@Override
	public void worker1(){
		Bob.screendataToWork=LunarEngine.screendataToWork;
		for(int i=0;i<mexCount;i++){
			bobsMex[i].render();
		}
		refreshBottomTextline();
		writeBottomTextline();
	}
	
}
