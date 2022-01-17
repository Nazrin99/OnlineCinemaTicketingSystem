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
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class SetTicketPriceController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label hall_label;

    @FXML
    private TableColumn<MoviesTicketPrice, String> moviename;

    @FXML
    private ChoiceBox<String> moviename_showtimes;

    @FXML
    private TableView<MoviesTicketPrice> movietable_ticketprice;

    @FXML
    private TextField price_premium;

    @FXML
    private TextField price_regular;

    @FXML
    private TextField price_student;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableColumn<MoviesTicketPrice, Double> ticketprice_p;

    @FXML
    private TableColumn<MoviesTicketPrice, Double> ticketprice_r;

    @FXML
    private TableColumn<MoviesTicketPrice, Double> ticketprice_s;

    @FXML
    private Label time_label;

    @FXML
    void addTicketPriceButtonClicked(ActionEvent event) {
        String moviename = moviename_showtimes.getValue();
        double ticketprice_r = Double.parseDouble(price_regular.getText().trim()), ticketprice_r_db = 0;
        double ticketprice_s = Double.parseDouble(price_student.getText().trim()), ticketprice_s_db = 0;
        double ticketprice_p = Double.parseDouble(price_premium.getText().trim()), ticketprice_p_db = 0;
        boolean movieexists = false;

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviesticketprice");

            while(resultSet.next()){
                if(resultSet.getString("moviename").equalsIgnoreCase(moviename)){
                    movieexists = true;
                    ticketprice_p_db = resultSet.getDouble("ticketprice_p");
                    ticketprice_r_db = resultSet.getDouble("ticketprice_r");
                    ticketprice_s_db = resultSet.getDouble("ticketprice_s");
                }
            }
            statement.close();
            resultSet.close();
            connection.close();
        }catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement;
            if(movieexists){
                int remove = moviesTicketPriceObservableList.indexOf(new MoviesTicketPrice(moviename, ticketprice_r_db, ticketprice_s_db, ticketprice_p_db))+1;
                moviesTicketPriceObservableList.remove(remove);
                moviesTicketPriceObservableList.add(new MoviesTicketPrice(moviename, ticketprice_r, ticketprice_s, ticketprice_p));

                preparedStatement = connection.prepareStatement("UPDATE moviesticketprice SET ticketprice_r = "+ticketprice_r+",ticketprice_s = "+ticketprice_s+", ticketprice_p = "+ticketprice_p+";");
                preparedStatement.execute();
            }
            else{
                preparedStatement = connection.prepareStatement("INSERT INTO moviesticketprice(moviename, ticketprice_r, ticketprice_s, ticketprice_p)" +
                        "VALUES('"+moviename+"',ticketprice_r, ticketprice_s, ticketprice_p);");
                preparedStatement.execute();

                moviesTicketPriceObservableList.add(new MoviesTicketPrice(moviename, ticketprice_r, ticketprice_s, ticketprice_p));
            }
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        price_premium.clear();
        price_regular.clear();
        price_student.clear();
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

    ObservableList<String> movieAdded = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieshalfinfo");

            while (resultSet.next()) {
                String moviename = resultSet.getString("moviename").trim();
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
        ticketprice_s.setCellValueFactory(new PropertyValueFactory<>("ticketprice_s"));
        ticketprice_p.setCellValueFactory(new PropertyValueFactory<>("ticketprice_p"));
        ticketprice_r.setCellValueFactory(new PropertyValueFactory<>("ticketprice_r"));

        movietable_ticketprice.setItems(moviesTicketPriceObservableList);

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviesticketprice");

            while(resultSet.next()) {
                moviesTicketPriceObservableList.add(new MoviesTicketPrice(resultSet.getString("moviename"),resultSet.getDouble("ticketprice_r"), resultSet.getDouble("ticketprice_s"), resultSet.getDouble("ticketprice_p")));
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public ObservableList<MoviesTicketPrice> moviesTicketPriceObservableList = FXCollections.observableArrayList();
}
