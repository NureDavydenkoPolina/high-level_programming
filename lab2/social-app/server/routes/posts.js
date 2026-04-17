const express = require("express");
const router = express.Router();

const data = require("../data/data");

router.get("/", (req, res) => {
  res.json(data.posts);
});

router.post("/", (req, res) => {
  const { userId, title } = req.body;

  const newPost = {
    id: data.postId++,
    userId,
    title
  };

  data.posts.push(newPost);
  res.json(newPost);
});

module.exports = router;