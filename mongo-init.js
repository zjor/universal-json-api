let db = connect("localhost:27017/admin");
db.auth("admin", "s3cr3t");

db = db.getSiblingDB('storage');

db.init.insert({message: 'init'});

db.createUser({
    user: "storage",
    pwd: "s3cr3t",
    roles: [{role: "readWrite", db: "storage"}],
});

