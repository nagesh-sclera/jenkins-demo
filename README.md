# Spring Boot + Jenkins CI/CD (Local)

## Prerequisites
- Docker & Docker Compose installed
- Git installed

## Project Structure
```
springboot-jenkins-project/
├── src/
│   ├── main/java/com/example/demo/
│   │   ├── DemoApplication.java
│   │   ├── controller/HelloController.java
│   │   ├── model/ApiResponse.java
│   │   └── service/HelloService.java
│   └── resources/application.properties
├── test/java/com/example/demo/
│   ├── DemoApplicationTests.java
│   └── controller/HelloControllerTest.java
├── Dockerfile
├── Jenkinsfile
├── docker-compose.yml
└── pom.xml
```

## Step 1 — Start Jenkins Locally
```bash
docker-compose up -d
```
Jenkins UI → http://localhost:8090

## Step 2 — Get Admin Password
```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

## Step 3 — Configure Jenkins
1. Open http://localhost:8090 and paste the admin password
2. Install **suggested plugins**
3. Go to **Manage Jenkins → Global Tool Configuration**
   - Add JDK  → Name: `JDK-17`   (auto-install or set JAVA_HOME)
   - Add Maven → Name: `Maven-3.9` (auto-install)
4. Install the **Docker Pipeline** plugin

## Step 4 — Create Pipeline Job
1. New Item → **Pipeline** → name it `springboot-demo`
2. Pipeline section → Definition: **Pipeline script from SCM**
3. SCM: Git → paste your repo URL (or use "Pipeline script" and paste Jenkinsfile directly)
4. Script Path: `Jenkinsfile`
5. Click **Save → Build Now**

## Available Endpoints After Deploy
| URL | Description |
|-----|-------------|
| http://localhost:9090/api/hello        | Welcome message     |
| http://localhost:9090/api/greet/{name} | Greet by name       |
| http://localhost:9090/api/health       | App health          |
| http://localhost:9090/actuator/health  | Spring Actuator     |

## Pipeline Stages
```
Checkout → Compile → Test → Package JAR → Build Docker Image → Deploy → Health Check → Cleanup
```

## Troubleshooting
- If Docker permission error in Jenkins: ensure `/var/run/docker.sock` is mounted
- If port 9090 is busy: change HOST_PORT in Jenkinsfile
- If Maven/JDK not found: verify tool names match in Global Tool Configuration
