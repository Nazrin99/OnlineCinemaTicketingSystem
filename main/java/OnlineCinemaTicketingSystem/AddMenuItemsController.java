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
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class AddMenuItemsController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<MenuItems, String> menudescription;

    @FXML
    private TextArea menudescription_input;

    @FXML
    private TableColumn<MenuItems, String> menuname;

    @FXML
    private TextField menuname_input;

    @FXML
    private TableColumn<MenuItems, String> menuprice;

    @FXML
    private TextField menuprice_input;

    @FXML
    private TableView<MenuItems> menutable;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label menulabel;

    @FXML
    void clearFormButtonClicked(ActionEvent event) {
        menuprice_input.clear();
        menudescription_input.clear();
        menuname_input.clear();
    }

    @FXML
    void addMenuItemsButtonClicked(ActionEvent event) {
        String menuname = menuname_input.getText().trim();
        String menudescription = menudescription_input.getText().trim(), menudescription_db = "";
        double menuprice = Double.parseDouble(menuprice_input.getText().trim()), menuprice_db = 0.0;
        Boolean menuexists = false;

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM menuitems");

            while(resultSet.next()){
                if(resultSet.getString("menuname").equalsIgnoreCase(menuname)){
                    menuexists = true;
                    menudescription_db = resultSet.getString("menudescription");
                    menuprice_db = resultSet.getDouble("menuprice");
                }
            }
        } catch(Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(menuexists){
            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE menuitems SET menudescription = '"+menudescription+"', menuprice = '"+menuprice+"';");
                preparedStatement.execute();

                int remove = menuItemsObservableList.indexOf(new MenuItems(menuname, menudescription_db, menuprice_db))+1;
                menuItemsObservableList.remove(remove);
                menuItemsObservableList.add(new MenuItems(menuname, menudescription, menuprice));
                menulabel.setText("* Menu items already exists, updating details instead *");

                preparedStatement.close();
                connection.close();
            } catch(Exception e){
                System.err.println();
                System.err.println(e.getMessage());
            }
        }
        else{
            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO menuitems(menuname, menudescription, menuprice)" +
                        "VALUES('"+menuname+"','"+menudescription+"',"+menuprice+")");
                preparedStatement.execute();

                menuItemsObservableList.add(new MenuItems(menuname, menudescription, menuprice));
                menulabel.setText("");

                preparedStatement.close();
                connection.close();
            } catch(Exception e){
                System.err.println();
                System.err.println(e.getMessage());
            }
        }
    }

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StaffInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setMaximized(true);
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
