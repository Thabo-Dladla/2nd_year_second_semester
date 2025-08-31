# benchmark_script.ps1 - Fixed PowerShell version

Write-Output "Starting benchmark..."
Write-Output "Date: $(Get-Date)"
Write-Output ""

# List of gate sizes to test
$gateSizes = @(10, 25, 50, 100, 250, 500)

foreach ($gate in $gateSizes) {
    # List of cutoff values to test
    $cutoffs = @(10, 25, 50, 100, 250, 500, 1000)
    
    foreach ($cutoff in $cutoffs) {
        Write-Output "--- GateSize = $gate, Cutoff = $cutoff ---"
        
        # Run the Java program
        java DungeonHunterParallel $gate 0.1 32 $cutoff
        
        # Add a blank line between runs
        Write-Output ""
    }
}

Write-Output "Benchmark completed at: $(Get-Date)"