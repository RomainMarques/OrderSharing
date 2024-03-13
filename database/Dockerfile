# Use the official MongoDB image from Docker Hub
FROM mongo:latest

# Set environment variables for MongoDB initialization
ENV MONGO_INITDB_DATABASE=orderdb
ENV MONGO_INITDB_ROOT_USERNAME=root
ENV MONGO_INITDB_ROOT_PASSWORD=root

# Copy the initialization script into the container
COPY init.js /docker-entrypoint-initdb.d/

# Expose the default MongoDB port
EXPOSE 27017

# Start MongoDB when the container launches
CMD ["mongod"]
