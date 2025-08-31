# 2nd Year - Second Semester (Computer Science)

This repository contains my second semester Computer Science work at UCT.  
The main focus here is on **Parallel Computing**.

**This project was developed from a serial base program provided by my lecture Michelle as course material. My contributions focused on extending it into a fully parallelized version using Java fork join framework**

## Parallel Computing Project

## Dungeon Hunter Parallel
This project simulates a dungeon exploration scenario, inspired by *Solo Leveling*.  
Imagine you are **Sung Jin-Woo**, summoning your shadows to **search concurrently** through a dungeon map, climbing uphill until they find the **strongest mana source** (the dungeon boss).

Main files in **parallel** computing folder :  

**DungeonHunterParallel.java** – Main entry point of the program.  
**DungeonMapParallel.java** – Handles dungeon map creation and mana distribution.  
**HuntParallel.java** – Defines the concurrent “shadow hunters” searching for the boss.  

## How to Run
Compile:
```bash
javac DungeonHunterParallel.java DungeonMapParallel.java HuntParallel.java

java DungeonHunterParallel <gate_size> <num_searchers> <seed>

gate_size = size of the dungeon map (e.g.100)  

num_searchers =number of concurrent hunters (e.g.4)  

seed = value to generate consistent dungeon maps, or 0 for randomness  


