
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TreeMap;
public class Theater extends Venue{
    TreeMap<String, Movie> movies = new  TreeMap<>();
    ArrayList<Showtime> showTimes = new  ArrayList<>();
     
    private int numOfRows = 0;
    private int numOfColumns = 0;

    static Scanner sc = new Scanner(System.in); 
    public Theater(){}
    public void addSeatsToTheTheater() {
        int numOfRows = 0;
        int numOfColumns = 0;
        boolean restart = false;
        do {
            System.out.println("Enter the number of rows of seats to be added: ");
            try {
                numOfRows = Integer.parseInt(sc.nextLine());
                checkNegative(numOfRows);
                checkZero(numOfRows);
                restart = false;
            } 
            catch (NumberFormatException e) {
                System.out.println("Error, you should enter valid integer here. Please retry.");
                restart =true;
            }
            catch (NegativeException e) {
                System.out.println(e.getMessage());
                restart =true;
            }
            catch (ZeroException e) {
                System.out.println(e.getMessage());
                restart =true;
            } 
        } while (restart);
        do {
            System.out.println("Enter the number of columns of seats to be added: ");
        try {
            numOfColumns = Integer.parseInt(sc.nextLine());
            checkNegative(numOfColumns);
            checkZero(numOfColumns);
            restart = false;
        } 
        catch (NumberFormatException e) {
            System.out.println("Error, you should enter valid integer here. Please retry.");
            restart =true;
        }  
        catch (NegativeException e) {
            System.out.println(e.getMessage());
            restart =true;
        }
        catch (ZeroException e) {
            System.out.println(e.getMessage());
            restart =true;
        }
        } while (restart);
        
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
       

    }
    public int getRowsNumber() {
        return this.numOfRows; 
    }
    public int getColumnssNumber() {
        return this.numOfColumns; 
    }
    public void addMovie() {
        LinkedHashMap<String, String> infoLinkedHashMap;
        String title;
        String releaseDate;
        System.out.print("Enter the title of the movie: ");
        title = sc.nextLine();
        
        if(movies.containsKey(title.toLowerCase())){
            System.out.println("Sorry, in this system, to store multiple movies with the same title is not allowed.");
            System.out.println("No movie is added.");
        }
        else{
            System.out.println("We need to specify which movie your want to add as there might have duplicated title in the database.");
            System.out.println("Enter the release date of the movie(or, just hit 'enter' on your keyboard to view all relevant movies): ");
            releaseDate = sc.nextLine();
            System.out.println("Fetching data from the database, please wait...");
            infoLinkedHashMap = Main.movieFetch(title, releaseDate);
            if (infoLinkedHashMap.size() !=0 ){
                Movie movie = new Movie();
                movie.setTitle(infoLinkedHashMap.get("title"));
                movie.setReleaseYear(infoLinkedHashMap.get("releaseDate"));
                movie.setReview(infoLinkedHashMap.get("review"));
                movie.setRating(infoLinkedHashMap.get("averageVote"));
                movie.setNumOfRating(infoLinkedHashMap.get("numOfVotes"));
                movie.setGenre(infoLinkedHashMap.get("genre"));
                movie.setDuration(infoLinkedHashMap.get("runtime"));
                movie.setDirector(infoLinkedHashMap.get("director"));
                movies.put(movie.getTitle().toLowerCase(), movie);
                System.out.println("Movie is added successfully.");
            }
        }
    }
    public void removeMovie() {
        String toBeRemoved;
        System.out.println("Warning, if a movie is deleted, its related showtimes would also be deleted.");
        System.out.print("Enter the title of the movie you want to remove: ");
        toBeRemoved = sc.nextLine(); 
        if(movies.containsKey(toBeRemoved.toLowerCase())){
            System.out.printf("%s is removed successfully.\n", movies.get(toBeRemoved.toLowerCase()).getTitle());
            movies.remove(toBeRemoved.toLowerCase());
            for (int i = 0; i < showTimes.size(); i++) {
                if (showTimes.get(i).getMovie().getTitle().toLowerCase().equals(toBeRemoved.toLowerCase())){
                    showTimes.remove(i);
                    i--;
                }
            }
        }
        else  System.out.println("No such movie in our Theater.");
        }
       
    
    public void viewMovie(Movie movie) {
        System.out.println("_______________________________________________________________________________________________________________________________________");
        System.out.printf("Title: %s\n", movie.getTitle());
        System.out.printf("Director: %s\n", movie.getDirector());
        System.out.printf("Release date: %s\n", movie.getReleaseYear());
        System.out.printf("Review: %s\n", movie.getReview());
        System.out.printf("Genre: %s\n", movie.getGenre());
        System.out.printf("Duration: %s\n", movie.getDuration());
        System.out.printf("Rating: %s\n", movie.getRating());
        System.out.printf("Number of rating: %s\n", movie.getNumOfRating());
        System.out.println("_______________________________________________________________________________________________________________________________________");
       
    }
    public void viewSpecifiedMovie() {
        String title;
                        Movie m = null;
                        Boolean none = true;
                        System.out.println("Enter the movie title: ");
                        title = sc.nextLine().toLowerCase();
                        for (String storedTitle : movies.keySet()) {
                            if (title.equals(storedTitle)) {
                                m = movies.get(storedTitle);
                                none = false;
                            }
                        }
                        if (none) {
                            System.out.println("No such movie, please check your spelling and retry.");
                        }
                        else{
                            viewMovie(m);
                        }
        
    }
    public void viewAllMovies() {
        for (String title : movies.keySet()) {
            viewMovie(movies.get(title));
        }
    }
    public void addShowTime() {
        String title;
        String date = "";
        String time = "";
        float price = 0;
        boolean duplicate = false;
        boolean wrongPrice = false;
        System.out.print("Enter the movie title: ");
        title = sc.nextLine();
        if (movies.containsKey(title.toLowerCase())){
            System.out.print("Enter a date (format yyyy-mm-dd is recommended): ");
            date = sc.nextLine();
            System.out.print("Enter a time (format hh:mm is recommended): ");
            time = sc.nextLine();
            for (Showtime st:showTimes ){
                if (st.getDate().equals(date) && st.getTime().equals(time)){
                    duplicate = true;   
                }   
            }
            if (duplicate){
                System.out.println("There has already been a showtime in the time you just entered. Failed to add showtime.");
            }    
            else{
                do {
                    System.out.print("Set a price: ");
                    try {
                        price = Float.parseFloat(sc.nextLine());
                        checkNegative(price);
                        checkZero(price);
                        wrongPrice = false;
                    }
                    catch(NegativeException e){
                        System.out.println(e.getMessage()); 
                        wrongPrice = true;
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("Enter integer please.");
                        wrongPrice = true;
                    }
                    catch (ZeroException e) {
                        System.out.println(e.getMessage());
                        wrongPrice = true;
                    }
                } while (wrongPrice);
                
                
                Showtime st = new Showtime();
                st.setMovie(movies.get(title.toLowerCase()));
                st.setDate(date);
                st.setTime(time);
                st.setPrice(price); 
                showTimes.add(st); 
                st.setSeats( this.numOfRows,  this.numOfColumns);
                System.out.println("Showtime added successfully.");
            }
            
        }
        else System.out.println("No such movie.");
    }
    public void checkNegative(double number) throws NegativeException {
        if (number < 0) {
            throw new NegativeException("Error, the number here should not be negative. Please retry.");
        }
    }
    public void checkZero(double number) throws ZeroException {
        if (number == 0) {
            throw new ZeroException("Error, the number here should not be zero. Please retry.");
        }
    }
    public void viewShowTime(Showtime st) {
        System.out.println("_______________________________________________________________________________________________________________________________________");
        System.out.printf("Title: %s\n", st.getMovie().getTitle());
        System.out.printf("Date: %s\n", st.getDate());
        System.out.printf("Time: %s\n", st.getTime());
        System.out.printf("Duration: %s\n", st.getMovie().getDuration());
        System.out.printf("Price: %.2f euros\n", st.getPrice());
        System.out.println("_______________________________________________________________________________________________________________________________________");
    }
    public void viewSpecifiedShowtime() {
        String title;
                    String date;
                    String time;
                    boolean noShowtime = true;
                    System.out.println("Enter the title of the movie:");
                    title = sc.nextLine();
                    for (Showtime st : showTimes){
                        if (st.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())){
                            System.out.printf("Title: %-20sDate: %-20sTime: %-20s\n",st.getMovie().getTitle(), st.getDate(), st.getTime());
                        }
                    }
                    System.out.println("Enter the date of the movie:");
                    date = sc.nextLine();
                    System.out.println("Enter the time of the movie:");
                    time = sc.nextLine();
                    for (Showtime showtime : showTimes) {
                        if(showtime.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())&&showtime.getDate().equals(date)&&showtime.getTime().equals(time)){
                            viewShowTime(showtime);
                            noShowtime = false;
                        }
                    }
                    if(noShowtime){
                        System.out.println("No such showtime");
                     }
        
    }
    public void viewAllShowTime() {
        for (Showtime st : showTimes) {
            viewShowTime(st);
        }
    }
    public void removeShowTime() {
        //need to check not just title, but also other attributes in showtime as one movie may have different show times
        String title;
        String date;
        String time;
        System.out.println("Enter the title of the movie whose showtime you want to remove");
        title = sc.nextLine();
        for (Showtime st : showTimes){
            if (st.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())){
                System.out.printf("Title: %-20sDate: %-20sTime: %-20s\n",st.getMovie().getTitle(), st.getDate(), st.getTime());
            }
        }
        System.out.println("Enter the date: ");
        date = sc.nextLine();
        System.out.println("Enter the time: ");
        time = sc.nextLine();
        boolean noShowtime = true;
        for (int i = 0; i < showTimes.size(); i++){
            if  (showTimes.get(i).getMovie().getTitle().toLowerCase().equals(title.toLowerCase())&&showTimes.get(i).getDate().equals(date)&&showTimes.get(i).getTime().equals(time)){
                showTimes.remove(i);
                noShowtime =false;
                System.out.println("Showtime has been removed successfully.");
                i--;
            }
        }
        if (noShowtime){
            System.out.println("No related showtime.");
        }
   }
   public void buyTickets() {
    String title;
    String date;
    String time;
    int numOfTickets = 0;
    int row = 0;
    int column = 0;
    boolean noShowtime = true;
    boolean wrongRow = false;
    boolean wrongCol = false;
    boolean wrongTicketNum = false;
    System.out.println("All available movies:");
    viewAllShowTime();
    System.out.println("Enter the title of the movie:");
    title = sc.nextLine();
    for (Showtime st : showTimes){
        if (st.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())){
            System.out.printf("Title: %-20sDate: %-20sTime: %-20s\n",st.getMovie().getTitle(), st.getDate(), st.getTime());
        }
    }
    System.out.println("Enter the date of the movie:");
    date = sc.nextLine();
    System.out.println("Enter the time of the movie:");
    time = sc.nextLine();
    for (Showtime showtime : showTimes) {
        if(showtime.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())&&showtime.getDate().equals(date)&&showtime.getTime().equals(time)){
            noShowtime = false;
            
            do{ System.out.println("Enter the number of tickets: ");
                try {
                    numOfTickets = Integer.parseInt(sc.nextLine());
                    checkNegative(numOfTickets);
                    checkZero(numOfTickets);
                    if (numOfTickets > numOfRows*numOfColumns){
                        System.out.println("Sorry, no enough seats.");
                        wrongTicketNum = true;
                    }
                    else{wrongTicketNum = false;}
                }    
                catch (NumberFormatException e) {
                    System.out.println("Please enter valid integer here.");
                    wrongTicketNum = true;
                }
                catch (NegativeException e) {
                    System.out.println(e.getMessage());
                    wrongTicketNum = true;
                }
                catch (ZeroException e) {
                    System.out.println(e.getMessage());
                    wrongTicketNum = true;
                }
            }
            while(wrongTicketNum);
                for (int i = 0; i < numOfTickets; i++) {
                    
                    System.out.printf("Enter the position of the %d ticket.\n", i+1);  
                    do {
                        System.out.printf("Enter the row number(0-%d)\n",numOfRows-1);
                        try {
                            row = Integer.parseInt(sc.nextLine());
                            checkNegative(row);
                           
                            if (row > numOfRows-1){
                                System.out.printf("Only %d rows, try again\n", numOfRows);
                                wrongRow = true;
                            }
                            else{ wrongRow = false;}
                        } 
                        catch (NumberFormatException e) {
                            System.out.println("Please enter valid integer here. try again");
                            wrongRow = true;
                        }
                        catch (NegativeException e) {
                            System.out.println(e.getMessage()+" Try again");
                            wrongRow = true;
                        }
                    } while (wrongRow);
                    do {
                        System.out.printf("Enter the column number(0-%d)\n",numOfColumns-1);
                        try {
                            column = Integer.parseInt(sc.nextLine());
                            checkNegative(column);
                            if (column > numOfColumns-1){
                                System.out.printf("Only %d columns, try again\n", numOfColumns);
                                wrongCol = true;
                            }
                            else{wrongCol = false;}
                        } 
                        catch (NumberFormatException e) {
                            System.out.println("Please enter  valid integer here. try again");
                            wrongCol = true;
                        }
                        catch (NegativeException e) {
                            System.out.println(e.getMessage()+" Try again");
                            wrongCol = true;
                        }
                        

                    } while (wrongCol);
                    if (showtime.getSeats()[row][column].getIsOccupied()){
                        System.out.println("This seat is occupied, please retry");
                        i--;
                    }
                    else {
                        showtime.getSeats()[row][column].setIsOccupied(true);
                        System.out.println("The seat is reserved successfully.");
                    }
                }

                System.out.printf("Total price: %.2f euros\n", showtime.getPrice()*numOfTickets);
        }
    }
        
        if(noShowtime){System.out.println("No such showtime");}
}

    

    
  
   public void viewSeat(Showtime showTime) {
        boolean allFree = true;
        Seat seats[][] = showTime.getSeats();
        System.out.println("_______________________________________________________________________________________________________________________________________");
        System.out.printf("Now displaying seats info for %s %s %s.\n", showTime.getMovie().getTitle(), showTime.getDate(), showTime.getTime());
        System.out.printf("Total seats: %d;\n", numOfRows*numOfColumns);
        System.out.printf("Total rows: %d;\n", numOfRows);
        System.out.printf("Total columns: %d;\n", numOfColumns);
        for (int i = 0; i < this.numOfRows; i++) {
            for (int j = 0; j < this.numOfColumns; j++) {
                if(seats[i][j].getIsOccupied()){
                    System.out.printf("Seat in row %d column %d is occupied.\n", i, j);
                    allFree = false;
                }
            }
        }
        if (allFree) {
            System.out.println("All the seats in this showtime is available.");
        }
        System.out.println("_______________________________________________________________________________________________________________________________________");
   }
   public void viewSpecifiedSeats(){
        String title;
        String date;
        String time;
        boolean noShowtime = true;
        System.out.println("Enter the title of the movie:");
        title = sc.nextLine();
        for (Showtime st : showTimes){
            if (st.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())){
                System.out.printf("Title: %-20sDate: %-20sTime: %-20s\n",st.getMovie().getTitle(), st.getDate(), st.getTime());
                }
            }
        System.out.println("Enter the date of the movie:");
        date = sc.nextLine();
        System.out.println("Enter the time of the movie:");
        time = sc.nextLine();
        for (Showtime showtime : showTimes) {
            if(showtime.getMovie().getTitle().toLowerCase().equals(title.toLowerCase())&&showtime.getDate().equals(date)&&showtime.getTime().equals(time)){
                viewSeat(showtime);
                noShowtime = false;
                }
            }
        if(noShowtime){
            System.out.println("No such showtime");
            }
   }
   public void viewSeatInAllShowtimes() {
        for (Showtime showtime : showTimes) {
            viewSeat(showtime);
        }
   }
   

    
}

        

