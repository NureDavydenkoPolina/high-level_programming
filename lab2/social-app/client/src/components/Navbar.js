import { NavLink } from "react-router-dom";

function Navbar({ users, selectedUser, setSelectedUser }) {
  return (
    <div>
      <nav className="nav">
        <NavLink to="/">Home</NavLink>
        <NavLink to="/posts">Posts</NavLink>
        <NavLink to="/users">Users</NavLink>
        <NavLink to="/messages">Messages</NavLink>
      </nav>

      <label>
        Current user:{" "}
        <select
          value={selectedUser}
          onChange={(e) => setSelectedUser(Number(e.target.value))}
        >
          {users.map((user) => (
            <option key={user.id} value={user.id}>
              {user.name}
            </option>
          ))}
        </select>
      </label>
    </div>
  );
}

export default Navbar;