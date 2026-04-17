const express = require("express");
const router = express.Router();

const data = require("../data/data");

router.post("/", (req, res) => {
  const { from, to, text } = req.body;

  const newMessage = {
    id: data.messageId++,
    from,
    to,
    text
  };

  data.messages.push(newMessage);
  res.json(newMessage);
});

router.get("/:user1/:user2", (req, res) => {
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