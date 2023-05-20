# LibraryManagement

Final Project for OOP class

#Build the project

javac -d out src/\*.java

java -cp out Main

#About the project 

Library Management system provides the ability to add books to the library it uses fundamental concepts of OOP such as encapsulation, polymorphism, and inheritance. It also provides GUI using Java SWING and elements such as tables, prompts, file menu, and etc. Moreover, it is also implemented File I/O by which you can export or import your personal library using csv format. 

#TO DO
Implement db such as mongodb to preserve the state of library.
Add user functionality to the SWING.
Containerize project building and running using docker compose.


#Hierarchy 

+-----------------+         +-------+-------+  
|     Library     |         |     Book      |
+-----------------+	    +-------+-------+
| - books: Book[] |	    | - title       |
+-----------------+ <-------| - author      |
| + addBook()     |         | - genre       |
| + removeBook()  |         | - available   |
| + displayBooks()|         +---------------+
+-----------------+	    | + getTitle()  |
			    | + getAuthor() |
			    | + getGenre()  |
			    |+ isAvailable()|
			    +---------------+

		      	            ^
			            |
			            |
			    +-------+-------+
			    |     User      |
			    +-------+-------+
			    | - name        |
			    | - address     |
			    | - borrowingHistory: Book[] |
			    +---------------+
			    | + getName()   |
			    | + getAddress()|
			    | + borrowBook()|
			    | + returnBook()|
			    +---------------+
				
