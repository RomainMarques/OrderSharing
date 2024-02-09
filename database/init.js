// Create a 'users' collection
db.createCollection('users');

// Insert sample data into the 'users' collection
db.users.insertMany([
    { name: 'John', role: 'customer' },
    { name: 'Alice', role: 'agent' },
    // Add more sample data as needed
]);
