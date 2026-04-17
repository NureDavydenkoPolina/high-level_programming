const express = require("express");
const router = express.Router();

const { friendships } = require("../data/data");

router.get("/:userId", (req, res) => {
  const userId = Number(req.params.userId);

  const userFriends = friendships
    .filter(f => f.user1 === userId || f.user2 === userId)
    .map(f => (f.user1 === userId ? f.user2 : f.user1));

  res.json(userFriends);
});

router.post("/", (req, res) => {
  const { user1, user2 } = req.body;

  const exists = friendships.find(
    f =>
      (f.user1 === user1 && f.user2 === user2) ||
      (f.user1 === user2 && f.user2 === user1)
  );

  if (exists) {
    return res.json({ message: "Already friends" });
  }

  const newFriendship = { user1, user2 };
  friendships.push(newFriendship);

  res.json(newFriendship);
});

router.delete("/", (req, res) => {
  const { user1, user2 } = req.body;

  const index = friendships.findIndex(
    f =>
      (f.user1 === user1 && f.user2 === user2) ||
      (f.user1 === user2 && f.user2 === user1)
  );

  if (index === -1) {
    return res.json({ message: "Not friends" });
  }

  friendships.splice(index, 1);

  res.json({ message: "Friend removed" });
});

module.exports = router;