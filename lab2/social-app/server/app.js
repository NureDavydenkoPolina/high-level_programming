const express = require("express");
const cors = require("cors");
const compression = require("compression");

const app = express();

app.use(cors());
app.use(express.json());
app.use(compression());

app.use("/users", require("./routes/users"));
app.use("/posts", require("./routes/posts"));
app.use("/comments", require("./routes/comments"));
app.use("/friends", require("./routes/friends"));
app.use("/messages", require("./routes/messages"));

app.listen(3000, () => {
  console.log("Server running on http://localhost:3000");
});