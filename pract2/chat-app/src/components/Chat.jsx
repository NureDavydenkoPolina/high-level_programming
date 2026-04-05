function Chat({ currentRoom, message, setMessage, sendMessage }) {
  return (
    <section>
      <h3>{currentRoom ? currentRoom.name : "Select room"}</h3>

      <div role="log" aria-live="polite">
        {currentRoom &&
          currentRoom.messages.map((msg, index) => (
            <p key={index}>
              <b>{msg.user}:</b> {msg.text}
            </p>
          ))}
      </div>

      {currentRoom && (
        <>
          <label>Message:</label>

          <div className="input-row">
            <input
              type="text"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") sendMessage();
              }}
            />
            <button onClick={sendMessage}>Send</button>
          </div>
        </>
      )}
    </section>
  );
}

export default Chat;