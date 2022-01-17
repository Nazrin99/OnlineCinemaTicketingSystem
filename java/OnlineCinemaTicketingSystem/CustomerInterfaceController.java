package OnlineCinemaTicketingSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CustomerInterfaceController implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private Button bookMovie;

    @FXML
    private Button bookingHistory;

    @FXML
    private Button seeShowtimes;

    @FXML
    private Button showFandBOption;

    @FXML
    private Button showMovies;

    @FXML
    private Button showTicketPrice;

    @FXML
    private Label welcomelabel;

    @FXML
    void bookMovieButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("BookMovieInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Book Movie Window");
        stage.show();
    }

    @FXML
    void bookingHistoryButtonClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("BookingHistoryInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Customer Booking History");
        stage.show();
    }

    @FXML
    void seeShowtimesButtonClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("SeeShowtimesInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Showtimes Window");
        stage.show();
    }

    @FXML
    void showFandBOptionButtonClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("ShowFandBInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("F&B Window");
        stage.show();
    }

    @FXML
    void showMoviesButtonClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("ShowMoviesInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("GCS Login Page");
        stage.show();
    }

    @FXML
    void showTicketPriceButtonClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("ShowTicketPriceInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Show Ticket Price Interface");
        stage.show();
    }

    @FXML
    void signoutButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("GCS Login Page");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currentloggeduser");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
               welcomelabel.setText("Welcome " + resultSet.getString("username") + "!");
            }
        } catch(Exception e){
            System.err.println();
            System.err.println();
        }
    }
}
