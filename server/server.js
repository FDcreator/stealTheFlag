app = require('express')();
server = require('http').Server(app);
io = require('socket.io')(server);

io.on('connection', (socket) => {
	console.log('User Connected');
	
	socket.on('disconnect', () => {
		console.log('User Disconnected');
	});
});

server.listen(3000, () => {
	console.log('Listening in port=3000')
});