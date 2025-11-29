FROM sbtscala/scala-sbt:eclipse-temurin-jammy-17.0.9_9_1.9.7_2.12.18
WORKDIR /app

# Copy build files
COPY build.sbt .
COPY project ./project

# Download dependencies
RUN sbt update

# Copy source code
COPY src ./src

# Build the project
RUN sbt clean assembly

