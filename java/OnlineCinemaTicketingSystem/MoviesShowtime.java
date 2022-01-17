package OnlineCinemaTicketingSystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MoviesShowtime {
    private SimpleStringProperty moviename, date, timestart, timeend, duration;
    private SimpleIntegerProperty hall, seatcapacity;

    public MoviesShowtime(String moviename, String date, String timestart, String timeend, int hall, int seatcapacity) {

        this.moviename = new SimpleStringProperty(moviename);
        this.date = new SimpleStringProperty(date);
        this.timestart = new SimpleStringProperty(timestart);
        this.timeend = new SimpleStringProperty(timeend);
        this.duration = new SimpleStringProperty(durationCalculator(timestart,timeend));
        this.hall = new SimpleIntegerProperty(hall);
        this.seatcapacity = new SimpleIntegerProperty(seatcapacity);
    }



    public void setMoviename(String moviename) {
        this.moviename.set(moviename);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setTimestart(String timestart) {
        this.timestart.set(timestart);
    }

    public void setTimeend(String timeend) {
        this.timeend.set(timeend);
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public void setHall(int hall) {
        this.hall.set(hall);
    }

    public void setSeatcapacity(int seatcapacity) {
        this.seatcapacity.set(seatcapacity);
    }

    public int getSeatcapacity() {
        return seatcapacity.get();
    }

    public SimpleIntegerProperty seatcapacityProperty() {
        return seatcapacity;
    }

    public String getMoviename() {
        return moviename.get();
    }

    public SimpleStringProperty movienameProperty() {
        return moviename;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getTimestart() {
        return timestart.get();
    }

    public SimpleStringProperty timestartProperty() {
        return timestart;
    }

    public String getTimeend() {
        return timeend.get();
    }

    public SimpleStringProperty timeendProperty() {
        return timeend;
    }

    public String getDuration() {
        return duration.get();
    }

    public SimpleStringProperty durationProperty() {
        return duration;
    }

    public int getHall() {
        return hall.get();
    }

    public SimpleIntegerProperty hallProperty() {
        return hall;
    }

    public MoviesShowtime() {
    }

    public String durationCalculator(String time_start, String time_end){
        int timestart_hours, timestart_minutes, timeend_hours, timeend_minutes, hours, minutes, duration;

        timestart_hours = Integer.parseInt(time_start.substring(0,2));
        timestart_minutes = Integer.parseInt(time_start.substring(2,4)) + timestart_hours*60;
        timeend_hours = Integer.parseInt(time_end.substring(0,2));
        timeend_minutes = Integer.parseInt(time_end.substring(2,4)) + timeend_hours*60;

        duration = timeend_minutes - timestart_minutes;
        return Integer.toString(duration/60) + "h " + Integer.toString(duration%60) + "m";

    }
}
