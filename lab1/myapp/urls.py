from django.urls import path
from . import views

urlpatterns = [
    path('author/<int:author_id>/', views.author_articles),

    path('articles/', views.article_list),
    path('article/<int:article_id>/', views.article_detail),

    path('search/', views.search_articles),
]