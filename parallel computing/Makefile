JAVAC=javac
JAVA=java
SOURCES=DungeonHunterParallel.java DungeonMapParallel.java HuntParallel.java
ARGS ?= 20 0.2 0
all:
	$(JAVAC) $(SOURCES)

run:
	$(JAVA) DungeonHunterParallel $(ARGS)

clean:
	rm -f *.class
	rm -f visualiseSearch.png visualiseSearchPath.png
