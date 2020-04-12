var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var players = [];

io.on('connection', (socket) => {
	console.log('User Connected: ' + socket.id);
	
	socket.emit('socketID', { id: socket.id } );
	socket.emit('getPlayers', players);
	socket.broadcast.emit('newPlayer',  {id: socket.id} );
	socket.on('playerMoved', (data) => {
		data.id = socket.id;
		socket.broadcast.emit('playerMoved', data);
		for ( let i = 0; i < players.length; i++ ) {
			if ( players[i].id == socket.id ) {
				players[i].x = data.x;
				players[i].y = data.y;
				players[i].state = data.state;
			}
		}
	});
	
	
	
	socket.on('disconnect', (id) => {
		console.log('User Disconnected: ' + socket.id);
		socket.broadcast.emit('playerDisconnect', { id: socket.id });
		
		for ( let i = 0; i < players.length; i++ ) {
			if ( players[i].id == socket.id ) {
				players.splice(i, 1);
			}
		}
	});
	
	players.push(new Player(socket.id, 50, 50, "STANDING"))
});

server.listen(3000, () => {
	console.log('Listening in port=3000')
});

function Player(id, x, y, state) {
	this.id = id;
	this.x = x;
	this.y = y;
	this.state = state;
}