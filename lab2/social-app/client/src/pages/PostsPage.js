import { useState } from "react";

function PostsPage({
  users,
  posts,
  comments,
  selectedUser,
  newPost,
  setNewPost,
  newComments,
  setNewComments,
  handleAddPost,
  handleAddComment,
}) {
  const [search, setSearch] = useState("");

  return (
    <div>
      <h2>Search</h2>

      <input
        type="text"
        placeholder="Search posts or users..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      <h1>Posts</h1>

      <h2>Create Post</h2>
      <input
        type="text"
        placeholder="Post text"
        value={newPost}
        onChange={(e) => setNewPost(e.target.value)}
      />
      <button onClick={handleAddPost}>Add Post</button>

      {posts
        .filter(post => {
          const user = users.find(u => u.id === post.userId);
          const text = search.toLowerCase();

          return (
            post.title.toLowerCase().includes(text) ||
            user?.name.toLowerCase().includes(text)
          );
        })
        .map(post => {
          const user = users.find((u) => u.id === post.userId);
          const postComments = comments.filter((c) => c.postId === post.id);

          return (
            <div key={post.id} className="card">
              <b>{user?.name}</b>: {post.title}

              <div style={{ marginTop: "10px" }}>
                {postComments.map((c) => {
                  const commentUser = users.find((u) => u.id === c.userId);

                  return (
                    <div key={c.id}>
                      « <b>{commentUser?.name}</b>: {c.text} »
                    </div>
                  );
                })}
              </div>

              <input
                placeholder="Write comment"
                value={newComments[post.id] || ""}
                onChange={(e) =>
                  setNewComments({
                    ...newComments,
                    [post.id]: e.target.value,
                  })
                }
              />

              <button onClick={() => handleAddComment(post.id)}>
                Comment
              </button>
            </div>
          );
        })}
    </div>
  );
}

export default PostsPage;