import { useState, useEffect } from "react";
import "./App.css";

import Login from "./components/Login";
import Rooms from "./components/Rooms";
import Chat from "./components/Chat";

function App() {
  const [username, setUsername] = useState("");
  const [currentUser, setCurrentUser] = useState(() => {
    return localStorage.getItem("currentUser");
  });

  const [rooms, setRooms] = useState(() => {
    return JSON.parse(localStorage.getItem("rooms")) || [];
  });

  const [currentRoom, setCurrentRoom] = useState(null);
  const [newRoom, setNewRoom] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    localStorage.setItem("rooms", JSON.stringify(rooms));
  }, [rooms]);

  useEffect(() => {
    if (currentRoom) {
      localStorage.setItem("currentRoom", currentRoom.name);
    }
  }, [currentRoom]);

  useEffect(() => {
    const savedRoom = localStorage.getItem("currentRoom");
    if (savedRoom && rooms.length > 0) {
      const found = rooms.find(r => r.name === savedRoom);
      if (found) setCurrentRoom(found);
    }
  }, [rooms]);

  const handleRegister = () => {
    let users = JSON.parse(localStorage.getItem("users")) || [];

    if (!username) return;

    if (users.includes(username)) {
      alert("User already exists!");
      return;
    }

    users.push(username);
    localStorage.setItem("users", JSON.stringify(users));

    localStorage.setItem("currentUser", username);
    setCurrentUser(username);
  };

  const handleLogin = () => {
    let users = JSON.parse(localStorage.getItem("users")) || [];

    if (!username) return;

    if (!users.includes(username)) {
      alert("User not found!");
      return;
    }

    localStorage.setItem("currentUser", username);
    setCurrentUser(username);
  };

  const handleLogout = () => {
    localStorage.removeItem("currentUser");
    localStorage.removeItem("currentRoom");
    setCurrentUser(null);
    setCurrentRoom(null);
  };

  const createRoom = () => {
    if (!newRoom) return;

    if (rooms.some(r => r.name === newRoom)) {
      alert("Room already exists!");
      return;
    }

    setRooms([...rooms, { name: newRoom, messages: [] }]);
    setNewRoom("");
  };

  const sendMessage = () => {
    if (!message || !currentRoom) return;

    const updatedRooms = rooms.map(room => {
      if (room.name === currentRoom.name) {
        return {
          ...room,
          messages: [
            ...room.messages,
            { user: currentUser, text: message }
          ]
        };
      }
      return room;
    });

    setRooms(updatedRooms);
    setMessage("");
  };

  if (!currentUser) {
    return (
      <Login
        username={username}
        setUsername={setUsername}
        onLogin={handleLogin}
        onRegister={handleRegister}
      />
    );
  }

  return (
    <main className="chat">
      <Rooms
        rooms={rooms}
        setCurrentRoom={setCurrentRoom}
        newRoom={newRoom}
        setNewRoom={setNewRoom}
        createRoom={createRoom}
        logout={handleLogout}
      />

      <Chat
        currentRoom={currentRoom}
        message={message}
        setMessage={setMessage}
        sendMessage={sendMessage}
      />
    </main>
  );
}

export default App;