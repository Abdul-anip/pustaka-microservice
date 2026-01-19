# Script untuk menambahkan monitoring dependencies ke semua microservices
# Run this script dari root directory project

Write-Host "=== Adding Monitoring Dependencies to All Microservices ===" -ForegroundColor Green

$services = @("anggota", "buku", "peminjaman", "pengembalian", "email", "api_gateway", "eureka_server")

$monitoringDeps = @"

	<!-- Monitoring Dependencies -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
	<dependency>
		<groupId>io.micrometer</groupId>
		<artifactId>micrometer-registry-prometheus</artifactId>
	</dependency>
	<dependency>
		<groupId>io.micrometer</groupId>
		<artifactId>micrometer-tracing-bridge-brave</artifactId>
	</dependency>
	<dependency>
		<groupId>io.zipkin.reporter2</groupId>
		<artifactId>zipkin-reporter-brave</artifactId>
	</dependency>

"@

foreach ($service in $services) {
    $pomFile = ".\$service\pom.xml"
    
    if (Test-Path $pomFile) {
        Write-Host "Processing $service..." -ForegroundColor Yellow
        
        # Read the content
        $content = Get-Content $pomFile -Raw
        
        # Check if actuator already exists
        if ($content -match "spring-boot-starter-actuator") {
            Write-Host "  [SKIP] Monitoring dependencies already exist in $service" -ForegroundColor Cyan
            continue
        }
        
        # Find the test dependency and insert before it
        $testPattern = '(\s+<dependency>\s+<groupId>org\.springframework\.boot</groupId>\s+<artifactId>spring-boot-starter-test</artifactId>)'
        
        if ($content -match $testPattern) {
            $newContent = $content -replace $testPattern, ($monitoringDeps + '$1')
            Set-Content -Path $pomFile -Value $newContent -NoNew line
            Write-Host "  [OK] Added monitoring dependencies to $service" -ForegroundColor Green
        } else {
            Write-Host "  [ERROR] Could not find test dependency in $service" -ForegroundColor Red
        }
    } else {
        Write-Host "  [ERROR] pom.xml not found for $service" -ForegroundColor Red
    }
}

Write-Host "`n=== Done! ===" -ForegroundColor Green
Write-Host "Next steps:"
Write-Host "1. Update application.properties/yml files"
Write-Host "2. Run: mvn clean package -DskipTests" -ForegroundColor Cyan
