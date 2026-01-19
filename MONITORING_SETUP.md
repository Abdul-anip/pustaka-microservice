# Monitoring System Setup Guide

## 🎯 Overview
This guide will help you add comprehensive monitoring to your microservices using:
- **Prometheus** - Metrics collection
- **Grafana** - Visualization dashboards
- **Zipkin** - Distributed tracing

---

## 📦 Files Created

✅ `docker-compose-monitoring.yml` - Monitoring stack  
✅ `prometheus/prometheus.yml` - Prometheus configuration  
✅ `grafana/provisioning/datasources/prometheus.yml` - Grafana datasource  
✅ `grafana/provisioning/dashboards/dashboard.yml` - Grafana dashboard config  
✅ `add-monitoring-deps.ps1` - Script to update POM files  
✅ `monitoring-config-template.properties` - Template for .properties files  
✅ `monitoring-config-template.yml` - Template for .yml files  
✅ `docker-compose-app.yml` - Updated with Zipkin configuration  

---

## 🚀 Installation Steps

### Step 1: Update POM.xml Files

Run the PowerShell script to automatically add monitoring dependencies:

```powershell
.\add-monitoring-deps.ps1
```

**What it does:**
- Adds `spring-boot-starter-actuator`
- Adds `micrometer-registry-prometheus`
- Adds `micrometer-tracing-bridge-brave`
- Adds `zipkin-reporter-brave`

**Manual alternative (if script fails):**
Open each `pom.xml` in: `anggota`, `buku`, `peminjaman`, `pengembalian`, `email`, `api_gateway`, `eureka_server`

Add before `<dependency>` for `spring-boot-starter-test`:

```xml
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
```

---

### Step 2: Update Application Configuration Files

#### For services with `application.properties`:
- `anggota/src/main/resources/application.properties`
- `buku/src/main/resources/application.properties`
- `peminjaman/src/main/resources/application.properties`
- `pengembalian/src/main/resources/application.properties`
- `email/src/main/resources/application.properties`

**Add these lines at the end:**

```properties
# Actuator Configuration
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=always
management.metrics.export.prometheus.enabled=true
management.health.defaults.enabled=true

# Application Info
info.app.name=${spring.application.name}
info.app.description=Library Management Microservice
info.app.version=1.0.0

# Distributed Tracing (Zipkin)
management.tracing.sampling.probability=1.0
management.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans
```

#### For services with `application.yml`:
- `eureka_server/src/main/resources/application.yml`
- `api_gateway/src/main/resources/application.yml`

**Add these lines:**

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
  health:
    defaults:
      enabled: true
  tracing:
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://zipkin:9411/api/v2/spans

info:
  app:
    name: ${spring.application.name}
    description: Library Management Microservice
    version: 1.0.0
```

---

### Step 3: Rebuild All Services

```powershell
# Build all services
mvn clean package -DskipTests
```

Or use Jenkins pipeline (it will handle this automatically).

---

### Step 4: Rebuild Docker Images

If using Jenkins, run the pipeline:
```
Build Now
```

**Manual alternative:**
```powershell
# Eureka Server
cd eureka_server
docker build -t eureka-server:latest .

# API Gateway
cd ../api_gateway
docker build -t api-gateway:latest .

# Anggota Service
cd ../anggota
docker build -t anggota-service:latest .

# Buku Service
cd ../buku
docker build -t buku-service:latest .

# Peminjaman Service
cd ../peminjaman
docker build -t peminjaman-service:latest .

# Pengembalian Service
cd ../pengembalian
docker build -t pengembalian-service:latest .

# Email Service
cd ../email
docker build -t email-service:latest .

cd ..
```

---

### Step 5: Start Monitoring Stack

First, make sure ELK network exists (from ELK Stack setup):

```powershell
docker network ls | findstr elk
```

If not exists, create it:
```powershell
docker network create elk
```

Then start monitoring stack:

```powershell
docker-compose -f docker-compose-monitoring.yml up -d
```

**Verify:**
```powershell
docker ps | findstr "prometheus\|grafana\|zipkin"
```

You should see 3 containers running:
- prometheus
- grafana
- zipkin

---

### Step 6: Restart Application Services

```powershell
docker-compose -f docker-compose-app.yml down
docker-compose -f docker-compose-app.yml up -d
```

---

### Step 7: Verify Everything is Working

#### Check Actuator Endpoints

```powershell
# Anggota Service
curl http://localhost:8081/actuator/health
curl http://localhost:8081/actuator/prometheus

# Buku Service
curl http://localhost:8082/actuator/health
curl http://localhost:8082/actuator/prometheus

# Peminjaman Service
curl http://localhost:8083/actuator/health
curl http://localhost:8083/actuator/prometheus

# Pengembalian Service
curl http://localhost:8084/actuator/health
curl http://localhost:8084/actuator/prometheus

# Email Service
curl http://localhost:8085/actuator/health
curl http://localhost:8085/actuator/prometheus

# API Gateway
curl http://localhost:9000/actuator/health
curl http://localhost:9000/actuator/prometheus

