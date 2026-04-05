function Rooms({ rooms, setCurrentRoom, newRoom, setNewRoom, createRoom, logout }) {
  return (
    <nav aria-label="Chat rooms">
      <h3>Rooms</h3>

      {rooms.map(room => (
        <div key={room.name}>
          <button onClick={() => setCurrentRoom(room)}>
            {room.name}
          </button>
        </div>
      ))}

      <label>New room:</label>
      <input
        type="text"
        value={newRoom}
        onChange={(e) => setNewRoom(e.target.value)}
      />
      <button onClick={createRoom}>Add Room</button>

      <button onClick={logout}>Logout</button>
    </nav>
  );
}

export default Rooms;