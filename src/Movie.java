


public class Movie {

    private String title;
    private String releaseYear;
    private String director;
    private String duration;
    private String genre;
    private String rating;
    private String numOfRating;
    private String review;
    public Movie(){}
    //setter
    public void setTitle(String title) {
        this.title = title;
    }
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setNumOfRating(String numOfRating) {
        this.numOfRating = numOfRating;
    }
    public void setReview(String review) {
        this.review = review;
    }
    //getter
    public String getTitle() {
        return this.title;
    }
    public String getReleaseYear() {
        return this.releaseYear;
    }
    public String getDirector() {
        return this.director;
    }
    public String getDuration() {
        return this.duration;
    }
    public String getGenre() {
        return this.genre;
    }
    public String getRating() {
        return this.rating;
    }
    public String getNumOfRating() {
        return this.numOfRating;
    }
    public String getReview() {
        return this.review;
    }
}