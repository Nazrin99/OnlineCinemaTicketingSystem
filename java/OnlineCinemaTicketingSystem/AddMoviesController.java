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
import java.util.concurrent.TimeUnit;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddMoviesController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableView<MoviesHalfInfo> movietable;

    @FXML
    private TableColumn<MoviesHalfInfo, String> synopsis;

    @FXML
    private TableColumn<MoviesHalfInfo, String> casts;

    @FXML
    private TableColumn<MoviesHalfInfo, String> language;

    @FXML
    private TableColumn<MoviesHalfInfo, String> moviegenre;

    @FXML
    private TableColumn<MoviesHalfInfo, String> moviename;

    @FXML
    private TextField casts_input;

    @FXML
    private TextField genre_input;

    @FXML
    private TextField language_input;

    @FXML
    private TextField moviename_input;

    @FXML
    private TextArea synopsis_input;


    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        moviename_input.clear();
        genre_input.clear();
        language_input.clear();
        synopsis_input.clear();
        casts_input.clear();
    }

    @FXML
    void confirmButtonClicked(ActionEvent event) {
        String moviename = moviename_input.getText().trim();
        String language = language_input.getText().trim();
        String synopsis = synopsis_input.getText().trim();
        String genre = genre_input.getText().trim();
        String casts = casts_input.getText().trim();

        moviesHalfInfoObservableList.add(new MoviesHalfInfo(moviename,genre,language, casts, synopsis));

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO movieshalfinfo(moviename, moviegenre,language, casts, synopsis)" +
                            "VALUES('"+moviename+"','"+genre+"','"+language+"','"+casts+"','"+synopsis+"');"
            );

            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO moviesticketprice(moviename)" +
                    "VALUES('"+moviename+"';");
            statement.execute();
            statement1.execute();

            statement.close();
            statement1.close();
            connection.close();
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void refreshTableButtonClicked(ActionEvent event) throws InterruptedException {
        stackPane.setVisible(false);
        stackPane.setManaged(false);
        TimeUnit.MILLISECONDS.sleep(1000);
        stackPane.setVisible(true);
        stackPane.setManaged(true);
    }

    @FXML
    private StackPane stackPane;

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StaffInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Staff Main page");
        stage.show();
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
    }

    private ObservableList<MoviesHalfInfo> moviesHalfInfoObservableList = FXCollections.observableArrayList();
}
