package OnlineCinemaTicketingSystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class MoviesTicketPrice {
    private SimpleStringProperty moviename;
    private SimpleDoubleProperty ticketprice_r, ticketprice_s, ticketprice_p;

    public MoviesTicketPrice(String moviename, double ticketprice_r, double ticketprice_s, double ticketprice_p) {
        this.moviename = new SimpleStringProperty(moviename);
        this.ticketprice_r = new SimpleDoubleProperty(ticketprice_r);
        this.ticketprice_s = new SimpleDoubleProperty(ticketprice_s);
        this.ticketprice_p = new SimpleDoubleProperty(ticketprice_p);
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

    public double getTicketprice_r() {
        return ticketprice_r.get();
    }

    public SimpleDoubleProperty ticketprice_rProperty() {
        return ticketprice_r;
    }

    public void setTicketprice_r(double ticketprice_r) {
        this.ticketprice_r.set(ticketprice_r);
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
