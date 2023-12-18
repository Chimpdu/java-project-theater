
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedHashMap;
/*
* Some features of org.json jar file is used in this program
* To run this program successfully, please make sure that
* You have added the the jar file placed in the "src" folder to the "Referenced Libraries" if your are using VScode (Other IDEs work pretty similar but may have different precedures to add jar files).
* I have included it when I submitted this project so it should be fine. But just in case, please add it manually if there displays error message.
* Please open the whole project in the IDE, not just the "src" folder.
*/
import org.json.*;
public class Main{
    
    public static void main(String[] args) {
        int selection = 0;
        Scanner sc = new Scanner(System.in);
        Theater t = new Theater();
        
        System.out.println("Welcome to Java Kino, the best theater in the universe!");
        System.out.println("Please first decide on the number of seats in our theater!");
        t.addSeatsToTheTheater();
        while (true) {
            System.out.println("");
            System.out.println("We offer following services.");
            System.out.println("1) Add a movie to our theater.");
            System.out.println("2) Remove a movie from our theater.");
            System.out.println("3) Display a selected movie info.");
            System.out.println("4) Display all movies' info.");
            System.out.println("5) Add a showtime of a movie to our theater.");
            System.out.println("6) Remove a showtime of a movie from our theater.");
            System.out.println("7) Display a selected showtime info of a movie.");
            System.out.println("8) Display all showtimes' info.");
            System.out.println("9) Purchase tickets.");
            System.out.println("10) View seats info for a selected showtime.");
            System.out.println("11) View seats info for all showtimes.");
            System.out.println("0) Exit.");
            System.out.println("Please enter your selection.");
            try {
                selection = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid selection. Please retry.");
                continue;
            }
            switch (selection) {
                case 0:
                    sc.close();
                    System.exit(0);
                    break;
                case 1:
                    t.addMovie();
                    break;
                case 2: 
                    t.removeMovie();
                    break;
                case 3:
                    
                    if (t.movies.size()==0) {
                        System.out.println("No movie is available, please add movies first.");
                    }
                    else{
                        t.viewSpecifiedMovie();
                    }
                    break;
                case 4:
                if (t.movies.size()==0){
                    System.out.println("No movie is available, please add movies first.");
                }    
                else{
                    t.viewAllMovies();
                }
                
                    break;
                case 5:
                    t.addShowTime();
                    break;
                case 6:

                    t.removeShowTime();
                    break;
                case 7:
                    if (t.showTimes.size()==0) {
                        System.out.println("No showtime is available, please  add showtimes first.");
                    } else {
                        t.viewSpecifiedShowtime();
                    }
                    
                    break;
                case 8:
                if (t.showTimes.size()==0) {
                    System.out.println("No showtime is available, please add showtimes first.");
                }
                else {
                    t.viewAllShowTime();
                }    
                
                    break;
                case 9:
                if (t.showTimes.size()==0) {
                    System.out.println("No showtime is available, please add showtimes first.");
                }
                else{
                    t.buyTickets();
                }
                break;
                case 10:
                if (t.showTimes.size()==0) {
                    System.out.println("No showtime is available, please add showtimes first.");
                }
                else{
                    t.viewSpecifiedSeats();
                }
                
                break;
                case 11:
                if (t.showTimes.size()==0) {
                    System.out.println("No showtime is available, please add showtimes first.");
                } else {
                    t.viewSeatInAllShowtimes();
                }
                
                break;
                default:
                    System.out.println("Please enter a valid selection. Please retry.");
                    break;
            }
        }
     
    }


    public static String apiFectch(String urlString){
        String infos="";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            //Check if connect is made
            int responseCode = conn.getResponseCode();
            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder(1024);
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();
             infos = informationString.toString();
            
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return infos;
    }



