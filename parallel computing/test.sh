#!/bin/bash
# test_single_density.sh
GATE_SIZE=100
RANDOM_SEED=32

# Get density and runs from command line arguments
DENSITY=${1:-0.1}    # First argument: density (default: 0.1)
RUNS=${2:-10}        # Second argument: number of runs (default: 10)

echo "Testing density: $DENSITY ($RUNS runs)"

for ((i=1; i<=$RUNS; i++)); do
    echo "=== Run $i ==="
    java DungeonHunterParallel $GATE_SIZE $DENSITY $RANDOM_SEED
    echo "=============="
done
