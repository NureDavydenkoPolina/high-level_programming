const express = require("express");
const router = express.Router();

const { readData } = require("../dataService");

router.get("/", (req, res) => {
  const data = readData();
  res.json(data.users);
});

module.exports = router;