/* Solo-levelling Hunt for Dungeon Master
 * Reference sequential version 
 * Michelle Kuttel 2025, University of Cape Town
 * author of original Java code adapted with assistance from chatGPT for reframing 
 * and complex power - "mana" - function.
 * Inspired by  "Hill Climbing with Montecarlo"
 * EduHPC'22 Peachy Assignment developed by Arturo Gonzalez Escribano  (Universidad de Valladolid 2021/2022)
 */
/**
 * DungeonHunterParallel.java
 *
 * Main driver for the Dungeon Hunter assignment.
 * This program initializes the dungeon map and performs a series of searches
 * to locate the global maximum.
 *
 * Usage:
 *   java DungeonHunterParallel <gridSize> <numSearches> <randomSeed>
 *
 */
/**Edited by Thabo Dladla ,University of Cape Town
 * Assisted by Deepseek Ai ,to help design the inner class HuntResult fo result isolation to minimize race conditions and for each thread to keep trck of each local maximum, without a shared mutable state ,inspired by my lecture notes about minimizing the shared mutable states for thread safety
 * This is the second version of this programm
 */

import java.util.Random; //for the random search locations
import java.util.concurrent.*;

class DungeonHunterParallel extends RecursiveTask<DungeonHunterParallel.HuntResult>{
	private static final int SEQUENTIAL_CUTOFF = 333;
	static final boolean DEBUG=false;
	int lo,hi;
	HuntParallel[] hunts;
	// Removed static max and finder - they are not used correctly in parallel and are now handled by HuntResult

	public DungeonHunterParallel(int lo,int hi, HuntParallel[] hunts){this.lo=lo; this.hi=hi; this.hunts =hunts;}

	//timers for how long it all takes
	static long startTime = 0;
	static long endTime = 0;
	private static void tick() {startTime = System.currentTimeMillis(); }
	private static void tock(){endTime=System.currentTimeMillis(); }

    public static void main(String[] args)  {
    	
    	double xmin, xmax, ymin, ymax; //dungeon limits - dungeons are square
    	DungeonMapParallel dungeon;  //object to store the dungeon as a grid
    	
     	int numSearches=10, gateSize= 10;		
    	HuntParallel [] searches;		// Array of searches
  
    	Random rand = new Random();  //the random number generator
      	int randomSeed=0;  //set seed to have predictability for testing
        
    	if (args.length!=3) {
    		System.out.println("Incorrect number of command line arguments provided.");
    		System.exit(0);
    	}
    	
    	
    	/* Read argument values */
      	try {
    	gateSize=Integer.parseInt( args[0] );
    	 if (gateSize <= 0) {
             throw new IllegalArgumentException("Grid size must be greater than 0.");
         }
    	
    	numSearches = (int) (Double.parseDouble(args[1])*(gateSize*2)*(gateSize*2)*DungeonMapParallel.RESOLUTION);
    	
    	randomSeed=Integer.parseInt( args[2] );
        if (randomSeed < 0) {
                throw new IllegalArgumentException("Random seed must be non-negative.");
            }
        else if(randomSeed>0)  rand = new Random(randomSeed);  // BUG FIX
        } catch (NumberFormatException e) {
            System.err.println("Error: All arguments must be numeric.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
 
      	
    	xmin =-gateSize;
    	xmax = gateSize;
    	ymin = -gateSize;
    	ymax = gateSize;
    	dungeon = new DungeonMapParallel(xmin,xmax,ymin,ymax,randomSeed); // Initialize dungeon
    	
    	int dungeonRows=dungeon.getRows();
    	int dungeonColumns=dungeon.getColumns();
     	searches= new HuntParallel [numSearches];
     	


    	for (int i=0;i<numSearches;i++){ //intialize searches at random locations in dungeon
    		searches[i]=new HuntParallel(i+1, rand.nextInt(dungeonRows),
    				rand.nextInt(dungeonColumns),dungeon);}


		
        ForkJoinPool pool = new ForkJoinPool();
		tick();
		// Create task for the entire array and invoke it
		DungeonHunterParallel.HuntResult result = pool.invoke(new DungeonHunterParallel(0, searches.length, searches));
		tock();
		
		

		System.out.printf("\t dungeon size: %d,\n", gateSize);
		System.out.printf("\t rows: %d, columns: %d\n", dungeonRows, dungeonColumns);
		System.out.printf("\t x: [%f, %f], y: [%f, %f]\n", xmin, xmax, ymin, ymax );
		System.out.printf("\t Number searches: %d\n", numSearches );

		/*  Total computation time */
		System.out.printf("\n\t time: %d ms\n",endTime - startTime );
		int tmp=dungeon.getGridPointsEvaluated();
		System.out.printf("\tnumber dungeon grid points evaluated: %d  (%2.0f%s)\n",tmp,(tmp*1.0/(dungeonRows*dungeonColumns*1.0))*100.0, "%");

		/* Results*/
		System.out.printf("Dungeon Master (mana %d) found at:  ", result.maxMana );
		System.out.printf("x=%.1f y=%.1f\n\n",dungeon.getXcoord(result.row), dungeon.getYcoord(result.col) );
		dungeon.visualisePowerMap("visualiseSearch.png", false);
		dungeon.visualisePowerMap("visualiseSearchPath.png", true);
    }

	@Override
        protected HuntResult compute(){
        if ((hi - lo) <= SEQUENTIAL_CUTOFF) {
            int localMax = Integer.MIN_VALUE;
			int bestRow = -1, bestCol = -1;
			
            for (int i = lo; i < hi; i++) {
                // CORRECTED by Deepseek Ai: Use hunts[i] instead of hunts[lo]
                int mana = hunts[i].findManaPeak();
				if(mana > localMax) {
    			    localMax = mana;
				    bestRow = hunts[i].getPosRow();
				    bestCol = hunts[i].getPosCol();
    			}
    		    if(DEBUG) System.out.println("Shadow "+hunts[i].getID()+" finished at  "+mana + " in " +hunts[i].getSteps());
            }
            return new HuntResult(localMax, bestRow, bestCol);
        } else {
            int mid = (hi + lo) / 2; // More conventional midpoint calculation

            DungeonHunterParallel left = new DungeonHunterParallel(lo, mid, hunts);
            DungeonHunterParallel right = new DungeonHunterParallel(mid, hi, hunts);

           left.fork();
           HuntResult rightAns = right.compute();
           HuntResult leftAns = left.join();
            
            return (leftAns.maxMana > rightAns.maxMana) ? leftAns : rightAns;
        }
    
}
     static class HuntResult {
        final int maxMana;
        final int row;
        final int col;
        HuntResult(int max, int r, int c) {
            this.maxMana = max;
            this.row = r;
            this.col = c;
        }
    }
}
