# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

#script to wait for the mysql container to initialize before the app container starts
# COPY wait-for-it.sh /wait-for-it.sh
# Copy the JAR file from the target directory into the container
COPY target/library-0.0.7.jar app.jar

# Expose the port the application will run on
EXPOSE 9100

# Install netcat (nc)
RUN apt-get update && apt-get install -y netcat

#to make the wait script executable
# RUN chmod +x /wait-for-it.sh

# Command to run the JAR file old
ENTRYPOINT ["java", "-jar", "app.jar"]
#new startup command
# ENTRYPOINT ["java -jar app.jar"]