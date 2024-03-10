db.createCollection('parks');
db.parks.insertMany([
    { "_id": "ID_TEST_PARK_A", name: 'A', location: 'A' },
    { "_id": "ID_TEST_PARK_B", name: 'B', location: 'B' },
    { "_id": "ID_TEST_PARK_C", name: 'C', location: 'C' },
]);


db.createCollection('users');
db.users.insertMany([
    { "_id": "ID_TEST_USER_JOHN", name: 'John', role: 'customer', email: 'romain.marques@efrei.net' },
    { "_id": "ID_TEST_USER_ALICE", name: 'Alice', role: 'agent', park: 'A' }
]);

db.createCollection('products');
db.products.insertMany([
    { "_id": "ID_TEST_PRODUCT_FRIES", name: 'Fries', price: 5.5, parkName: 'A' },
    { "_id": "ID_TEST_PRODUCT_COCA", name: 'Coca Cola', price: 2.3, parkName: 'A'},
    { "_id": "ID_TEST_PRODUCT_BURGER", name: 'Burger', price: 6, parkName: 'B' },
]);

db.createCollection('individualOrders');
db.individualOrders.insertMany([
    { "_id": "ID_TEST_ORDER_JOHN", customerName: 'John', productList: ['Fries', 'Coca Cola'], totalPrice: 300 },
    { "_id": "ID_TEST_ORDER_JOHN2", customerName: 'John', productList: ['Burger'], totalPrice: 200 },
]);

db.createCollection('sharedOrders');
db.sharedOrders.insertOne(
    {
        "_id": "ID_TEST_PARK_A",
        totalPrice: 500, toPay: 500, parkName: 'A', alleyNumber: '1',
        individualOrders: [
            {"$ref" : "individualOrders", "$id": "ID_TEST_ORDER_JOHN"},
            {"$ref" : "individualOrders", "$id": "ID_TEST_ORDER_JOHN2"}
        ],
    }
);

db.createCollection('alleys');
db.alleys.insertMany([
    { "_id": "ID_TEST_ALLEY1", number: 1, QRCode: '123' },
    { "_id": "ID_TEST_ALLEY2", number: 2, QRCode: '1234' },
    { "_id": "ID_TEST_ALLEY3", number: 3, QRCode: '12345' },
    { "_id": "ID_TEST_ALLEY4", number: 4, QRCode: '123456' },
    { "_id": "ID_TEST_ALLEY5", number: 5, QRCode: '1234567' },
    { "_id": "ID_TEST_ALLEY6", number: 6, QRCode: '12321' },
    { "_id": "ID_TEST_ALLEY7", number: 7, QRCode: '1232' },
    { "_id": "ID_TEST_ALLEY8", number: 8, QRCode: '1231' },
    { "_id": "ID_TEST_ALLEY9", number: 9, QRCode: '12322' },
    { "_id": "ID_TEST_ALLEY10", number: 10, QRCode: '1233' },
    { "_id": "ID_TEST_ALLEY11", number: 11, QRCode: '12333' },
    { "_id": "ID_TEST_ALLEY12", number: 12, QRCode: '12344' },
    { "_id": "ID_TEST_ALLEY13", number: 13, QRCode: '12355' },
    { "_id": "ID_TEST_ALLEY14", number: 14, QRCode: '12366' },
    { "_id": "ID_TEST_ALLEY15", number: 15, QRCode: '12377' },
    { "_id": "ID_TEST_ALLEY16", number: 16, QRCode: '12388' },
    { "_id": "ID_TEST_ALLEY17", number: 17, QRCode: '12399' },
    { "_id": "ID_TEST_ALLEY18", number: 18, QRCode: '12300' },
    { "_id": "ID_TEST_ALLEY19", number: 19, QRCode: '1230' },
    { "_id": "ID_TEST_ALLEY20", number: 20, QRCode: '12' },
]);

db.createCollection('catalogs');
db.catalogs.insertOne(
    {
        "_id": "ID_CALALOG_A",
        QRCode: '55555',
        parkName: 'A',
        products: [
            {"$ref": "products", "_id": "ID_TEST_PRODUCT_FRIES"},
            {"$ref": "products", "_id": "ID_TEST_PRODUCT_COCA"},
            {"$ref": "products", "_id": "ID_TEST_PRODUCT_BURGER"}
        ]
    }
)