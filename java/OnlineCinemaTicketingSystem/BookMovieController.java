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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class BookMovieController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<MoviesFullInfo, String> date;

    @FXML
    private TextField date_book;

    @FXML
    private TableColumn<MoviesFullInfo, String> duration;

    @FXML
    private TableColumn<MoviesFullInfo, Integer> hall;

    @FXML
    private TextField hall_book;

    @FXML
    private Label hall_label;

    @FXML
    private TableColumn<MoviesFullInfo, String> moviename;

    @FXML
    private ChoiceBox<String> moviename_book;

    @FXML
    private TableView<MoviesFullInfo> movietable_showtimes;

    @FXML
    private Label seatcapacity_label;

    @FXML
    private Label seatcapacity_label1;

    @FXML
    private Label seatcapacity_label2;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField ticket_p_book;

    @FXML
    private TextField ticket_r_book;

    @FXML
    private TextField ticket_s_book;

    @FXML
    private TableColumn<MoviesFullInfo, Double> ticketprice_p;

    @FXML
    private TableColumn<MoviesFullInfo, Double> ticketprice_r;

    @FXML
    private TableColumn<MoviesFullInfo, Double> ticketprice_s;

    @FXML
    private Label book_label;

    @FXML
    private TableColumn<MoviesFullInfo, String> timeend;

    @FXML
    private TextField timeend_book;

    @FXML
    private TableColumn<MoviesFullInfo, String> timestart;

    @FXML
    private TextField timestart_book;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        timeend_book.clear();
        timestart_book.clear();
        ticket_s_book.clear();
        ticket_p_book.clear();
        ticket_r_book.clear();
        hall_book.clear();
        date_book.clear();
        book_label.setText("");

    }

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CustomerInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Customer Main Page");
        stage.show();
    }

    @FXML
    void pickSeatsButtonClicked(ActionEvent event) throws IOException{
        String moviename = moviename_book.getValue().trim();
        String date = date_book.getText().trim();
        String timestart = timestart_book.getText().trim();
        String timeend = timeend_book.getText().trim(), duration = "";
        Boolean showtimes_exists = false;
        int hall = Integer.parseInt(hall_book.getText().trim());
        int ticket_r_num = Integer.parseInt(ticket_r_book.getText().trim());
        int ticket_s_num = Integer.parseInt(ticket_s_book.getText().trim());
        int ticket_p_num = Integer.parseInt(ticket_p_book.getText().trim());

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesshowtime WHERE moviename = '"+moviename+"' AND date = '"+date+"' AND timestart = '"+timestart+"' AND timeend = '"+timeend+"' AND hall = "+hall+";");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                showtimes_exists = true;
                duration = resultSet.getString("duration");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }

        if(!showtimes_exists){
            book_label.setText("* Showtime doesn't exists! *");
        }
        else{
           try{
               Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
               PreparedStatement preparedStatement1 = connection1.prepareStatement("UPDATE currentloggeduser SET moviename = '"+moviename+"', date = '"+date+"', hall = "+hall+", timestart = '"+timestart+"', timeend = '"+timeend+"', duration = '"+duration+"', ticket_r_num = "+ticket_r_num+", ticket_s_num = "+ticket_s_num+", ticket_p_num = "+ticket_p_num+";");
               preparedStatement1.execute();
               preparedStatement1.close();

               connection1.close();
           } catch(Exception e){
               System.err.println();
               System.err.println(e.getMessage());
           }
            root = FXMLLoader.load(getClass().getResource("PickSeatsInterface.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Showtimes Window");
            stage.show();
        }


    }

    @FXML
    void refreshTableButtonClicked(ActionEvent event) throws InterruptedException{
        stackPane.setVisible(false);
        stackPane.setManaged(false);
        TimeUnit.MILLISECONDS.sleep(1000);
        stackPane.setVisible(true);
        stackPane.setManaged(true);
    }

    private ObservableList<String> movieAdded = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddShowtimesController addShowtimesController = new AddShowtimesController();
        moviename_book.setItems(movieAdded);
        movietable_showtimes.setItems(moviesFullInfoObservableList);

        moviename.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        timestart.setCellValueFactory(new PropertyValueFactory<>("timestart"));
        timeend.setCellValueFactory(new PropertyValueFactory<>("timeend"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        hall.setCellValueFactory(new PropertyValueFactory<>("hall"));
        ticketprice_r.setCellValueFactory(new PropertyValueFactory<>("ticketprice_r"));
        ticketprice_s.setCellValueFactory(new PropertyValueFactory<>("ticketprice_s"));
        ticketprice_p.setCellValueFactory(new PropertyValueFactory<>("ticketprice_p"));

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesfullinfo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                moviesFullInfoObservableList.add(new MoviesFullInfo(resultSet.getString("moviename"), resultSet.getString("date"),resultSet.getString("timestart"), resultSet.getString("timeend"), resultSet.getInt("hall"),resultSet.getDouble("ticketprice_s"), resultSet.getDouble("ticketprice_r"), resultSet.getDouble("ticketprice_p")));
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movieshalfinfo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                movieAdded.add(resultSet.getString("moviename"));
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
    }


    private ObservableList<MoviesFullInfo> moviesFullInfoObservableList = FXCollections.observableArrayList();
}