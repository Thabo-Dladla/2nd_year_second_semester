# Simple version for just one density
$gates = @(10, 25, 50, 75, 100, 150, 250, 500)

foreach ($gate in $gates) {
    Write-Host "--- Parallel: GateSize = $gate ---" -ForegroundColor Green
    java DungeonHunterParallel $gate 0.1 32
    Write-Host ""
}