# Example benchmark_script.sh for the server
for gate in 10 25 50 100 250 500
do
  for cutoff in 10 25 50 100 250 500 1000
  do
    echo "--- GateSize = $gate, Cutoff = $cutoff ---"
    java DungeonHunterParallel $gate 0.1 32 $cutoff
  done
done 
