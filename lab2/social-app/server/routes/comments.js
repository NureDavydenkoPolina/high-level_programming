const express = require("express");
const router = express.Router();

const data = require("../data/data");

router.get("/", (req, res) => {
  res.json(data.comments);
});

router.post("/", (req, res) => {
  const { postId, text, userId } = req.body;

  const newComment = {
    id: data.commentId++,
    postId,
    text,
    userId
  };

  data.comments.push(newComment);
  res.json(newComment);
});

module.exports = router;