var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var players = [];

io.on('connection', (socket) => {
	console.log('User Connected: ' + socket.id);
	
	// emite o id para si
	socket.emit('socketID', { id: socket.id } );
	// emite os players para si
	socket.emit('getPlayers', players); // emite para todos, ele pode pegar
	// emite o id para outros players
	socket.broadcast.emit('newPlayer',  {id: socket.id} ); // emite para outros sockets, ou seja, o socket nao pode pegar
	
	socket.on('playerMoved', (data) => {
		data.id = socket.id;
		socket.broadcast.emit('playerMoved', data);
		for ( let i = 0; i < players.length; i++ ) {
			if ( players[i].id == socket.id ) {
				players[i].x = data.x;
				players[i].y = data.y;
				players[i].state = data.state;
				players[i].direction = data.direction;
				
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
	
	// pega as informacoes do player ao ser criado
	socket.on('myPlayer', (player) => {
		players.push(new Player(socket.id, player.x, player.y, player.state, player.direction));
	})
});

server.listen(3000, () => {
	console.log('Listening in port=3000')
});

function Player(id, x, y, state, direction) {
	this.id = id;
	this.x = x;
	this.y = y;
	this.state = state;
	this.direction = direction;
}