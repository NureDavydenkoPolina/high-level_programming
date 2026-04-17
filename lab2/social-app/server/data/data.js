let users = [
  { id: 1, name: "Polina" },
  { id: 2, name: "Alex" }
];

let posts = [
  { id: 1, userId: 1, title: "My first post" },
  { id: 2, userId: 2, title: "Hello world" }
];

let postId = 3;

let comments = [
  { id: 1, postId: 1, text: "Nice post!", userId: 1 }
];

let commentId = 2;

let friendships = [];

let messages = [];
let messageId = 1;

module.exports = {
  users,
  posts,
  postId,
  comments,
  commentId,
  friendships,
  messages,
  messageId
};