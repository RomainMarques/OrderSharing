db.createCollection('park');
db.park.insertMany([
    { name: 'A', location: 'A' },
    { name: 'B', location: 'B' },
    { name: 'C', location: 'C' },
]);


db.createCollection('users');

db.users.insertMany([
    { name: 'John', role: 'customer' },
    { name: 'Alice', role: 'agent', park: 'A' }
]);

db.createCollection('products');
db.products.insertMany([
    { name: 'Fries', price: 100, park: 'A' },
    { name: 'Coca Cola', price: 200, park: 'A'},
    { name: 'Burger', price: 300, park: 'B' },
]);

db.createCollection('orders');
db.orders.insertMany([
    { customer: 'John', park: 'A', product: ['Fries', 'Coca Cola'] },
    { customer: 'John', park: 'B', product: ['Burger'] },
]);

