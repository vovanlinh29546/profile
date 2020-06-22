var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var mongodb = require('mongodb');

var MongoClient = mongodb.MongoClient;
var url = 'mongodb://localhost:27017/quanlysv';

// can ha thap mongodb xuong 2.33
MongoClient.connect(url, function(err, db) {
    if (err) {
        console.log('Unable to connect to the mongoDB server. Error:', err);
    } else {
        //HURRAY!! We are connected. :)
        console.log('Connection established to', url);
        collection = db.collection('users_login');
        collection_student = db.collection('Sinhvien');
       
    }
});

io.on('connection', function(socket) {
    socket.on('join', function(data) {
        console.log("EventJoin: " + data);
    });

    socket.on('login', function(email, password) {
        console.log("Eventlogin: " + email + " va pass: " + password);

        var cursor = collection.find({ email: email });
        // console.log(cursor);


        cursor.each(function(err, doc) {
            if (err) {
                console.log(err);
                socket.emit('login', false);
            } else {

                if (doc != null) {
                    if (doc.password == password) {
                        console.log(doc.password);
                        socket.emit('login', true);

                    } else {
                        socket.emit('login', false);

                    }

                }
            }
        });



    });
    socket.on('register', function(email,password,name,sdt,ngaysinh ) {
        console.log(name + "register");

        var users_login = { email: email, password: password, name: name,sdt: sdt, ngaysinh: ngaysinh };

        collection.insert(users_login, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('register', false);
            } else {
                console.log('Inserted new user ok');
                socket.emit('register', true);
            }
        });
    });
    socket.on('getStudent', function(msg) {
        console.log("Nhan lenh getStudent: " + msg);

        var cursor = collection_student.find();

        cursor.each(function(err, doc) {
            if (doc != null) {
                var strings = JSON.parse(JSON.stringify(doc));
                console.log(strings);
                socket.emit('getStudent', strings)
            } else if (doc == null) {
                console.log("Ket thuc getStudent");
            }
        });
    });

    socket.on('insertStudent', function(id, name, email) {
        console.log(name + "register");

        var student = { id: id, name: name, email: email };

        collection_student.insert(student, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('insertStudent', false);
            } else {
                console.log('Inserted new user ok');
                socket.emit('insertStudent', true);
            }
        });
    });
    socket.on('deleteStudent', function(_id) {
        console.log(_id + " deleteStudent");
        var student = { _id: new mongodb.ObjectID(_id) };
        collection_student.remove(student, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('deleteStudent', false);
            } else {
                console.log('Delete product ok');
                socket.emit('deleteStudent', true);
            }
        });
    });

    socket.on('updateStudent', function(_id, id, name, email) {
        console.log(name + " updateStudent");
        collection_student.update({ _id: new mongodb.ObjectID(_id) }, { $set: { id: id, name: name, email: email } }, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('updateStudent', false);
            } else {
                socket.emit('updateStudent', true);

            }
        });
    });

});

http.listen(3000, function() {
    console.log('listening on *:3000');
});
// Routes HTTP GET Request /localhost:8080/
app.get('/', function (req, res) {
    res.sendfile('edit_index.html');
});




