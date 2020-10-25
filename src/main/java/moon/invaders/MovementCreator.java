package moon.invaders;

import java.util.ArrayList;

import abyss.lunarengine.gfx.Bob;

/**
 * Precalculates a movement table.
 * <br>The movement (list of x,y coordinates) is defined by a set of commands:
 * <br><b>start:<i>x,y</i></b>: set start position
 * <br><b>step:<i>x,y</i></b>: sets a delta x and delta y value
 * <br><b>moveCount:<i>c</i></b>: creates c points while adding x,y values set by <code>step</code> command
 * <br><b>moveToX:<i>x</i></b>: creates points while adding x,y, until given x matches
 * <br><b>moveTo:<i>x,y,c</i></b>: creates a line to x,y, using c points
 * <br><b>sector:<i>centre x, centre y, angle, count, direction</i></b>: creates a sector of <code>angle</code> degree using <code>count</code> points. <code>direction>0/1 = clockwise/counterclockwise
 * <br><b>spline:<i>spline x, spline y, endpoint x, endpoint y, count</i></b>: creates a spline using <code>count</code> points
 * <br><b>splineCV:<i>spline x, spline y, endpoint x, endpoint y, step</i></b>: creates a spline with Constant Velocity using point delta <code>step</code>
 * <br><b>mirrorX:<i>switch</i></b>: all following x points are mirrored (middle of screen), until mirroring is switched off by mirrorX:0
 * <br><b>mirrorY:<i>switch</i></b>: all following y points are mirrored (middle of screen), until mirroring is switched off by mirrorY:0
 * <br><b>deltaX:<i>delta</i></b>: adds <code>delta</code> to x of all following points
 * <br><b>deltaY:<i>delta</i></b>: adds <code>delta</code> to y of all following points
 * 
 * @author Thomas Mattel
 */
public class MovementCreator {
	private int shift;
	private ArrayList<Integer> arrayListX;
	private ArrayList<Integer> arrayListY;
	private double posX;
	private double posY;
	private double stepX;
	private double stepY;
	
	private boolean mirrorX;
	private boolean mirrorY;
	private int deltaX;
	private int deltaY;
	
	private int surfPosition=-1;
	private int x;
	private int y;
	private int loopIndex=-1;
	
	private int[] movementXLane;
	private int[] movementYLane;
	
	private static final int MOVEMENTTYPE_RUNSTOP=0;
	private static final int MOVEMENTTYPE_LOOP=1;
	private static final int MOVEMENTTYPE_PINGPONG=2;
	private int movementtype;
	private int movementdirection=1;
	
	public static MovementCreator getInstance(String movementDefinition,int shift) {
		MovementCreator movementCreator=new MovementCreator(shift);
		movementCreator.parse(movementDefinition);
		movementCreator.movementXLane=movementCreator.createXArray();
		movementCreator.movementYLane=movementCreator.createYArray();
		return movementCreator;
	}
	
	public MovementCreator(int shift) {
		this.shift=shift;
	}
	
