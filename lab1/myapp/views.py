from django.shortcuts import render
from .models import Article
from django.contrib.auth.models import User
from django.db.models import Q

def author_articles(request, author_id):
    author = User.objects.get(id=author_id)
    articles = Article.objects.filter(author=author)

    return render(request, 'myapp/author_articles.html', {
        'author': author,
        'articles': articles
    })

def article_list(request):
    articles = Article.objects.all()
    return render(request, 'myapp/article_list.html', {
        'articles': articles
    })


def article_detail(request, article_id):
    article = Article.objects.get(id=article_id)
    return render(request, 'myapp/article_detail.html', {
        'article': article
    })

def search_articles(request):
    query = request.GET.get('q')
    articles = []

    if query:
        articles = Article.objects.filter(
            Q(title__icontains=query) | Q(text__icontains=query)
        )

    return render(request, 'myapp/search.html', {
        'articles': articles,
        'query': query
    })