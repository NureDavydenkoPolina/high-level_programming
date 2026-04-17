const express = require("express");
const router = express.Router();

const { readData, writeData, getNextId } = require("../dataService");

router.get("/", (req, res) => {
  const data = readData();
  res.json(data.comments);
});

router.post("/", (req, res) => {
  const data = readData();

  const newComment = {
    id: getNextId(data.comments),
    postId: req.body.postId,
    text: req.body.text,
    userId: req.body.userId
  };

  data.comments.push(newComment);
  writeData(data);

  res.json(newComment);
});

module.exports = router;