	public void parse(String movementDefinition){
		arrayListX=new ArrayList<Integer>();
		arrayListY=new ArrayList<Integer>();
		stepX=0;
		stepY=0;
		mirrorX=false;
		mirrorY=false;
		deltaX=0;
		deltaY=0;
		String[] commandlines=movementDefinition.split("#");
		for(int line=0;line<commandlines.length;line++){
			String[] command=commandlines[line].split(":");
			String[] values=command[1].split(",");
			double[] valuesDouble=new double[values.length];
			for(int i=0;i<values.length;i++){
				valuesDouble[i]=Double.parseDouble(values[i]);
			}
			
			if(command[0].equals("start")){
				if(values.length!=2){
					System.err.println("Usage: start:x,y");
					System.err.println("--> sets the start point");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				posX=valuesDouble[0];
				posY=valuesDouble[1];
				addValues();
			}else if(command[0].equals("step")){
				if(values.length!=2){
					System.err.println("Usage: step:step x,step y");
					System.err.println("--> sets the x and y steps in pixel, following commands can use them");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				stepX=valuesDouble[0];
				stepY=valuesDouble[1];
			}else if(command[0].equals("moveCount")){
				if(values.length!=1){
					System.err.println("Usage: moveCount:count");
					System.err.println("--> generates 'count' points while adding 'stepX' and 'stepY' to the current point");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				for(int i=0;i<valuesDouble[0];i++){
					posX+=stepX;
					posY+=stepY;
					addValues();
				}
			}else if(command[0].equals("moveToX")){
				if(values.length!=1){
					System.err.println("Usage: moveToX:x position");
					System.err.println("--> generates points, adding 'stepX' and 'stepY' to the current point until x = 'x position'");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				int count=(int)((valuesDouble[0]-posX)/stepX);
				count=Math.abs(count);
				for(int i=0;i<count;i++){
					posX+=stepX;
					posY+=stepY;
					addValues();
				}
			}else if(command[0].equals("moveTo")){
				if(values.length!=3){
					System.err.println("Usage: moveTo:x position, y position, count");
					System.err.println("--> generates 'count' points drawing a line to x/y position");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				double a=valuesDouble[0]-posX;
				double b=valuesDouble[1]-posY;
				double c=Math.sqrt(a*a+b*b);
				int count=(int)(c/valuesDouble[2]);
				count=Math.abs(count);
				double stepX=a/count;
				double stepY=b/count;
				for(int i=0;i<count;i++){
					posX+=stepX;
					posY+=stepY;
					addValues();
				}
			}else if(command[0].equals("sector")){
				if(values.length!=5){
					System.err.println("Usage: sector:centre x delta, centre y delta, angle, count, direction");
					System.err.println("--> generates a part of a circle");
					System.err.println("\tcentre x,centre y: distance from the current point to the centre of the circle");
					System.err.println("\tangle ...of the sector to draw in degree");
					System.err.println("\tcount number of point to draw");
					System.err.println("\tdirection: 0=clockwise, 1=counterclockwise");
					System.err.println("Example: sector:300,300,3,160,1");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				double a=valuesDouble[0]-posX;
				double b=valuesDouble[1]-posY;
				double c=Math.sqrt(a*a+b*b);

				double pi=3.141592654;
				double rad=pi/180.0;
				double u=c*2*pi;
				double points=u/valuesDouble[2]*valuesDouble[3]/360.0;
				double angleStep=valuesDouble[3]/points;
				angleStep=Math.abs(angleStep);

				double startAngle=Math.atan(b/a)/rad;
				if(a<0){
					//startAngle-=90.0;
				}else if(valuesDouble[0]>0 && valuesDouble[1]>0){
					startAngle+=180.0;
				}else if(valuesDouble[1]>0){
					startAngle+=180.0;
				}

				if(valuesDouble[4]==0){
					for(double angle=startAngle;angle<(startAngle+valuesDouble[3]);angle+=angleStep){
						posX=valuesDouble[0]+Math.cos(angle*rad)*c;
						posY=valuesDouble[1]+Math.sin(angle*rad)*c;
						addValues();
					}
				}else{
					for(double angle=startAngle;angle>(startAngle-valuesDouble[3]);angle-=angleStep){
						posX=valuesDouble[0]+Math.cos(angle*rad)*c;
						posY=valuesDouble[1]+Math.sin(angle*rad)*c;
						addValues();
					}
				}
			}else if(command[0].equals("spline")){
				if(values.length!=5){
					System.err.println("Usage: spline:spline x spline y, endpoint x, endpoint y, count");
					System.err.println("--> generates a spline using count points");
					System.err.println("Example: spline:80,500,900,500,150");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				spline(posX,posY,valuesDouble[0],valuesDouble[1],valuesDouble[2],valuesDouble[3],(int)valuesDouble[4]);
			}else if(command[0].equals("splineCV")){
				if(values.length!=5){
					System.err.println("Usage: splineCV:spline x spline y, endpoint x, endpoint y, step");
					System.err.println("--> generates a spline with Constant Velocity, using 'step' delta");
					System.err.println("Example: splineCV:80,500,900,500,5");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				spline2(posX,posY,valuesDouble[0],valuesDouble[1],valuesDouble[2],valuesDouble[3],(int)valuesDouble[4]);
			}else if(command[0].equals("mirrorX")){
				if(values.length!=1){
					System.err.println("Usage: mirrorX:switch");
					System.err.println("--> after mirrorX:1, all following x points are mirrored (middle of screen), until mirroring is switched off by mirrorX:0");
					System.err.println("Example: mirrorX:1");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				mirrorX=valuesDouble[0]>0;
			}else if(command[0].equals("mirrorY")){
				if(values.length!=1){
					System.err.println("Usage: mirrorY:switch");
					System.err.println("--> after mirrorY:1, all following y points are mirrored (middle of screen), until mirroring is switched off by mirrorY:0");
					System.err.println("Example: mirrorY:1");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				mirrorY=valuesDouble[0]>0;
			}else if(command[0].equals("deltaX")){
				if(values.length!=1){
					System.err.println("Usage: deltaX:delta");
					System.err.println("--> adds 'delta' to x of all following points");
					System.err.println("Example: deltaX:50");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				deltaX=(int)valuesDouble[0];
			}else if(command[0].equals("deltaY")){
				if(values.length!=1){
					System.err.println("Usage: deltaY:delta");
					System.err.println("--> adds 'delta' to y of all following points");
					System.err.println("Example: deltaY:50");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				deltaY=(int)valuesDouble[0];
			}else if(command[0].equals("loop")){
				loopIndex=arrayListX.size()-1;
				if(values.length!=1){
					System.err.println("Usage: loop:movementtype");
					System.err.println("--> marks the current position as loop point, and sets the movementtype:");
					System.err.println("1: loop");
					System.err.println("2: pingpong");
					System.err.println("Example: loop:1");
					throw new IllegalArgumentException("Command: "+command[0]+", parameter: "+command[1]);
				}
				movementtype=(int)valuesDouble[0];
			}else{
				throw new RuntimeException("Unknown command: "+command[0]);
			}
		}
	}

	private void spline(double x1,double y1,double x2,double y2,double x3,double y3,int step){
		double point1X=x1;
		double point1Y=y1;
		double point1StepX=(x2-x1)/step;
		double point1StepY=(y2-y1)/step;
		
		double deltaSplineX;
		double deltaSplineY;
		
		double point2X=x2;
		double point2Y=y2;
		double point2StepX=(x3-x2)/step;
		double point2StepY=(y3-y2)/step;
		
		for(int i=0;i<step;i++){
			point1X+=point1StepX;
			point1Y+=point1StepY;
			point2X+=point2StepX;
			point2Y+=point2StepY;

			deltaSplineX=point2X-point1X;
			deltaSplineY=point2Y-point1Y;
			
			posX=point1X+(deltaSplineX*i/step);
			posY=point1Y+(deltaSplineY*i/step);
			addValues();
		}
		
	}
	
	private void spline2(double x1,double y1,double x2,double y2,double x3,double y3,int delta){
		int step=2000;
		double point1X=x1;
		double point1Y=y1;
		double point1StepX=(x2-x1)/step;
		double point1StepY=(y2-y1)/step;
		
		double deltaSplineX;
		double deltaSplineY;
		
		double point2X=x2;
		double point2Y=y2;
		double point2StepX=(x3-x2)/step;
		double point2StepY=(y3-y2)/step;

		double lastX=posX;
		double lastY=posY;
		
		for(int i=0;i<step;i++){
			point1X+=point1StepX;
			point1Y+=point1StepY;
			point2X+=point2StepX;
			point2Y+=point2StepY;

			deltaSplineX=point2X-point1X;
			deltaSplineY=point2Y-point1Y;
			
			posX=point1X+(deltaSplineX*i/step);
			posY=point1Y+(deltaSplineY*i/step);
	
			if(checkDeltaC(lastX,lastY,posX,posY,delta)){
				addValues();
				lastX=posX;
				lastY=posY;
			}
		}
	}

	private boolean checkDeltaC(double lastX,double lastY,double posX,double posY,double minDelta){
		double a=lastX-posX;
		double b=lastY-posY;
		double c=Math.sqrt(a*a+b*b);
		return c>=minDelta;
	}
	
	public int[] createXArray(){
		int[] movementXLane=new int[arrayListX.size()];
		for(int i=0;i<arrayListX.size();i++){
			movementXLane[i]=arrayListX.get(i).intValue();
		}
		return movementXLane;
	}
	
	public int[] createYArray(){
		int[] movementYLane=new int[arrayListY.size()];
		for(int i=0;i<arrayListY.size();i++){
			movementYLane[i]=arrayListY.get(i).intValue();
		}
		return movementYLane;
	}
	
	private void addValues(){
		int x=deltaX+(int)(posX+0.5);
		int y=deltaY+(int)(posY+0.5);
		
		if(mirrorX){
			x=1024-32-x;
		}
		if(mirrorY){
			y=600-y;
		}
		arrayListX.add(new Integer(x<<shift));
		arrayListY.add(new Integer(y<<shift));
	}

	public void resetMovement() {
		surfPosition=-1;
	}
	
	public int nextPosition(int position) {
		if(position==movementXLane.length-1) {
			if(movementtype==MOVEMENTTYPE_RUNSTOP) {
				return -1;
			}

			if(movementtype==MOVEMENTTYPE_LOOP) {
				return loopIndex;
			}else if(movementtype==MOVEMENTTYPE_PINGPONG && movementdirection==1){
				movementdirection=-1;
			}
		}else if(position==loopIndex+1 && movementdirection==-1) {
			position+=movementdirection;
			x=movementXLane[position];
			y=movementYLane[position];
			movementdirection=1;
			return position;
		}
		position+=movementdirection;
		x=movementXLane[position];
		y=movementYLane[position];
		return position;
	}
	
	public int getNextX() {
		return movementXLane[surfPosition];
	}
	
	public int getNextY() {
		return movementYLane[surfPosition];
	}

	public int getX() {
		return x << Bob.SHIFT;
	}
	
	public int getY() {
		return y << Bob.SHIFT;
	}

}
