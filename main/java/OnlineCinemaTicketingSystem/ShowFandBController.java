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

public class ShowFandBController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label menuLabel;

    @FXML
    private TableColumn<MenuItems, String> menudescription;

    @FXML
    private TableColumn<MenuItems, String> menuname;

    @FXML
    private TextField menuname_input;

    @FXML
    private TableColumn<MenuItems, String> menuprice;

    @FXML
    private TableView<MenuItems> menutable;

    @FXML
    private StackPane stackPane;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        menuname_input.clear();
        menuLabel.setText("");
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
        String searchedMenu = menuname_input.getText().trim();
        Boolean resultSetEmpty = true;
        menuItemsObservableList.clear();

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM menuitems WHERE menuname LIKE '"+searchedMenu+"%'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                menuLabel.setText("");
                resultSetEmpty = false;
                menuItemsObservableList.add(new MenuItems(resultSet.getString("menuname"), resultSet.getString("menudescription"), resultSet.getDouble("menuprice")));
            }
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(resultSetEmpty){
            menuLabel.setText("* No Show Times Available For Searched Movie! *");
        }
    }

    @FXML
    void initializer(){
        menuItemsObservableList.clear();
        menuname.setCellValueFactory(new PropertyValueFactory<>("menuname"));
        menudescription.setCellValueFactory(new PropertyValueFactory<>("menudescription"));
        menuprice.setCellValueFactory(new PropertyValueFactory<>("menuprice"));

        menutable.setItems(menuItemsObservableList);

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM menuitems");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                menuItemsObservableList.add(new MenuItems(resultSet.getString("menuname"), resultSet.getString("menudescription"), resultSet.getDouble("menuprice")));
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
    private ObservableList<MenuItems> menuItemsObservableList = FXCollections.observableArrayList();
}
