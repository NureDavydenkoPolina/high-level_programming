import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import "./App.css";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import PostsPage from "./pages/PostsPage";
import UsersPage from "./pages/UsersPage";
import MessagesPage from "./pages/MessagesPage";

function App() {
  const [users, setUsers] = useState([]);
  const [posts, setPosts] = useState([]);
  const [comments, setComments] = useState([]);
  const [friends, setFriends] = useState([]);

  const [selectedUser, setSelectedUser] = useState(1);
  const [selectedChatUser, setSelectedChatUser] = useState(null);
  const [chatMessages, setChatMessages] = useState([]);

  const [newPost, setNewPost] = useState("");
  const [newComments, setNewComments] = useState({});
  const [newMessage, setNewMessage] = useState("");

  useEffect(() => {
    fetch("http://localhost:3000/users")
      .then((res) => res.json())
      .then(setUsers);

    fetch("http://localhost:3000/posts")
      .then((res) => res.json())
      .then(setPosts);

    fetch("http://localhost:3000/comments")
      .then((res) => res.json())
      .then(setComments);
  }, []);

  useEffect(() => {
    fetch(`http://localhost:3000/friends/${selectedUser}`)
      .then((res) => res.json())
      .then(setFriends);
  }, [selectedUser]);

  useEffect(() => {
    if (!selectedChatUser) {
      setChatMessages([]);
      return;
    }

    fetch(`http://localhost:3000/messages/${selectedUser}/${selectedChatUser}`)
      .then((res) => res.json())
      .then(setChatMessages);
  }, [selectedUser, selectedChatUser]);

  const handleAddPost = () => {
    if (!newPost.trim()) return;

    fetch("http://localhost:3000/posts", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: selectedUser,
        title: newPost,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setPosts((prev) => [...prev, data]);
        setNewPost("");
      });
  };

  const handleAddComment = (postId) => {
    const text = newComments[postId];
    if (!text?.trim()) return;

    fetch("http://localhost:3000/comments", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        postId,
        text,
        userId: selectedUser,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setComments((prev) => [...prev, data]);
        setNewComments((prev) => ({ ...prev, [postId]: "" }));
      });
  };

  const handleAddFriend = (friendId) => {
    fetch("http://localhost:3000/friends", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        user1: selectedUser,
        user2: friendId,
      }),
    })
      .then((res) => res.json())
      .then(() => {
        fetch(`http://localhost:3000/friends/${selectedUser}`)
          .then((res) => res.json())
          .then(setFriends);
      });
  };

  const handleSendMessage = () => {
    if (!newMessage.trim() || !selectedChatUser) return;

    fetch("http://localhost:3000/messages", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        from: selectedUser,
        to: selectedChatUser,
        text: newMessage,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        setChatMessages((prev) => [...prev, data]);
        setNewMessage("");
      });
  };

  const handleRemoveFriend = (friendId) => {
    fetch("http://localhost:3000/friends", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        user1: selectedUser,
        user2: friendId
      })
    })
      .then(res => res.json())
      .then(() => {
        fetch(`http://localhost:3000/friends/${selectedUser}`)
          .then(res => res.json())
          .then(setFriends);
      });
  };  

  return (
    <BrowserRouter>
      <div className="container">
        <Navbar
          users={users}
          selectedUser={selectedUser}
          setSelectedUser={setSelectedUser}
        />

        <Routes>
          <Route path="/" element={<HomePage users={users} selectedUser={selectedUser} />} />

          <Route
            path="/posts"
            element={
              <PostsPage
                users={users}
                posts={posts}
                comments={comments}
                selectedUser={selectedUser}
                newPost={newPost}
                setNewPost={setNewPost}
                newComments={newComments}
                setNewComments={setNewComments}
                handleAddPost={handleAddPost}
                handleAddComment={handleAddComment}
              />
            }
          />

          <Route
            path="/users"
            element={
              <UsersPage
                users={users}
                selectedUser={selectedUser}
                friends={friends}
                handleAddFriend={handleAddFriend}
                handleRemoveFriend={handleRemoveFriend}
              />
            }
          />

          <Route
            path="/messages"
            element={
              <MessagesPage
                users={users}
                selectedUser={selectedUser}
                selectedChatUser={selectedChatUser}
                setSelectedChatUser={setSelectedChatUser}
                chatMessages={chatMessages}
                newMessage={newMessage}
                setNewMessage={setNewMessage}
                handleSendMessage={handleSendMessage}
              />
            }
          />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;