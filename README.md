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
