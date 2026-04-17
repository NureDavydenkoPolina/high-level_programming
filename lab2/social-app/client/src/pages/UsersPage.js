function UsersPage({
  users,
  selectedUser,
  friends,
  handleAddFriend,
  handleRemoveFriend
}) {
  return (
    <div>
      <h1>Users</h1>

      {users.map((user) => {
        if (user.id === selectedUser) return null;

        const isFriend = friends.includes(user.id);

        return (
          <div key={user.id} style={{ marginBottom: "10px" }}>
            {user.name}{" "}

            {isFriend ? (
              <>
                <span>✓ Friend </span>
                <button onClick={() => handleRemoveFriend(user.id)}>
                  Remove
                </button>
              </>
            ) : (
              <button onClick={() => handleAddFriend(user.id)}>
                Add Friend
              </button>
            )}
          </div>
        );
      })}
    </div>
  );
}

export default UsersPage;