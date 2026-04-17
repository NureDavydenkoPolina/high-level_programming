const express = require("express");
const cors = require("cors");
const compression = require("compression");

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());
app.use(compression());

const usersRoutes = require("./routes/users");
const postsRoutes = require("./routes/posts");
const commentsRoutes = require("./routes/comments");
const friendsRoutes = require("./routes/friends");
const messagesRoutes = require("./routes/messages");

app.use("/users", usersRoutes);
app.use("/posts", postsRoutes);
app.use("/comments", commentsRoutes);
app.use("/friends", friendsRoutes);
app.use("/messages", messagesRoutes);

app.listen(PORT, () => {
  console.log(`Server started on http://localhost:${PORT}`);
});