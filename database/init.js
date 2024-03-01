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
db.individualOrders.insertMany([
    { "_id": "FOUFOU", customerName: 'John', productList: ['Fries', 'Coca Cola'], totalPrice: 300 },
    { "_id": "FIFI", customerName: 'John', productList: ['Burger'], totalPrice: 200 },
]);

db.createCollection('sharedOrders');
db.sharedOrders.insertOne(
    {
        totalPrice: 500, toPay: 500, parkName: 'A', alleyNumber: 1,
        individualOrders: [
            {"$ref" : "individualOrders", "$id": "FOUFOU"},
            {"$ref" : "individualOrders", "$id": "FIFI"}
        ],
    }
);

db.createCollection('alleys');
db.alleys.insertMany([
    { number: 1, QRCode: '123' },
    { number: 2, QRCode: '1234' },
    { number: 3, QRCode: '12345' },
    { number: 4, QRCode: '123456' },
    { number: 5, QRCode: '1234567' },
    { number: 6, QRCode: '12321' },
    { number: 7, QRCode: '1232' },
    { number: 8, QRCode: '1231' },
    { number: 9, QRCode: '12322' },
    { number: 10, QRCode: '1233' },
    { number: 11, QRCode: '12333' },
    { number: 12, QRCode: '12344' },
    { number: 13, QRCode: '12355' },
    { number: 14, QRCode: '12366' },
    { number: 15, QRCode: '12377' },
    { number: 16, QRCode: '12388' },
    { number: 17, QRCode: '12399' },
    { number: 18, QRCode: '12300' },
    { number: 19, QRCode: '1230' },
    { number: 20, QRCode: '12' },
]);
