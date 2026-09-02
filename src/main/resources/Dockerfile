# 2. Production Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
# Force popular diagram tools to write their cache and config to the writable path
ENV PLANTUML_LIMIT_SIZE=8192
ENV GRAPHVIZ_DOT=/usr/bin/dot
ENV XDG_CACHE_HOME=/tmp/.cache
# Copy the AWS Lambda Adapter binary extension
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.8.4 /lambda-adapter /opt/extensions/lambda-adapter

# Copy your compiled fat JAR directly from the builder stage
COPY embabel-demo-agent-1.0.0-SNAPSHOT.jar app.jar

# ❌ REMOVED: No more unzip or jar commands needed!

# Configure the runtime environment ports
ENV PORT=8080
EXPOSE 8080

# 🚀 FIXED: Execute the fat JAR file directly using standard java jar execution
CMD ["java", "-Djava.io.tmpdir=/tmp", "-jar", "app.jar"]
