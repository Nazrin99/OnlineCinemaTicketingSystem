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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class RemoveMoviesController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    SetTicketPriceController setTicketPriceController = new SetTicketPriceController();

    @FXML
    private ChoiceBox<String> moviename_delete;

    @FXML
    private TableColumn<MoviesHalfInfo, String> casts;

    @FXML
    private TableColumn<MoviesHalfInfo, String> language;

    @FXML
    private TableColumn<MoviesHalfInfo, String> moviegenre;

    @FXML
    private TableColumn<MoviesHalfInfo, String> moviename;

    @FXML
    private TableView<MoviesHalfInfo> movietable;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableColumn<MoviesHalfInfo, String> synopsis;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {

    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {
        String moviename = (moviename_delete.getValue().toString().trim()), moviename1 = "";
        String moviegenre = "", language = "", casts = "", synopsis = "";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieshalfinfo WHERE moviename LIKE '" + moviename + "%';");

            while(resultSet.next()) {
                moviename1 = resultSet.getString("moviename");
                moviegenre = resultSet.getString("moviegenre");
                language = resultSet.getString("language");
                casts = resultSet.getString("casts");
                synopsis = resultSet.getString("synopsis");
            }
            statement.close();
            resultSet.close();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM movieshalfinfo WHERE moviename = '"+ moviename1 +"';");
            preparedStatement.execute();
            preparedStatement.close();
            int remove = moviesHalfInfoObservableList.indexOf(new MoviesHalfInfo(moviename, moviegenre, language, casts, synopsis))+1;
            moviesHalfInfoObservableList.remove(remove);

            int remove1 = movieAdded.indexOf(moviename1)+1;
            movieAdded.remove(remove1);

            PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM moviesshowtime WHERE moviename = '"+moviename1+"';");
            preparedStatement1.execute();
            preparedStatement1.close();

            PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM moviesticketprice WHERE moviename = '"+moviename1+"';");
            int remove2 = setTicketPriceController.moviesTicketPriceObservableList.indexOf(new MoviesTicketPrice(moviename1, 0.0,0.0,0.0));
            setTicketPriceController.moviesTicketPriceObservableList.remove(remove2);

            preparedStatement2.execute();
            preparedStatement2.close();

            PreparedStatement preparedStatement3 = connection.prepareStatement("DELETE FROM moviesfullinfo WHERE moviename = '"+moviename1+"';");
            preparedStatement3.execute();
            preparedStatement3.close();

            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StaffInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Staff Interface");
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
        moviename.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        moviegenre.setCellValueFactory(new PropertyValueFactory<>("moviegenre"));
        language.setCellValueFactory(new PropertyValueFactory<>("language"));
        casts.setCellValueFactory(new PropertyValueFactory<>("casts"));
        synopsis.setCellValueFactory(new PropertyValueFactory<>("synopsis"));

        movietable.setItems(moviesHalfInfoObservableList);

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movieshalfinfo");

            while(resultSet.next()) {
                moviesHalfInfoObservableList.add(new MoviesHalfInfo(resultSet.getString("moviename"), resultSet.getString("moviegenre"), resultSet.getString("language"), resultSet.getString("casts"), resultSet.getString("synopsis")));
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
        moviename_delete.setItems(movieAdded);
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movieshalfinfo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                movieAdded.add(resultSet.getString("moviename"));
            }
        } catch (Exception e){
            System.err.println();
            System.err.println();
        }
    }
    private ObservableList<MoviesHalfInfo> moviesHalfInfoObservableList = FXCollections.observableArrayList();

    private ObservableList<String> movieAdded = FXCollections.observableArrayList();
}
