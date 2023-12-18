

public class Showtime {
    private Movie movie;
    private String date;
    private String time;
    private double price;
    private Seat[][] allSeats;
    public Showtime(){}
     //setters
    public void setSeats(int numOfRows, int numOfColumns) {
        allSeats = new Seat[numOfRows][numOfColumns];
        for (int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfColumns; j++){
                Seat st = new Seat();
                st.setRowNum(i);
                st.setColumnNum(j);
                allSeats[i][j] = st;
            }
        }
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    //getters
    public Movie getMovie() {
        return this.movie;
    }
    public String getDate() {
        return this.date;
    }
    public String getTime() {
        return this.time;
    }
    public double  getPrice() {
        return this.price;
    }
    public Seat[][] getSeats() {
        return allSeats;
    }
    
}
