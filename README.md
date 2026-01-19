# Latihan Perpustakaan - Microservices CI/CD & Monitoring

Proyek ini adalah sistem manajemen perpustakaan berbasis Microservices yang dilengkapi dengan infrastruktur CI/CD menggunakan **Jenkins** dan monitoring menggunakan **ELK Stack** (Elasticsearch, Logstash, Kibana).

## 🚀 Infrastruktur

Proyek ini menggunakan Docker Compose untuk menjalankan infrastruktur pendukung.

### 1. ELK Stack (Monitoring)
Digunakan untuk sentralisasi log dari semua microservices.

**Cara Menjalankan:**
```bash
docker-compose -f docker-compose-elk.yml up -d
```

**Akses Layanan:**
- **Kibana (Dashboard Log):** [http://localhost:5601](http://localhost:5601)
- **Elasticsearch:** [http://localhost:9200](http://localhost:9200)
- **Logstash:** Port 5000 (TCP/UDP)

---

### 2. Jenkins (CI/CD)
Digunakan untuk otomatisasi build, test, dan pembuatan Docker image.

**Cara Menjalankan:**
```bash
docker-compose -f docker-compose-jenkins.yml up -d
```

**Akses Layanan:**
- **Jenkins UI:** [http://localhost:8080](http://localhost:8080)

#### 📝 Panduan Konfigurasi Awal Jenkins

Setelah Jenkins berjalan, ikuti langkah-langkah berikut untuk mengkonfigurasinya:

**Langkah 1: Ambil Password Admin Awal**
Jalankan perintah ini di terminal untuk mendapatkan password login pertama kali:
```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```
Copy password yang muncul dan masukkan ke halaman login Jenkins.

**Langkah 2: Install Plugins**
1. Pilih **"Install suggested plugins"**.
2. Tunggu hingga proses instalasi selesai.

**Langkah 3: Konfigurasi Tools (Wajib)**
Agar pipeline bisa berjalan, konfigurasi Maven dan JDK secara manual:

1. Pergi ke **Dahboard** > **Manage Jenkins** > **Tools**.
2. **JDK Installations**:
   - Klik **Add JDK**.
   - Name: `JDK 21` (**Harus sama persis**).
   - **Uncheck** "Install automatically".
   - JAVA_HOME: `/opt/java/openjdk`
3. **Maven Installations**:
   - Klik **Add Maven**.
   - Name: `Maven 3.9` (**Harus sama persis**).
   - **Check** "Install automatically".
   - Pilih versi **3.9.6** (atau terbaru) dari list "Install from Apache".
4. Klik **Save**.

**Langkah 4: Buat Pipeline Job**
1. Pergi ke **Dashboard** > **New Item**.
2. Masukkan nama (misal: `Library-CI-CD`) dan pilih **Pipeline**.
3. Scroll ke bawah ke bagian **Pipeline Definition**:
   - Definition: `Pipeline script from SCM`.
   - SCM: `Git`.
   - Repository URL: Masukkan URL GitHub repository ini.
   - Branch Specifier: `*/main`.
   - Script Path: `Jenkinsfile` (biarkan default).
4. Klik **Save**.
5. Klik **Build Now** untuk menjalankan pipeline pertama kali.

---

## 🛠️ Microservices

Daftar layanan yang tersedia dalam repositori ini:
- **Eureka Server**: Service Discovery (Port 8761)
- **API Gateway**: Gerbang utama akses API (Port 9000)
- **Anggota Service**: Manajemen data anggota (Port 8081)
- **Buku Service**: Manajemen data buku (Port 8082)
- **Peminjaman Service**: Logika peminjaman buku (Port 8083)
- **Pengembalian Service**: Logika pengembalian buku (Port 8084)
- **Email Service**: Layanan notifikasi email (Port 8085)

---

### 3. Monitoring Stack (Prometheus + Grafana + Zipkin)
Digunakan untuk monitoring metrics, visualization, dan distributed tracing.

**Cara Menjalankan:**
```bash
docker-compose -f docker-compose-monitoring.yml up -d
```

**Akses Layanan:**
- **Prometheus (Metrics):** [http://localhost:9090](http://localhost:9090)
- **Grafana (Dashboards):** [http://localhost:3000](http://localhost:3000) - Login: admin/admin
- **Zipkin (Distributed Tracing):** [http://localhost:9411](http://localhost:9411)

**📊 Setup Guide:**  
Lihat [MONITORING_SETUP.md](MONITORING_SETUP.md) untuk panduan lengkap instalasi dan konfigurasi.

**Metrics yang di-monitor:**
- JVM Memory, CPU, Threads, GC
- HTTP Requests (count, latency, errors)
- Database connection pool
- RabbitMQ queue metrics
- Custom business metrics

---

## 🔗 Quick Start

### 1. Start Infrastructure
```bash
# Start ELK Stack (Logging)
docker-compose -f docker-compose-elk.yml up -d

# Start Monitoring Stack (Metrics & Tracing)
docker-compose -f docker-compose-monitoring.yml up -d

# Optional: Start Jenkins (CI/CD)
docker-compose -f docker-compose-jenkins.yml up -d
```

### 2. Build & Start Application
```bash
# Using Jenkins: Run the pipeline, atau

# Manual build:
mvn clean package -DskipTests

# Build Docker images (via Jenkins or manual)
# See Jenkinsfile for build commands

# Start all microservices
docker-compose -f docker-compose-app.yml up -d
```

### 3. Verify Services
```bash
# Check running containers
docker ps

# Check Eureka Dashboard
curl http://localhost:8761

# Check service health
curl http://localhost:8081/actuator/health
```

---

## 📊 Monitoring & Observability

### Logging (ELK Stack)
- **Kibana**: Centralized log viewing and analysis
- **Elasticsearch**: Log storage and search
- **Logstash**: Log aggregation and processing

### Metrics (Prometheus + Grafana)
- **Prometheus**: Time-series metrics database
- **Grafana**: Metrics visualization and dashboards
- Real-time monitoring of all microservices
- Pre-built Spring Boot dashboards

### Tracing (Zipkin)
- Distributed request tracing across services
- Latency analysis and bottleneck identification
- Service dependency visualization

---

## 🌐 Access URLs

| Service | URL | Credentials |
|---------|-----|-------------|
| Eureka Dashboard | http://localhost:8761 | - |
| API Gateway | http://localhost:9000 | - |
| Prometheus | http://localhost:9090 | - |
| Grafana | http://localhost:3000 | admin / admin |
| Zipkin | http://localhost:9411 | - |
| Kibana | http://localhost:5601 | - |
| RabbitMQ Management | http://localhost:15672 | guest / guest |
| Jenkins | http://localhost:8080 | - |

---

## 📝 API Endpoints

All APIs accessible via API Gateway at `http://localhost:9000`

### Anggota (Members)
- `GET /api/anggota` - Get all members
- `GET /api/anggota/{id}` - Get member by ID
- `POST /api/anggota` - Create new member
- `DELETE /api/anggota/{id}` - Delete member

### Buku (Books)
- `GET /api/buku` - Get all books
- `GET /api/buku/{id}` - Get book by ID
- `POST /api/buku` - Create new book
- `DELETE /api/buku/{id}` - Delete book

### Peminjaman (Borrowing)
- `GET /api/peminjaman` - Get all borrowings
- `GET /api/peminjaman/{id}` - Get borrowing by ID
- `GET /api/peminjaman/buku/{id}` - Get borrowings by book ID
- `POST /api/peminjaman` - Create new borrowing (sends email notification)
- `DELETE /api/peminjaman/{id}` - Delete borrowing

### Pengembalian (Return)
- `GET /api/pengembalian` - Get all returns
- `GET /api/pengembalian/{id}` - Get return by ID
- `POST /api/pengembalian` - Process book return
- `DELETE /api/pengembalian/{id}` - Delete return

---

## 🏗️ Architecture

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       v
┌─────────────────┐     ┌──────────────┐
│   API Gateway   │────▶│ Eureka Server│
│    Port 9000    │     │  Port 8761   │
└────────┬────────┘     └──────────────┘
         │
    ┌────┴────┬────────┬────────────┬────────────┐
    v         v        v            v            v
┌────────┐┌───────┐┌──────────┐┌──────────┐┌───────┐
│Anggota ││ Buku  ││Peminjaman││Pengembalian││Email  │
│ :8081  ││ :8082 ││  :8083   ││  :8084   ││ :8085 │
└────────┘└───────┘└─────┬────┘└────┬─────┘└───┬───┘
                         │           │          │
                         v           v          v
                    ┌────────────────────────────┐
                    │       RabbitMQ             │
                    │   Ports: 5672, 15672       │
                    └────────────────────────────┘

    ┌──────────────────────────────────────────┐
    │         Monitoring & Observability        │
    ├──────────────┬──────────────┬────────────┤
    │  Prometheus  │   Grafana    │   Zipkin   │
    │    :9090     │    :3000     │   :9411    │
    └──────────────┴──────────────┴────────────┘

    ┌──────────────────────────────────────────┐
    │            Logging (ELK Stack)            │
    ├──────────────┬──────────────┬────────────┤
    │Elasticsearch │   Logstash   │   Kibana   │
    │    :9200     │    :5000     │   :5601    │
    └──────────────┴──────────────┴────────────┘
```

---

## 🛠️ Technologies

| Category | Technology | Version |
|----------|-----------|---------|
| Language | Java | 21 |
| Framework | Spring Boot | 3.5.6 |
| Cloud | Spring Cloud | 2025.0.0 |
| Build Tool | Maven | 3.9 |
| Service Discovery | Netflix Eureka | - |
| API Gateway | Spring Cloud Gateway | - |
| Database | H2 (In-Memory) | - |
| Message Queue | RabbitMQ | 3-management-alpine |
| Metrics | Prometheus | latest |
| Visualization | Grafana | latest |
| Distributed Tracing | Zipkin | latest |
| Logging | ELK Stack | - |
| CI/CD | Jenkins | - |
| Container | Docker | - |
| Orchestration | Docker Compose | - |

---

## 📖 Documentation

- [MONITORING_SETUP.md](MONITORING_SETUP.md) - Complete monitoring setup guide
- [Jenkinsfile](Jenkinsfile) - CI/CD pipeline configuration

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📄 License

This project is open source and available under the MIT License.
