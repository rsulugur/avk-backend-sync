FROM ubuntu:24.04
LABEL authors="Rohit Reddy"
FROM ubuntu:24.10 AS builder
# Set the working directory
WORKDIR /app

# Install dependencies
RUN apt-get update && apt-get install -y maven openjdk-17-jdk wget zip && apt-get clean

# Set Java 17 as JAVA_HOME so maven can use 17 to build the JAR File.
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
RUN export JAVA_HOME

# Install Google Chrome
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y ./google-chrome-stable_current_amd64.deb

ENV CHROMEDRIVER_VERSION=120.0.6099.71
RUN wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$CHROMEDRIVER_VERSION/linux64/chromedriver-linux64.zip
RUN unzip chromedriver-linux64.zip && rm -dfr chromedriver-linux64.zip
RUN mkdir /usr/bin/chromedriver
RUN mv ./chromedriver-linux64/chromedriver /usr/bin/chromedriver
RUN chmod +x /usr/bin/chromedriver

# Copy all files from the current directory to /src in the container
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/app/target/gpt-0.0.1-SNAPSHOT.jar"]