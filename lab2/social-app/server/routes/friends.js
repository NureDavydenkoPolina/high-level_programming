const express = require("express");
const router = express.Router();

const { readData, writeData } = require("../dataService");

router.get("/:userId", (req, res) => {
  const data = readData();
  const userId = Number(req.params.userId);

  const userFriends = data.friendships
    .filter(f => f.user1 === userId || f.user2 === userId)
    .map(f => (f.user1 === userId ? f.user2 : f.user1));

  res.json(userFriends);
});

router.post("/", (req, res) => {
  const data = readData();
  const { user1, user2 } = req.body;

  const exists = data.friendships.find(
    f =>
      (f.user1 === user1 && f.user2 === user2) ||
      (f.user1 === user2 && f.user2 === user1)
  );

  if (!exists) {
    data.friendships.push({ user1, user2 });
    writeData(data);
  }

  res.json({ message: "OK" });
});

router.delete("/", (req, res) => {
  const data = readData();
  const { user1, user2 } = req.body;

  data.friendships = data.friendships.filter(
    f =>
      !(
        (f.user1 === user1 && f.user2 === user2) ||
        (f.user1 === user2 && f.user2 === user1)
      )
  );

  writeData(data);

  res.json({ message: "Removed" });
});

module.exports = router;