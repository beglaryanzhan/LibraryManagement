FROM openjdk:11

# Install necessary libraries
RUN apt-get update && apt-get install -y libxext6 libxrender1 libxtst6

# Set the working directory
WORKDIR /app

# Copy the application files
COPY . /app

# Build the application
RUN javac -d out src/*.java

# Specify the entry point
CMD ["java", "-cp", "out", "Main"]
