package OnlineCinemaTicketingSystem;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MoviesFullInfo {
    private SimpleStringProperty moviename, moviegenre, language, casts, synopsis, date, timestart, timeend, duration;
    private SimpleIntegerProperty hall, seatcapacity;
    private SimpleDoubleProperty ticketprice_s, ticketprice_r, ticketprice_p;

    public int getSeatcapacity() {
        return seatcapacity.get();
    }

    public SimpleIntegerProperty seatcapacityProperty() {
        return seatcapacity;
    }

    public void setSeatcapacity(int seatcapacity) {
        this.seatcapacity.set(seatcapacity);
    }

    public MoviesFullInfo() {
    }

    public MoviesFullInfo(String moviename, String moviegenre, String language, String casts, String synopsis, String date, String timestart, String timeend, int hall, int seatcapacity, double ticketprice_s, double ticketprice_r, double ticketprice_p) {
        this.moviename = new SimpleStringProperty(moviename);
        this.moviegenre = new SimpleStringProperty(moviegenre);
        this.language = new SimpleStringProperty(language);
        this.casts = new SimpleStringProperty(casts);
        this.synopsis = new SimpleStringProperty(synopsis);
        this.date = new SimpleStringProperty(date);
        this.timestart = new SimpleStringProperty(timestart);
        this.timeend = new SimpleStringProperty(timeend);
        this.duration = new SimpleStringProperty(durationCalculator(timestart, timeend));
        this.hall = new SimpleIntegerProperty(hall);
        this.seatcapacity = new SimpleIntegerProperty(seatcapacity);
        this.ticketprice_s = new SimpleDoubleProperty(ticketprice_s);
        this.ticketprice_r = new SimpleDoubleProperty(ticketprice_r);
        this.ticketprice_p = new SimpleDoubleProperty(ticketprice_p);
    }

    public MoviesFullInfo(String moviename, String date, String timestart, String timeend, int hall, double ticketprice_s, double ticketprice_r, double ticketprice_p) {
        this.moviename = new SimpleStringProperty(moviename);
        this.date = new SimpleStringProperty(date);
        this.timestart = new SimpleStringProperty(timestart);
        this.timeend = new SimpleStringProperty(timeend);
        this.duration = new SimpleStringProperty(durationCalculator(timestart, timeend));
        this.hall = new SimpleIntegerProperty(hall);
        this.ticketprice_s = new SimpleDoubleProperty(ticketprice_s);
        this.ticketprice_r = new SimpleDoubleProperty(ticketprice_r);
        this.ticketprice_p = new SimpleDoubleProperty(ticketprice_p);
    }

    public MoviesFullInfo(String moviename, String date, String timestart, String timeend, int hall, int seatcapacity) {
        this.moviename = new SimpleStringProperty(moviename);
        this.date = new SimpleStringProperty(date);
        this.timestart = new SimpleStringProperty(timestart);
        this.timeend = new SimpleStringProperty(timeend);
        this.duration = new SimpleStringProperty(durationCalculator(timestart,timeend));
        this.hall = new SimpleIntegerProperty(hall);
        this.seatcapacity = new SimpleIntegerProperty(seatcapacity);
    }

    public MoviesFullInfo(String moviename, String date, String timestart, String timeend, int hall) {
        this.moviename = new SimpleStringProperty(moviename);
        this.date = new SimpleStringProperty( date);
        this.timestart = new SimpleStringProperty(timestart);
        this.timeend = new SimpleStringProperty(timeend);
        this.duration = new SimpleStringProperty(durationCalculator(timestart, timeend));
        this.hall = new SimpleIntegerProperty(hall);
    }



    //Method to calculate duration
    private String durationCalculator(String time_start, String time_end){
        int timestart_hours, timestart_minutes, timeend_hours, timeend_minutes, hours, minutes, duration;

        timestart_hours = Integer.parseInt(time_start.substring(0,2));
        timestart_minutes = Integer.parseInt(time_start.substring(2,4)) + timestart_hours*60;
        timeend_hours = Integer.parseInt(time_end.substring(0,2));
        timeend_minutes = Integer.parseInt(time_end.substring(2,4)) + timeend_hours*60;

        duration = timeend_minutes - timestart_minutes;
        return Integer.toString(duration/60) + "h " + Integer.toString(duration%60) + "m";

    }

    public String getMoviename() {
        return moviename.get();
    }

    public SimpleStringProperty movienameProperty() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename.set(moviename);
    }

    public String getMoviegenre() {
        return moviegenre.get();
    }

    public SimpleStringProperty moviegenreProperty() {
        return moviegenre;
    }

    public void setMoviegenre(String moviegenre) {
        this.moviegenre.set(moviegenre);
    }

    public String getLanguage() {
        return language.get();
    }

    public SimpleStringProperty languageProperty() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public String getCasts() {
        return casts.get();
    }

    public SimpleStringProperty castsProperty() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts.set(casts);
    }

    public String getSynopsis() {
        return synopsis.get();
    }

    public SimpleStringProperty synopsisProperty() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis.set(synopsis);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTimestart() {
        return timestart.get();
    }

    public SimpleStringProperty timestartProperty() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart.set(timestart);
    }

    public String getTimeend() {
        return timeend.get();
    }

    public SimpleStringProperty timeendProperty() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend.set(timeend);
    }

    public String getDuration() {
        return duration.get();
    }

    public SimpleStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public int getHall() {
        return hall.get();
    }

    public SimpleIntegerProperty hallProperty() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall.set(hall);
    }

    public double getTicketprice_s() {
        return ticketprice_s.get();
    }

    public SimpleDoubleProperty ticketprice_sProperty() {
        return ticketprice_s;
    }

    public void setTicketprice_s(double ticketprice_s) {
        this.ticketprice_s.set(ticketprice_s);
    }

    public double getTicketprice_r() {
        return ticketprice_r.get();
    }

    public SimpleDoubleProperty ticketprice_rProperty() {
        return ticketprice_r;
    }

    public void setTicketprice_r(double ticketprice_r) {
        this.ticketprice_r.set(ticketprice_r);
    }

    public double getTicketprice_p() {
        return ticketprice_p.get();
    }

    public SimpleDoubleProperty ticketprice_pProperty() {
        return ticketprice_p;
    }

    public void setTicketprice_p(double ticketprice_p) {
        this.ticketprice_p.set(ticketprice_p);
    }
}
