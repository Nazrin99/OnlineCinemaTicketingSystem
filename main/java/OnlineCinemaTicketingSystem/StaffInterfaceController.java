package OnlineCinemaTicketingSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class StaffInterfaceController {

    Stage stage;
    Parent root;
    Scene scene;


    @FXML
    void addMenuButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddMenuItemsInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Add Menu Items Window");
        stage.show();
    }

    @FXML
    void addMoviesButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddMoviesInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Add Movies Window");
        stage.show();
    }

    @FXML
    void removeMenuButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RemoveMenuItemsInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Remove Menu Items Window");
        stage.show();
    }

    @FXML
    void removeMoviesButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RemoveMoviesInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Remove Movies Window");
        stage.show();
    }

    @FXML
    void setTicketPriceButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SetTicketPriceInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Set Ticket Prices Window");
        stage.show();
    }

    @FXML
    void addShowtimesButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddShowtimesInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Add Movie Showtimes Window");
        stage.show();
    }

    @FXML
    void signoutButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("GSC Login Page");
        stage.show();
    }

    @FXML
    void deployButtonClicked(ActionEvent event) {
        String moviename, genre, language, casts, synopsis, date, timestart, timeend, duration;
        int hall, seatcapacity;
        double ticketprice_r, ticketprice_s, ticketprice_p;
        MoviesShowtime[] moviesShowtimes = new MoviesShowtime[100];
        MoviesHalfInfo[] moviesHalfInfos = new MoviesHalfInfo[50];
        MoviesTicketPrice[] moviesTicketPrices = new MoviesTicketPrice[100];
        int allowed_length_showtimes = 0, allowed_length_halfinfo = 0, allowed_length_ticketprice = 0;

        //Create MoviesShowtime objects to populate moviesfullinfo table
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesshowtime");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                moviesShowtimes[allowed_length_showtimes] = new MoviesShowtime(resultSet.getString("moviename"), resultSet.getString("date"), resultSet.getString("timestart"), resultSet.getString("timeend"), resultSet.getInt("hall"), resultSet.getInt("seatcapacity"));
                allowed_length_showtimes++;
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        //Clear moviesfullinfo tables and populate tables with objects of MoviesShowtime
        try {
            int i = 0;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM moviesfullinfo");
            preparedStatement.execute();
            preparedStatement.close();
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO moviesfullinfo(`moviename`, `date`, `timestart`, `timeend`, `duration`, `hall` , `seat_capacity`)" +
                    "VALUES('" + moviesShowtimes[i].getMoviename() + "', '" + moviesShowtimes[i].getDate() + "','" + moviesShowtimes[i].getTimestart() + "','" + moviesShowtimes[i].getTimeend() + "','" + moviesShowtimes[i].getDuration() + "'," + moviesShowtimes[i].getHall() + "," + moviesShowtimes[i].getSeatcapacity() + ");");
            for (i = 0; i < allowed_length_showtimes; i++) {
                preparedStatement1.execute();
            }
            preparedStatement1.close();
            connection.close();

        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        //Get objects for movieshalfinfo
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movieshalfinfo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                moviesHalfInfos[allowed_length_halfinfo] = new MoviesHalfInfo(resultSet.getString("moviename"), resultSet.getString("moviegenre"), resultSet.getString("language"), resultSet.getString("casts"), resultSet.getString("synopsis"));
                allowed_length_halfinfo++;
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        //Write movieshalfinfo data into moviesfullinfo
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            int i = 0;
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE moviesfullinfo SET moviegenre = '" + moviesHalfInfos[i].getMoviegenre() + "', language = '" + moviesHalfInfos[i].getLanguage() + "', casts = '" + moviesHalfInfos[i].getCasts() + "', synopsis = '" + moviesHalfInfos[i].getSynopsis() + "';");
            for (i = 0; i < allowed_length_halfinfo; i++) {
                preparedStatement1.execute();
            }
            preparedStatement1.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        //Get objects for moviesticketprice
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesticketprice");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                moviesTicketPrices[allowed_length_ticketprice] = new MoviesTicketPrice(resultSet.getString("moviename"), resultSet.getDouble("ticketprice_r"), resultSet.getDouble("ticketprice_s"), resultSet.getDouble("ticketprice_p"));
                allowed_length_ticketprice++;
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        //Write moviesticketprice into moviesfullinfo
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            int i = 0;
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE moviesfullinfo SET ticketprice_r = " + moviesTicketPrices[i].getTicketprice_r() + ", ticketprice_s = " + moviesTicketPrices[i].getTicketprice_s() + ", ticketprice_p = " + moviesTicketPrices[i].getTicketprice_p() + " WHERE moviename = '"+moviesTicketPrices[i].getMoviename()+"';");
            for (i = 0; i < allowed_length_ticketprice; i++) {
                preparedStatement1.execute();
            }
            preparedStatement1.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

    }
}


