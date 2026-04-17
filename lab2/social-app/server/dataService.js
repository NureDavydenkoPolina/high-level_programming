const fs = require("fs");
const path = require("path");

const FILE = path.join(__dirname, "data.json");

function readData() {
  const data = fs.readFileSync(FILE, "utf-8");
  return JSON.parse(data);
}

function writeData(data) {
  fs.writeFileSync(FILE, JSON.stringify(data, null, 2));
}

module.exports = { readData, writeData };