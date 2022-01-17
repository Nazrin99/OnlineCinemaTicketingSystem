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

public class RemoveMenuItemsController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<MenuItems, String> menudescription;

    @FXML
    private Label menulabel_delete;

    @FXML
    private TableColumn<MenuItems, String> menuname;

    @FXML
    private TextField menuname_delete;

    @FXML
    private TableColumn<MenuItems, Double> menuprice;

    @FXML
    private TableView<MenuItems> menutable;

    @FXML
    private StackPane stackPane;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        menuname_delete.clear();
        menulabel_delete.setText("");
    }

    @FXML
    void deleteMenuItemsButtonClicked(ActionEvent event) {
        String menuname = menuname_delete.getText().trim();
        String menudescription = "";
        double menuprice = 0.0;
        Boolean menu_exists = false;

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM menuitems");

            while(resultSet.next()){
                if(resultSet.getString("menuname").equals(menuname)){
                    menu_exists = true;
                    menudescription = resultSet.getString("menudescription");
                    menuprice = resultSet.getDouble("menuprice");
                }
            }
            statement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(menu_exists){
            menulabel_delete.setText("");
            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM menuitems WHERE menuname = '"+menuname+"';");
                preparedStatement.execute();

                int remove = menuItemsObservableList.indexOf(new MenuItems(menuname, menudescription, menuprice))+1;
                menuItemsObservableList.remove(remove);

                preparedStatement.close();
                connection.close();
            } catch(Exception e){
                System.err.println();
                System.err.println(e.getMessage());
            }
        }
        else{
            menulabel_delete.setText("* Menu Item DOES NOT EXIST! *");
        }

    }

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StaffInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Staff Interface");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    void refreshTableButtonClicked(ActionEvent event) throws InterruptedException{
        stackPane.setVisible(false);
        stackPane.setManaged(false);
        TimeUnit.SECONDS.sleep(1);
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
