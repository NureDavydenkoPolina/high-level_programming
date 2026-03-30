from datetime import date

def greet():
    name = input("Введіть ваше ім'я: ")
    print(f"Привіт, {name}!")

def findAverage():
    a = int(input("Введіть число 1: "))
    b = int(input("Введіть число 2: "))
    c = int(input("Введіть число 3: "))
    average = (a + b + c) / 3
    print(f"Середнє: {average}")

def findAge():
    yearOfBirth = int(input("Введіть рік народження: "))
    age = date.today().year - yearOfBirth
    print(f"Ваш вік: {age}")

class Book:
    def __init__(self, title, author, year):
        self.title = title
        self.author = author
        self.year = year

greet()
findAverage()
findAge()
myBook = Book(title = "Кобзар", author = "Т.Шевченко", year = 1840)
print(f"Книга: {myBook.title}, автор: {myBook.author}, рік: {myBook.year}")
