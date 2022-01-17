package OnlineCinemaTicketingSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class AddShowtimesController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label hall_label;

    @FXML
    private Label seatcapacity_label;

    @FXML
    private Label time_label;

    @FXML
    private ChoiceBox<String> moviename_showtimes;

    @FXML
    private TableColumn<MoviesShowtime, String> date;

    @FXML
    private TextField date_showtimes;

    @FXML
    private TableColumn<MoviesShowtime, String> duration;

    @FXML
    private TableColumn<MoviesShowtime, Integer> hall;

    @FXML
    private TextField hall_theatre;

    @FXML
    private TableColumn<MoviesShowtime, String> moviename;

    @FXML
    private TableView<MoviesShowtime> movietable_showtimes;

    @FXML
    private TableColumn<MoviesShowtime, Integer> seatCapacity;

    @FXML
    private TextField seatCapacity_showtimes;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableColumn<MoviesShowtime, String> timeend;

    @FXML
    private TextField timeend_showtimes;

    @FXML
    private TableColumn<MoviesShowtime, String> timestart;

    @FXML
    private TextField timestart_showtimes;

    ObservableList<String> movieAdded = FXCollections.observableArrayList();

    @FXML
    void deleteShowtimesButtonClicked(ActionEvent event){
        String moviename = moviename_showtimes.getValue().trim();
        String date = date_showtimes.getText().trim();
        int hall = Integer.parseInt(hall_theatre.getText().trim());
        int seatcapacity  = Integer.parseInt(seatCapacity_showtimes.getText().trim());
        String timestart = timestart_showtimes.getText().trim();
        String timeend = timeend_showtimes.getText().trim();
        Boolean hallexists = hallexists(hall);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedstatement = connection.prepareStatement("DELETE FROM moviesshowtime " +
                    "WHERE moviename = '"+moviename+"' AND hall = "+hall+" AND timestart ='"+timestart+"' AND'"+timeend+"' AND `date` = '"+date+"'");
            preparedstatement.execute();
            preparedstatement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        int remove = moviesShowtimeObservableList.indexOf(new MoviesShowtime(moviename, date, timestart, timeend, hall, seatcapacity))+1;
        moviesShowtimeObservableList.remove(remove);


    }

    @FXML
    void addShowtimesButtonClicked(ActionEvent event) {
        String moviename = moviename_showtimes.getValue();
        String date = date_showtimes.getText().trim();
        int hall = Integer.parseInt(hall_theatre.getText().trim());
        int seatcapacity  = Integer.parseInt(seatCapacity_showtimes.getText().trim());
        String timestart = timestart_showtimes.getText().trim();
        String timeend = timeend_showtimes.getText().trim();
        Boolean hallexists = hallexists(hall);;

        if(!hallexists && seatcapacity > 50){
            hall_label.setText("* Invalid theatre hall number *");
            seatcapacity_label.setText("* Exceeded Maximum Capacity(50) *");
        }
        else if(!hallexists && seatcapacity <= 50){
            hall_label.setText("* Invalid theatre hall number *");
        }
        else if(hallexists && seatcapacity > 50){
            seatcapacity_label.setText("Exceeded maximum capacity");
        }
        else{
            hall_label.setText("");
            seatcapacity_label.setText("");
            moviesShowtimeObservableList.add(new MoviesShowtime(moviename, date, timestart, timeend, hall, seatcapacity));

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedstatement = connection.prepareStatement("INSERT INTO moviesshowtime(moviename,date, timestart, timeend,duration, hall,seatcapacity)" +
                        "VALUES('"+moviename+"','"+date+"','"+timestart+"','"+timeend+"','"+this.duration.getText().trim()+"',"+hall+","+seatcapacity+");");
                preparedstatement.execute();
                preparedstatement.close();

                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO moviesbookedseats(moviename,date, timestart, timeend, hall)" +
                        "VALUES('"+moviename+"','"+date+"','"+timestart+"','"+timeend+"',"+hall+");");


                connection.close();
            } catch (Exception e) {
                System.err.println();
                System.err.println(e.getMessage());
            }
        }
    }

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        date_showtimes.clear();
        timeend_showtimes.clear();
        timestart_showtimes.clear();
        seatCapacity_showtimes.clear();
        hall_theatre.clear();
        hall_label.setText("");
        seatcapacity_label.setText("");
    }

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StaffInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Staff Main Page");
        stage.show();
    }

    @FXML
    void refreshTableButtonClicked(ActionEvent event) throws InterruptedException{
        stackPane.setVisible(false);
        stackPane.setManaged(false);
        TimeUnit.MILLISECONDS.sleep(1000);
        stackPane.setVisible(true);
        stackPane.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieshalfinfo");

            while (resultSet.next()) {
                String moviename = resultSet.getString("moviename");
                movieAdded.add(moviename);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

        moviename_showtimes.setItems(movieAdded);

        moviename.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        timestart.setCellValueFactory(new PropertyValueFactory<>("timestart"));
        timeend.setCellValueFactory(new PropertyValueFactory<>("timeend"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        hall.setCellValueFactory(new PropertyValueFactory<>("hall"));
        seatCapacity.setCellValueFactory(new PropertyValueFactory<>("seatcapacity"));

        movietable_showtimes.setItems(moviesShowtimeObservableList);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviesshowtime");

            while (resultSet.next()) {
                String moviename = resultSet.getString("moviename");
                String date = resultSet.getString("date");
                String timestart = resultSet.getString("timestart");
                String timeend = resultSet.getString("timeend");
                int seatcapacity = resultSet.getInt("seatcapacity");
                int hall = resultSet.getInt("hall");
                moviesShowtimeObservableList.add(new MoviesShowtime(moviename, date, timestart, timeend, hall, seatcapacity));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    public boolean hallexists(int hall){
        if(hall < 1 || hall > 9){
            return false;
        }
        else{
            return true;
        }
    }

    @FXML
    private ObservableList<MoviesShowtime> moviesShowtimeObservableList = FXCollections.observableArrayList();

}
