var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var mongodb = require('mongodb');

var MongoClient = mongodb.MongoClient;
var url = 'mongodb://localhost:27017/quanlydt';

// can ha thap mongodb xuong 2.33
MongoClient.connect(url, function(err, db) {
    if (err) {
        console.log('Unable to connect to the mongoDB server. Error:', err);
    } else {
        //HURRAY!! We are connected. :)
        console.log('Connection established to', url);
        collection_dt = db.collection('dt');
       
    }
});

io.on('connection', function(socket) {
    socket.on('join', function(data) {
        console.log("EventJoin: " + data);
    });

    
    socket.on('getdt', function(msg) {
        console.log("Nhan lenh getdt: " + msg);

        var cursor = collection_dt.find();

        cursor.each(function(err, doc) {
            if (doc != null) {
                var strings = JSON.parse(JSON.stringify(doc));
                console.log(strings);
                socket.emit('getdt', strings)
            } else if (doc == null) {
                console.log("Ket thuc getdt");
            }
        });
    });

   

});

http.listen(3000, function() {
    console.log('listening on *:3000');
});
// Routes HTTP GET Request /localhost:8080/





