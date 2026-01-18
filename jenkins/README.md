# Jenkins CI/CD Setup Guide

Panduan lengkap untuk setup dan konfigurasi Jenkins CI/CD pada proyek Library Management System.

## Prerequisites

- Docker dan Docker Compose terinstall
- Maven 3.9+
- JDK 21
- Git

## Quick Start

### 1. Start Jenkins

```powershell
# Start Jenkins dengan Docker Compose
docker-compose -f docker-compose-jenkins.yml up -d

# Check logs
docker-compose -f docker-compose-jenkins.yml logs -f jenkins
```

Jenkins akan tersedia di: `http://localhost:8080`

### 2. Initial Setup

1. **Get Initial Admin Password**
   ```powershell
   docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
   ```

2. **Install Suggested Plugins**
   - Pilih "Install suggested plugins" saat first-time setup
   - Plugin tambahan yang diperlukan:
     - Pipeline
     - Docker Pipeline
     - Git
     - Maven Integration
     - Workspace Cleanup

3. **Create Admin User**
   - Buat user admin sesuai kebutuhan

### 3. Configure Tools

#### Maven Configuration
1. Manage Jenkins → Global Tool Configuration
2. Maven installations → Add Maven
   - Name: `Maven 3.9`
   - Install automatically: ✓
   - Version: `3.9.x`

#### JDK Configuration
1. Manage Jenkins → Global Tool Configuration
2. JDK installations → Add JDK
   - Name: `JDK 21`
   - Install automatically: ✓
   - Version: `21`

#### Docker Configuration
Docker sudah tersedia karena menggunakan Docker-in-Docker (DinD).

### 4. Create Pipeline Jobs

#### Option A: Main Pipeline (Recommended)

1. New Item → Pipeline
2. Name: `Library-Management-CI-CD`
3. Pipeline section:
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `<your-git-repo-url>`
   - Branch: `*/main` atau `*/master`
   - Script Path: `Jenkinsfile`
4. Save

#### Option B: Individual Service Pipelines

Ulangi langkah di atas untuk setiap service dengan Script Path:
- `eureka_server/Jenkinsfile`
- `api_gateway/Jenkinsfile`
- `anggota/Jenkinsfile`
- `buku/Jenkinsfile`
- `peminjaman/Jenkinsfile`
- `pengembalian/Jenkinsfile`
- `email/Jenkinsfile`

### 5. Run Pipeline

1. Buka pipeline job
2. Click "Build Now"
3. Monitor build progress di "Console Output"

## Pipeline Stages

### Main Pipeline (`Jenkinsfile`)

1. **Checkout**: Clone repository
2. **Build All Services**: Parallel Maven build untuk semua services
3. **Test All Services**: Parallel unit testing
4. **Build Docker Images**: Build Docker images untuk semua services
5. **Verify Images**: Verifikasi images berhasil dibuat

### Individual Service Pipeline

1. **Checkout**: Clone repository
2. **Build**: Maven build untuk service
3. **Test**: Unit testing
4. **Build Docker Image**: Build Docker image dengan tag build number
5. **Verify Image**: Verifikasi image

## Docker Images

Setelah pipeline berhasil, Docker images akan tersedia:

```powershell
docker images
```

Output:
```
eureka-server:latest
api-gateway:latest
anggota-service:latest
buku-service:latest
peminjaman-service:latest
pengembalian-service:latest
email-service:latest
```

## Troubleshooting

### Issue: Maven build fails

**Solution:**
- Check Maven dan JDK configuration di Global Tool Configuration
- Verify `pom.xml` tidak ada error
- Check console output untuk detail error

### Issue: Docker build fails

**Solution:**
- Verify Docker daemon running: `docker ps`
- Check Dockerfile syntax
- Ensure Docker socket mounted: `/var/run/docker.sock`

### Issue: Tests failing

**Solution:**
- Run tests locally: `mvn test`
- Check test logs di Jenkins console output
- Verify dependencies tersedia

### Issue: Permission denied on Docker

**Solution:**
```powershell
# Restart Jenkins container
docker-compose -f docker-compose-jenkins.yml restart jenkins
```

### Issue: Jenkins can't access Git repository

**Solution:**
1. Manage Jenkins → Manage Credentials
2. Add credentials untuk Git (username/password atau SSH key)
3. Update pipeline configuration untuk menggunakan credentials

## Advanced Configuration

### Webhook Integration

Setup webhook untuk auto-trigger build saat push ke Git:

1. **GitHub/GitLab**:
   - Repository Settings → Webhooks
   - URL: `http://<jenkins-url>:8080/github-webhook/` (GitHub)
   - URL: `http://<jenkins-url>:8080/project/<job-name>` (GitLab)

2. **Jenkins**:
   - Job Configuration → Build Triggers
   - Enable: `GitHub hook trigger for GITScm polling`

### Environment Variables

Tambahkan environment variables di pipeline:

```groovy
environment {
    DOCKER_REGISTRY = 'your-registry'
    APP_VERSION = '1.0.0'
}
```

### Notifications

Setup email notifications di `post` section:

```groovy
post {
    success {
        emailext (
            subject: "Build Success: ${env.JOB_NAME}",
            body: "Build completed successfully",
            to: "team@example.com"
        )
    }
}
```

## Maintenance

### Cleanup Old Images

```powershell
# Remove dangling images
docker image prune -f

# Remove old images
docker images | findstr "days ago" | awk '{print $3}' | xargs docker rmi
```

### Backup Jenkins Data

```powershell
# Backup Jenkins home

# Pull latest image
docker pull jenkins/jenkins:lts

# Recreate container
docker-compose -f docker-compose-jenkins.yml up -d --force-recreate jenkins

1. Start ELK stack:
   docker-compose -f docker-compose-elk.yml up -d

2. Access Kibana: `http://localhost:5601`

3. Configure log shipping dari Jenkins ke Logstash (optional)

## Best Practices

1. **Use Multibranch Pipeline** untuk support multiple branches
2. **Enable Build Triggers** untuk automated builds
3. **Archive Artifacts** untuk JAR files
4. **Use Shared Libraries** untuk reusable pipeline code
5. **Implement Quality Gates** dengan SonarQube integration
6. **Regular Backups** dari Jenkins configuration

## Resources

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Pipeline Syntax](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [Docker Pipeline Plugin](https://plugins.jenkins.io/docker-workflow/)
