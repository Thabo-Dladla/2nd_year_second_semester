for gate in 10 25 50 75 100 150 250 500
do
    echo "--- Parallel: GateSize = $gate ---"
    java DungeonHunterParallel $gate 0.1 32
    echo ""
done
