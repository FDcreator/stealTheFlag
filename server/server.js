var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var players = [];

io.on('connection', (socket) => {
	console.log('User Connected: ' + socket.id);
	
	socket.on('disconnect', () => {
		console.log('User Disconnected: ' + socket.id);
	});
});

server.listen(3000, () => {
	console.log('Listening in port=3000')
});

function Player(id, x, y, stage) {
	this.id = id;
	this.x = x;
	this.y = y;
	this.stage = stage;
}