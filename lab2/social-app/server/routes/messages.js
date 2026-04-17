const express = require("express");
const router = express.Router();

const { readData, writeData, getNextId } = require("../dataService");

router.post("/", (req, res) => {
  const data = readData();

  const newMessage = {
    id: getNextId(data.messages),
    from: req.body.from,
    to: req.body.to,
    text: req.body.text
  };

  data.messages.push(newMessage);
  writeData(data);

  res.json(newMessage);
});

router.get("/:user1/:user2", (req, res) => {
  const data = readData();
  const user1 = Number(req.params.user1);
  const user2 = Number(req.params.user2);

  const chat = data.messages.filter(
    m =>
      (m.from === user1 && m.to === user2) ||
      (m.from === user2 && m.to === user1)
  );

  res.json(chat);
});

module.exports = router;