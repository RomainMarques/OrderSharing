FROM mongo:latest

# Expose MongoDB default port
EXPOSE 27017

# Set the working directory inside the container
WORKDIR /docker-entrypoint-initdb.d/

# Copy the initialization script into the container
COPY init.js /docker-entrypoint-initdb.d/

# Start MongoDB service
CMD ["mongod"]
