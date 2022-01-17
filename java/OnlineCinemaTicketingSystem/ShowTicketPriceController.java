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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ShowTicketPriceController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TextField moviename_input;

    @FXML
    private TableColumn<MoviesTicketPrice, String> moviename_ticketprice;

    @FXML
    private TableView<MoviesTicketPrice> movietable_ticketprice;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label ticketpriceLabel;

    @FXML
    private TableColumn<MoviesTicketPrice, Double> ticketprice_p;

    @FXML
    private TableColumn<MoviesTicketPrice, Double> ticketprice_r;

    @FXML
    private TableColumn<MoviesTicketPrice, Double> ticketprice_s;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        ticketpriceLabel.setText("");
        moviename_input.clear();
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
        moviesTicketPriceObservableList.clear();

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesticketprice WHERE moviename = '"+searchedMovie+"'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ticketpriceLabel.setText("");
                resultSetEmpty = false;
                moviesTicketPriceObservableList.add(new MoviesTicketPrice(resultSet.getString("moviename"), resultSet.getDouble("ticketprice_r"), resultSet.getDouble("ticketprice_s"), resultSet.getDouble("ticketprice_p")));
            }
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(resultSetEmpty){
            ticketpriceLabel.setText("* Ticket Price Is Not Set For Searched Movie! *");
        }
    }

    @FXML
    void initializer(){
        moviesTicketPriceObservableList.clear();
        moviename_ticketprice.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        ticketprice_r.setCellValueFactory(new PropertyValueFactory<>("ticketprice_r"));
        ticketprice_s.setCellValueFactory(new PropertyValueFactory<>("ticketprice_s"));
        ticketprice_p.setCellValueFactory(new PropertyValueFactory<>("ticketprice_p"));

        movietable_ticketprice.setItems(moviesTicketPriceObservableList);

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM moviesticketprice");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                moviesTicketPriceObservableList.add(new MoviesTicketPrice(resultSet.getString("moviename"), resultSet.getDouble("ticketprice_r"), resultSet.getDouble("ticketprice_s"), resultSet.getDouble("ticketprice_p")));
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

    @FXML
    private ObservableList<MoviesTicketPrice> moviesTicketPriceObservableList = FXCollections.observableArrayList();
}
