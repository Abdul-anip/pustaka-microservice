# Script untuk mengganti semua "asyrafil" dengan "hanif"
# Run from project root directory

Write-Host "=== Renaming Package from 'asyrafil' to 'hanif' ===" -ForegroundColor Green
Write-Host ""

# 1. Replace in POM.xml files
Write-Host "[1/3] Updating POM.xml files..." -ForegroundColor Yellow
$pomFiles = Get-ChildItem -Path . -Recurse -Filter "pom.xml" -File

foreach ($file in $pomFiles) {
    $content = Get-Content $file.FullName -Raw
    if ($content -match "asyrafil") {
        $newContent = $content -replace "asyrafil", "hanif"
        Set-Content -Path $file.FullName -Value $newContent -NoNewline
        Write-Host "  ✓ Updated: $($file.FullName)" -ForegroundColor Green
    }
}

# 2. Replace in Java source files
Write-Host ""
Write-Host "[2/3] Updating Java source files..." -ForegroundColor Yellow
$javaFiles = Get-ChildItem -Path . -Recurse -Filter "*.java" -File

foreach ($file in $javaFiles) {
    $content = Get-Content $file.FullName -Raw
    if ($content -match "asyrafil") {
        $newContent = $content -replace "asyrafil", "hanif"
        Set-Content -Path $file.FullName -Value $newContent -NoNewline
        Write-Host "  ✓ Updated: $($file.FullName)" -ForegroundColor Green
    }
}

# 3. Rename package directories
Write-Host ""
Write-Host "[3/3] Renaming package directories..." -ForegroundColor Yellow

$services = @("anggota", "buku", "peminjaman", "pengembalian", "email", "api_gateway", "eureka_server")

foreach ($service in $services) {
    $oldPath = ".\$service\src\main\java\com\asyrafil"
    $newPath = ".\$service\src\main\java\com\hanif"
    
    if (Test-Path $oldPath) {
        # Create parent directory if not exists
        $parentDir = Split-Path $newPath -Parent
        if (!(Test-Path $parentDir)) {
            New-Item -ItemType Directory -Path $parentDir -Force | Out-Null
        }
        
        # Move directory
        Move-Item -Path $oldPath -Destination $newPath -Force
        Write-Host "  ✓ Renamed: $oldPath -> $newPath" -ForegroundColor Green
    }
    
    # Rename in test directory too
    $oldTestPath = ".\$service\src\test\java\com\asyrafil"
    $newTestPath = ".\$service\src\test\java\com\hanif"
    
    if (Test-Path $oldTestPath) {
        $parentDir = Split-Path $newTestPath -Parent
        if (!(Test-Path $parentDir)) {
            New-Item -ItemType Directory -Path $parentDir -Force | Out-Null
        }
        
        Move-Item -Path $oldTestPath -Destination $newTestPath -Force
        Write-Host "  ✓ Renamed: $oldTestPath -> $newTestPath" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "=== Done! ===" -ForegroundColor Green
Write-Host ""
Write-Host "Summary of changes:" -ForegroundColor Cyan
Write-Host "  • All 'com.asyrafil' replaced with 'com.hanif' in source files"
Write-Host "  • All groupId changed from 'com.asyrafil' to 'com.hanif' in POM files"
Write-Host "  • Package directories renamed from 'asyrafil' to 'hanif'"
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "  1. Verify changes: git diff (if using git)"
Write-Host "  2. Rebuild project: mvn clean package -DskipTests"
Write-Host "  3. Rebuild Docker images if needed"