    public static LinkedHashMap<String, String> movieFetch(String title, String releaseDate){
        LinkedHashMap<String, String> movieInfo = new LinkedHashMap<>();
        //general_search: get the id of all related searches
        String converted_title = title.replace(' ', '+');
        String urlString =  "https://api.themoviedb.org/3/search/movie?api_key=a0fa1e3a499f3bf73501f659b480e676&query=" + converted_title;
        String informationString = apiFectch(urlString);
        
        try {
            JSONObject myresponse = new JSONObject(informationString);
            //System.out.println(myresponse); //experiments
            //System.out.println(myresponse.getJSONArray("results").length()); //experiments
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < myresponse.getJSONArray("results").length();i++ ){
                int id = myresponse.getJSONArray("results").getJSONObject(i).getInt("id");
                String urlForDetailSearch = "https://api.themoviedb.org/3/movie/"+Integer.toString(id)+"?api_key=a0fa1e3a499f3bf73501f659b480e676&language=en-US";
                urls.add(urlForDetailSearch);     
            }
            if (urls.size() == 0) {
                System.out.println("No relevant movie found. Check your spellings please.");
            }
            else {
                //detailed search, get the exact urls. One for movie information and the other for director information
                for (String url : urls) {
                    String info = apiFectch(url);
                    // url is for movie information
                    try {
                        JSONObject jsonInfo = new JSONObject(info);
                        if (jsonInfo.getString("title").toString().toLowerCase().equals(title.toLowerCase()) && jsonInfo.getString("release_date").toString().equals(releaseDate)){
                            movieInfo.put("title", jsonInfo.getString("title"));
                            movieInfo.put("releaseDate",jsonInfo.getString("release_date"));
                            movieInfo.put("review",jsonInfo.getString("overview"));
                            movieInfo.put("averageVote",Double.toString(jsonInfo.getDouble("vote_average")));
                            movieInfo.put("numOfVotes",Integer.toString(jsonInfo.getInt("vote_count")));
                            movieInfo.put("runtime", Integer.toString(jsonInfo.getInt("runtime")));
                            StringBuffer genre = new StringBuffer("");
                            for (int i=0; i<jsonInfo.getJSONArray("genres").length(); i++){
                                genre.append(jsonInfo.getJSONArray("genres").getJSONObject(i).getString("name"));
                                genre.append("; ");
                            }
                            movieInfo.put("genre", genre.toString());
                            String detailedID = Integer.toString(jsonInfo.getInt("id"));
                            String directorURL = "https://api.themoviedb.org/3/movie/"+detailedID+"/credits?api_key=a0fa1e3a499f3bf73501f659b480e676&language=en-US";
                            String directorInfo = apiFectch(directorURL);
                            JSONObject directorURLJSONInfo = new JSONObject(directorInfo);
                            JSONArray crewJsonArray = directorURLJSONInfo.getJSONArray("crew");
                            for (int i = 0; i < crewJsonArray.length(); i++){
                                if (crewJsonArray.getJSONObject(i).getString("job").equals("Director")) {
                                    movieInfo.put("director", crewJsonArray.getJSONObject(i).getString("name"));
                                }
                            } 
                        }    
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } 
                
                //System.out.println(url); //experiments
            
   
        //System.out.println(movieInfo); //experiments
        if (movieInfo.size()==0 && urls.size()!=0) {
            for (String url : urls) {
                String info = apiFectch(url);
                try {
                    JSONObject jsonInfo = new JSONObject(info);
                    System.out.printf("title: %-50s release year: %s\n", jsonInfo.getString("title").toString(), jsonInfo.getString("release_date").toString());                
                    } 
                catch (JSONException e) {
                    e.printStackTrace();
                } 
        
                }
                
            System.out.println("_______________________________________________________________________________________________________________________________________");
            System.out.println("The list above is all relevant movies based on the title and release date you provided");
            System.out.println("No movie is added before more specific information is given, please use accurate information above and retry.");
            System.out.println("Please note that extra blanks is not accepted.");
    } 
    else if (movieInfo.size()!=0) System.out.println("General movie data fetched successfully from database.");    
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return movieInfo;
        }
}
           
        
        
        
        