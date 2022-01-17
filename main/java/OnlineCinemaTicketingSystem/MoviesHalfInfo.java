package OnlineCinemaTicketingSystem;

import javafx.beans.property.SimpleStringProperty;

public class MoviesHalfInfo {
    private SimpleStringProperty moviename, moviegenre, language, casts, synopsis;

    //General constructor
    public MoviesHalfInfo(String moviename, String moviegenre, String language, String casts, String synopsis) {

        this.moviename = new SimpleStringProperty(moviename);
        this.moviegenre = new SimpleStringProperty(moviegenre);
        this.language = new SimpleStringProperty(language);
        this.casts = new SimpleStringProperty(casts);
        this.synopsis = new SimpleStringProperty(synopsis);
    }

    public void setMoviename(String moviename) {
        this.moviename.set(moviename);
    }

    public void setMoviegenre(String moviegenre) {
        this.moviegenre.set(moviegenre);
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public void setCasts(String casts) {
        this.casts.set(casts);
    }

    public void setSynopsis(String synopsis) {
        this.synopsis.set(synopsis);
    }

    public String getMoviename() {
        return moviename.get();
    }

    public SimpleStringProperty movienameProperty() {
        return moviename;
    }

    public String getMoviegenre() {
        return moviegenre.get();
    }

    public SimpleStringProperty moviegenreProperty() {
        return moviegenre;
    }

    public String getLanguage() {
        return language.get();
    }

    public SimpleStringProperty languageProperty() {
        return language;
    }

    public String getCasts() {
        return casts.get();
    }

    public SimpleStringProperty castsProperty() {
        return casts;
    }

    public String getSynopsis() {
        return synopsis.get();
    }

    public SimpleStringProperty synopsisProperty() {
        return synopsis;
    }

    //Default constructor
    public MoviesHalfInfo() {
    }

    //Copy constructor
    public MoviesHalfInfo(MoviesHalfInfo movie){
        this.moviename = movie.moviename;
        this.moviegenre = movie.moviegenre;
        this.casts = movie.casts;
        this.language = movie.language;
        this.synopsis = movie.synopsis;
    }

}
