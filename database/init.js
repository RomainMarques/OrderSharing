db.createCollection('parks');
db.parks.insertMany([
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
    { name: 'Fries', price: 100, parkName: 'A' },
    { name: 'Coca Cola', price: 200, parkName: 'A'},
    { name: 'Burger', price: 300, parkName: 'B' },
]);

db.createCollection('individualOrders');
db.orders.insertMany([
    { curstomerName: 'John', parkName: 'A', productList: ['Fries', 'Coca Cola'], totalPrice: 300, toPay: 300 },
    { curstomerName: 'John', parkName: 'B', productList: ['Burger'], totalPrice: 200, toPay: 200 },
]);

