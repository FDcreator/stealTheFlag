var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var players = [];

io.on('connection', (socket) => {
	console.log('User Connected: ' + socket.id);
	
	socket.emit('socketID', { id: socket.id } );
	socket.broadcast.emit('User id: ' + socket.id);
	
	socket.broadcast.emit('newPlayer',  {id: socket.id} );
	
	
	socket.on('disconnect', (id) => {
		console.log('User Disconnected: ' + socket.id);
		socket.broadcast.emit('playerDisconnect', { id: socket.id });
		
		for ( let i = 0; i < players.length; i++ ) {
			if ( players[i].id == socket.id ) {
				players.splice(i, 1);
			}
		}
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