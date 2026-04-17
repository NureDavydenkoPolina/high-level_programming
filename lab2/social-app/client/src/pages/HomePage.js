function HomePage({ users, selectedUser }) {
  const currentUser = users.find((user) => user.id === selectedUser);

  return (
    <div>
      <h1>Social App</h1>
      <p>Current user: {currentUser?.name || "Loading..."}</p>
    </div>
  );
}

export default HomePage;