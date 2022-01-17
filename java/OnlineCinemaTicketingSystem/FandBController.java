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

public class FandBController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<MenuItems, String> menudescription;

    @FXML
    private Label fandb_totalprice;

    @FXML
    private Label menulabel_buy;

    @FXML
    private TableColumn<MenuItems, String> menuname;

    @FXML
    private TextField menuname_buy;

    @FXML
    private TableColumn<MenuItems, String> menuprice;

    @FXML
    private TableView<MenuItems> menutable;

    @FXML
    private StackPane stackPane;

    Double totalprice_fandb = 0.0;

    @FXML
    void addMenuItemsButtonClicked(ActionEvent event) {
        String menuname = menuname_buy.getText().trim();
        Double menuprice = 0.0;
        Boolean menuexists = false;

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM menuitems WHERE menuname = '"+menuname+"';");

            while(resultSet.next()){
                if(resultSet.getString("menuname").equalsIgnoreCase(menuname)){
                    menuexists = true;
                    menuprice = resultSet.getDouble("menuprice");
                    totalprice_fandb += menuprice;
                    fandb_totalprice.setText("Total Price : " + totalprice_fandb);
                    menulabel_buy.setText("");
                }
            }
            statement.close();
            resultSet.close();
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(!menuexists){
            menulabel_buy.setText("* Menu Items DOES NOT EXISTS! *");
            menuname_buy.clear();
        }
    }

    @FXML
    void checkoutButtonClicked(ActionEvent event) {
        String username = "";
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currentloggeduser");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                username = resultSet.getString("username");
            }
            preparedStatement.close();
            resultSet.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE currentloggeduser SET fandbtotalprice = "+totalprice_fandb+" WHERE username = '"+username+"'");
            preparedStatement1.execute();
            preparedStatement1.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("PickSeatsInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Pick Your Seats");
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
        menuname.setCellValueFactory(new PropertyValueFactory<>("menuname"));
        menudescription.setCellValueFactory(new PropertyValueFactory<>("menudescription"));
        menuprice.setCellValueFactory(new PropertyValueFactory<>("menuprice"));

        menutable.setItems(menuItemsObservableList);
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM menuitems");

            while(resultSet.next()) {
                menuItemsObservableList.add(new MenuItems(resultSet.getString("menuname"), resultSet.getString("menudescription"), resultSet.getDouble("menuprice")));
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
    }

    private ObservableList<MenuItems> menuItemsObservableList = FXCollections.observableArrayList();
}
