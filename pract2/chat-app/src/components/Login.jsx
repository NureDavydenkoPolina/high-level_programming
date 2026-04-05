function Login({ username, setUsername, onLogin, onRegister }) {
  return (
    <main className="login">
      <h1>Chat App</h1>

      <label htmlFor="username">Enter your name:</label>
      <input
        id="username"
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <button onClick={onLogin}>Login</button>
      <button onClick={onRegister}>Register</button>
    </main>
  );
}

export default Login;