const express = require("express");
const router = express.Router();

const { readData, writeData, getNextId } = require("../dataService");

router.get("/", (req, res) => {
  const data = readData();
  res.json(data.posts);
});

router.post("/", (req, res) => {
  const data = readData();

  const newPost = {
    id: getNextId(data.posts),
    userId: req.body.userId,
    title: req.body.title
  };

  data.posts.push(newPost);
  writeData(data);

  res.json(newPost);
});

module.exports = router;