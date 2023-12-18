# java-project-theater
a relatively big school java project (Everything except method "apiFetch" was written by myself, "apiFetch" is provided by lecturer)
## make sure the java-json.jar file is included in "referenced library" before running the code!
#1 This program relies on some features offered by org.json which means include java-json jar file is necessary to make it executable. It has been included in the "referenced library" and it should work normally. If it is not recognized, you may need to include that jar file manually.(you can find the jar file in "src" folder)
   
#2 This program includes many apis and apis are sometimes not stable. Error only occur once in a blue moon and it's malfunction is due to TMDB website.

#3 Some changes have been made to the project specification in order to better simulate the real world. The changes are as follows.

Change a: The two-dimensional array of Seat objects(allSeats[][]) is no longer placed in Theater class. Instead, two integer values: the number of rows and the number of columns are set in the beginning of the execution and then stored in Theater class. The two-dimentional array is now in Showtime class.

Reason: In real world scenarios, the seats are usually fixed in the theater. Their occupancy may differ in different showtimes. If the two dimentional array is in Theater class, once a Seat's isOccupied boolean value is changed, then it means that seat's occupancy is changed in all showtimes. This is not how real world works. To solve this problem, perhaps the easiest way, is to let the array allSeats[][] to be an attribute of Showtime class, not Theater class. In the beginning of the execution, the numbers of rows and columns of the seats are asked from the user and are stored in Theater class's attributes: numOfRows, numOfColumns. When user creates a Showtime instance, numOfRows and numOfColumns are passed to it and a two-dimentional array is created.(this means each showtime has their own allSeats[][] while the rows and columns are all the same) After the user selects a showtime and buys tickets, only the isOccupied boolean value of the selected Seat object of the allSeats array inside that particular showtime is changed. The seat with the same seat number in other showtimes is not affected at all.

change b: "it also has methods that allow the user to buy tickets and find movies and showtimes by title, time, and date" The latter part of this requirement is not explicitly executed, but merged with other features in this program.
Reason: In this program, user can already search for movies and showtimes by title, time, and date info if they use "3) Display a selected movie info." and "7) Display a selected showtime info of a movie." It would be redundable if a seperate search method is added.

Additional features of this program:

1. As there can be movies with duplicated names in the database, the user is asked to provide both title and release date to specify which exact movie they want to add. If the user is unsure about the release date, he/she can directly hit "enter" to see all revevant titles and release dates based on his or her input. The user still need provide accurate title and release date in order to add a movie. Upper or lower case does not matter, Extra blanks may make the input unrecognizable to the system though.

2. The system does not allow user to add two movies with the same title even though they might be different movies. The system does not allow user to add showtimes whose date and time are the same. To handle movies with same name would make the program even more complex and the situation where movies with same names to be added to a cinema together is farely rare. That's why it is not allowed. Since this theater can only display one movie at a time, duplicated showtime is not allowed.

3. When a movie is deleted, its showtimes are also deleted. This is because when the user has decided to discard a movie, its showtimes are usually also useless.

4.When the user is asked to provide movie title, showtime date and time, the system would always display help information so that the user do not have to remember everything by themselves.

5. Error prevention has been seriously considered. There's not much chance that the program would crush provided that the jar file and the apis work normally.
## demo video
https://youtu.be/XdRmDMElo5Y
