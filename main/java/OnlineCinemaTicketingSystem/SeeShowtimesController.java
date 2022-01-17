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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class SeeShowtimesController implements Initializable {

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private TableColumn<MoviesFullInfo, String> moviename_showtimes;

    @FXML
    private TableColumn<MoviesFullInfo, String> date_showtimes;

    @FXML
    private TableColumn<MoviesFullInfo, String> duration_showtimes;

    @FXML
    private TableColumn<MoviesFullInfo, Integer> hall_showtimes;

    @FXML
    private TextField moviename_input;

    @FXML
    private TableView<MoviesFullInfo> movietable_showtimes;

    @FXML
    private Label showMoviesLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableColumn<MoviesFullInfo, String> timeend_showtimes;

    @FXML
    private TableColumn<MoviesFullInfo, String> timestart_showtimes;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        moviename_input.clear();
        showMoviesLabel.setText("");
        initializer();
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
    void refreshTableButtonClicked(ActionEvent event) throws InterruptedException{
        stackPane.setVisible(false);
        stackPane.setManaged(false);
        TimeUnit.MILLISECONDS.sleep(1000);
        stackPane.setVisible(true);
        stackPane.setManaged(true);
    }

    @FXML
    void searchButtonClicked(ActionEvent event) {
        String searchedMovie = moviename_input.getText().trim();
        Boolean resultSetEmpty = true;
        moviesFullInfoObservableList.clear();

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesfullinfo WHERE moviename = '"+searchedMovie+"'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                showMoviesLabel.setText("");
                resultSetEmpty = false;
                moviesFullInfoObservableList.add(new MoviesFullInfo(resultSet.getString("moviename"), resultSet.getString("date"), resultSet.getString("timestart"), resultSet.getString("timeend"), resultSet.getInt("hall")));
            }
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(resultSetEmpty){
            showMoviesLabel.setText("* No Show Times Available For Searched Movie! *");
        }
    }

    @FXML
    void initializer(){
        moviesFullInfoObservableList.clear();
        moviename_showtimes.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        date_showtimes.setCellValueFactory(new PropertyValueFactory<>("date"));
        timestart_showtimes.setCellValueFactory(new PropertyValueFactory<>("timestart"));
        timeend_showtimes.setCellValueFactory(new PropertyValueFactory<>("timeend"));
        duration_showtimes.setCellValueFactory(new PropertyValueFactory<>("duration"));
        hall_showtimes.setCellValueFactory(new PropertyValueFactory<>("hall"));

        movietable_showtimes.setItems(moviesFullInfoObservableList);

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesfullinfo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                moviesFullInfoObservableList.add(new MoviesFullInfo(resultSet.getString("moviename"), resultSet.getString("date"), resultSet.getString("timestart"), resultSet.getString("timeend"), resultSet.getInt("hall")));
            }
            preparedStatement.close();
            connection.close();
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer();
    }

    private ObservableList<MoviesFullInfo> moviesFullInfoObservableList = FXCollections.observableArrayList();
}
