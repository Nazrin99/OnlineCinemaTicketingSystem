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

public class ShowMoviesController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<MoviesHalfInfo, String> casts;

    @FXML
    private TableColumn<MoviesHalfInfo, String> language;

    @FXML
    private TableColumn<MoviesHalfInfo, String> moviegenre;

    @FXML
    private TableColumn<MoviesHalfInfo, String> moviename;

    @FXML
    private TextField moviename_show;

    @FXML
    private TableView<MoviesHalfInfo> movietable_show;

    @FXML
    private Label showMoviesLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableColumn<MoviesHalfInfo, String> synopsis;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        moviename_show.clear();
        showMoviesLabel.setText("");
        initializer();
    }

    @FXML
    void initializer(){
        moviename.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        moviegenre.setCellValueFactory(new PropertyValueFactory<>("moviegenre"));
        language.setCellValueFactory(new PropertyValueFactory<>("language"));
        casts.setCellValueFactory(new PropertyValueFactory<>("casts"));
        synopsis.setCellValueFactory(new PropertyValueFactory<>("synopsis"));

        movietable_show.setItems(moviesHalfInfoObservableList);
        moviesHalfInfoObservableList.clear();

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
    }

    @FXML
    void searchButtonClicked(ActionEvent event) {
        String searchedMovie = moviename_show.getText().trim(), moviegenre = "", casts = "", synopsis = "", language = "";
        Boolean movie_exists = false;
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movieshalfinfo");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                if(resultSet.getString("moviename").equalsIgnoreCase(searchedMovie)){
                    movie_exists = true;
                    showMoviesLabel.setText("");
                    moviegenre = resultSet.getString("moviegenre");
                    casts = resultSet.getString("casts");
                    synopsis = resultSet.getString("synopsis");
                    language = resultSet.getString("language");
                }
            }
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(!movie_exists){
            moviesHalfInfoObservableList.clear();
            showMoviesLabel.setText("* Movie is NOT AVAILABLE! *");
        }
        else{
            moviesHalfInfoObservableList.clear();
            moviesHalfInfoObservableList.add(new MoviesHalfInfo(searchedMovie, moviegenre, language, casts, synopsis));
        }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer();
    }
    private ObservableList<MoviesHalfInfo> moviesHalfInfoObservableList = FXCollections.observableArrayList();
}
