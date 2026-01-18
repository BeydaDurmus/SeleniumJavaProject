# Selenium Test Automation Dockerfile
# Using Selenium standalone chrome image for better compatibility
FROM selenium/standalone-chrome:4.15.0

USER root

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src
COPY testng.xml .
COPY testng-multi.xml .

# Set environment variables
ENV HEADLESS=true
ENV BROWSER=chrome

# Default command: run tests
CMD ["mvn", "test", "-Dheadless=true", "-Dbrowser=chrome"]
