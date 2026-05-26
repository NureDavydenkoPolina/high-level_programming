from database import SessionLocal
from models import User, Post, Comment, Log
from sqlalchemy import or_

def create_user(username, email):
    session = SessionLocal()

    user = User(
        username=username,
        email=email
    )

    session.add(user)
    session.commit()

    print(f"Користувач {username} створений")

    session.close()


def create_post(user_id, title, content):
    session = SessionLocal()

    post = Post(
        user_id=user_id,
        title=title,
        content=content
    )

    session.add(post)
    session.commit()

    add_log(
        "CREATE_POST",
        f"User {user_id} created post '{title}'"
    )

    print(f"Post '{title}' created")

    session.close()


def create_comment(user_id, post_id, content):
    session = SessionLocal()

    comment = Comment(
        user_id=user_id,
        post_id=post_id,
        content=content
    )

    session.add(comment)
    session.commit()

    add_log(
        "CREATE_COMMENT",
        f"User {user_id} commented post {post_id}"
    )

    print("Comment added")

    session.close()

def show_posts():
    session = SessionLocal()

    posts = session.query(Post).all()

    print("\n=== Posts ===")

    for post in posts:
        print(f"\nPost №{post.id}")
        print(f"Author ID: {post.user_id}")
        print(f"Title: {post.title}")
        print(f"Text: {post.content}")

        if post.comments:
            print("Comments:")

            for comment in post.comments:
                print(
                    f"  [{comment.user_id}] {comment.content}"
                )

        else:
            print("No comments")

    session.close()

def search_posts(keyword):
    session = SessionLocal()

    posts = session.query(Post).filter(
        or_(
            Post.title.contains(keyword),
            Post.content.contains(keyword)
        )
    ).all()

    add_log(
        "SEARCH_POST",
        f"Search keyword: {keyword}"
    )

    print(f"\n=== Result of search: '{keyword}' ===")

    if not posts:
        print("Not found")

    for post in posts:
        print(f"\nID: {post.id}")
        print(f"Author: {post.user_id}")
        print(f"Title: {post.title}")
        print(f"Text: {post.content}")

    session.close()

def filter_posts_by_author(user_id):
    session = SessionLocal()

    posts = session.query(Post).filter(
        Post.user_id == user_id
    ).all()

    add_log(
        "FILTER_POSTS",
        f"Filter by author {user_id}"
    )

    print(f"\n=== Posts of author {user_id} ===")

    if not posts:
        print("Posts not found")

    for post in posts:
        print(f"\nID: {post.id}")
        print(f"Title: {post.title}")
        print(f"Text: {post.content}")

    session.close()

def add_log(action, details):
    session = SessionLocal()

    log = Log(
        action=action,
        details=details
    )

    session.add(log)
    session.commit()

    session.close()

def show_logs():
    session = SessionLocal()

    logs = session.query(Log).all()

    print("\n=== Logs ===")

    for log in logs:
        print(f"\nID: {log.id}")
        print(f"Action: {log.action}")
        print(f"Description: {log.details}")
        print(f"Time: {log.created_at}")

    session.close()