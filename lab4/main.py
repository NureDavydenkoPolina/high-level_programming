from database import Base, engine
from crud import (
    create_user,
    create_post,
    create_comment,
    show_posts,
    search_posts,
    filter_posts_by_author,
    show_logs
)


def create_tables():
    Base.metadata.create_all(bind=engine)


if __name__ == "__main__":
    create_tables()

    create_user(
        "Polina",
        "polina@gmail.com"
    )

    create_user(
        "Sae",
        "sae@gmail.com"
    )

    create_post(
        1,
        "Learning Python",
        "Today I started learning SQLAlchemy and database management."
    )

    create_post(
        2,
        "Game Development",
        "I am currently working on a detective visual novel project."
    )

    create_post(
        1,
        "University Life",
        "This semester includes programming, databases, and software engineering."
    )

    create_comment(
        2,
        1,
        "Good luck with SQLAlchemy!"
    )

    create_comment(
        1,
        2,
        "That sounds like an interesting project."
    )

    create_comment(
        2,
        3,
        "Database courses are very useful."
    )

    show_posts()

    search_posts("Python")
    search_posts("database")

    filter_posts_by_author(1)
    filter_posts_by_author(2)

    show_logs()