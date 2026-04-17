function MessagesPage({
  users,
  selectedUser,
  selectedChatUser,
  setSelectedChatUser,
  chatMessages,
  newMessage,
  setNewMessage,
  handleSendMessage,
}) {
  return (
    <div>
      <h1>Messages</h1>

      <h2>Choose chat</h2>
      {users.map((user) => {
        if (user.id === selectedUser) return null;

        return (
          <div key={user.id} style={{ marginBottom: "8px" }}>
            <button onClick={() => setSelectedChatUser(user.id)}>
              Chat with {user.name}
            </button>
          </div>
        );
      })}

      {selectedChatUser && (
        <div className="card">
          <h3>Chat</h3>

          {chatMessages.map((m) => {
            const sender = users.find((u) => u.id === m.from);

            return (
              <div key={m.id}>
                <b>{sender?.name}</b>: {m.text}
              </div>
            );
          })}

          <input
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            placeholder="Type message..."
          />

          <button onClick={handleSendMessage}>Send</button>
        </div>
      )}
    </div>
  );
}

export default MessagesPage;