# Eureka Server
curl http://localhost:8761/actuator/health
curl http://localhost:8761/actuator/prometheus
```

#### Check Prometheus Targets

1. Open: http://localhost:9090
2. Go to **Status** > **Targets**
3. All targets should show **UP** (green)

Expected targets:
- prometheus
- eureka-server
- api-gateway
- anggota-service
- buku-service
- peminjaman-service
- pengembalian-service
- email-service

---

### Step 8: Configure Grafana Dashboards

#### Login to Grafana

URL: http://localhost:3000  
Username: `admin`  
Password: `admin`

(You'll be prompted to change password on first login)

#### Import Spring Boot Dashboard

1. Click **+** (Create) > **Import**
2. Enter dashboard ID: **11378**
3. Click **Load**
4. Select **Prometheus** as data source
5. Click **Import**

This dashboard shows:
- JVM Memory
- CPU Usage
- HTTP Requests
- Response Times
- Error Rates

#### Import JVM Dashboard

1. Click **+** > **Import**
2. Enter dashboard ID: **4701**
3. Click **Load**
4. Select **Prometheus** as data source
5. Click **Import**

#### Create Custom Dashboard (Optional)

1. Click **+** > **Create** > **Dashboard**
2. Add panels with queries like:
   - `http_server_requests_seconds_count` - Request count
   - `http_server_requests_seconds_sum` - Total time
   - `jvm_memory_used_bytes` - Memory usage
   - `system_cpu_usage` - CPU usage

---

### Step 9: Verify Distributed Tracing

#### Open Zipkin

URL: http://localhost:9411

#### Generate some traces

Make some API calls:

```powershell
# Create anggota
curl -X POST http://localhost:9000/api/anggota -H "Content-Type: application/json" -d "{\"nama\":\"Test User\"}"

# Create buku
curl -X POST http://localhost:9000/api/buku -H "Content-Type: application/json" -d "{\"judul\":\"Test Book\"}"

# Create peminjaman
curl -X POST http://localhost:9000/api/peminjaman -H "Content-Type: application/json" -d "{\"anggota_id\":1,\"buku_id\":1}"
```

#### View traces in Zipkin

1. Go to http://localhost:9411
2. Click **Run Query**
3. You should see traces for your API calls
4. Click on a trace to see the full request flow across services

---

## 🎨 What You Can Monitor

### Application Metrics
- **HTTP Requests**: Count, duration, errors
- **JVM**: Memory (heap/non-heap), GC, threads
- **Database**: Connection pool, query time
- **RabbitMQ**: Queue depth, message rate
- **Custom Business Metrics**: Can be added

### Infrastructure Metrics
- CPU usage
- Memory usage
- Disk I/O
- Network I/O

### Distributed Tracing
- Request flow across microservices
- Latency analysis
- Error tracking
- Dependency visualization

---

## 📊 Access URLs

| Service | URL | Credentials |
|---------|-----|-------------|
| **Prometheus** | http://localhost:9090 | - |
| **Grafana** | http://localhost:3000 | admin / admin |
| **Zipkin** | http://localhost:9411 | - |
| **Eureka Dashboard** | http://localhost:8761 | - |
| **RabbitMQ Management** | http://localhost:15672 | guest / guest |
| **Kibana** | http://localhost:5601 | - |
| **API Gateway** | http://localhost:9000 | - |

---

## 🛠️ Troubleshooting

### Prometheus targets are DOWN

**Check:**
1. Are all microservices running?
   ```bash
   docker ps
   ```

2. Can Prometheus reach the services?
   ```bash
   docker exec prometheus ping anggota-service
   ```

3. Are actuator endpoints exposed?
   ```bash
   curl http://localhost:8081/actuator/prometheus
   ```

### Grafana shows "No Data"

**Check:**
1. Is Prometheus datasource configured?
   - Go to Configuration > Data Sources
   - Ensure Prometheus is set to `http://prometheus:9090`

2. Are there metrics in Prometheus?
   - Go to http://localhost:9090
   -  Type: `up` in query box
   - Should show all services

### Zipkin shows no traces

**Check:**
1. Environment variables set correctly in docker-compose-app.yml
2. Zipkin endpoint reachable from services:
   ```bash
   docker exec anggota-service ping zipkin
   ```

3. Sample rate is 1.0 (100% of requests traced)

---

## 🔧 Advanced Configuration

### Custom Metrics

Add to any service:

```java
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class MyService {
    private final Counter peminjamanCounter;
    
    public MyService(MeterRegistry registry) {
        this.peminjamanCounter = Counter.builder("peminjaman.created")
            .description("Number of peminjaman created")
            .register(registry);
    }
    
    public void createPeminjaman() {
        // business logic
        peminjamanCounter.increment();
    }
}
```

### Alerting (Optional)

Create `prometheus/alert-rules.yml`:

```yaml
groups:
  - name: microservices
    rules:
      - alert: ServiceDown
        expr: up == 0
        for: 1m
        labels:
          severity: critical
        annotations:
          summary: "Service {{ $labels.job }} is down"
```

Update `prometheus.yml`:
```yaml
rule_files:
  - "alert-rules.yml"
```

---

## ✅ Verification Checklist

- [ ] POM files updated with dependencies
- [ ] Application properties/yml updated
- [ ] All services rebuilt (mvn clean package)
- [ ] Docker images rebuilt
- [ ] Monitoring stack running (Prometheus, Grafana, Zipkin)
- [ ] Application services restarted
- [ ] Actuator endpoints accessible
- [ ] Prometheus targets all UP
- [ ] Grafana dashboards imported
- [ ] Zipkin receiving traces

---

## 📚 Next Steps

1. **Customize Grafana Dashboards**
   - Add business-specific metrics
   - Configure alert notifications

2. **Add Custom Metrics**
   - Track business KPIs
   - Monitor specific operations

3. **Setup Alerting**
   - Configure Prometheus alert rules
   - Integrate with notification channels (email, Slack, etc.)

4. **Performance Tuning**
   - Analyze slow queries
   - Optimize resource usage
   - Identify bottlenecks

---

## 🎉 Success!

Your microservices now have comprehensive monitoring with:
✅ Real-time metrics collection  
✅ Beautiful visualization dashboards  
✅ Distributed request tracing  
✅ Health monitoring  
✅ Performance insights  

Happy monitoring! 📊
