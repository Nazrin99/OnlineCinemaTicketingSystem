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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class BookingHistoryController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    private Label[] dateLabel = new Label[2];
    private Label[] durationLabel = new Label[2];
    private Label[] fandbLabel = new Label[2];
    private Label[] hallLabel = new Label[2];
    private Label[] movienameLabel = new Label[2];
    private Label[] seatBookedLabel = new Label[2];
    private Label[] ticket_p_numLabel = new Label[2];
    private Label[] ticket_r_numLabel = new Label[2];
    private Label[] ticket_s_numLabel = new Label[2];
    private Label[] timeendLabel = new Label[2];
    private Label[] timestartLabel = new Label[2];
    private Label[] totalpriceLabel = new Label[2];
    String username = "";
    int i = 0;
    
    @FXML
    private VBox vbox_history1 = new VBox();

    @FXML
    private VBox vbox_history11;

    @FXML
    private Label date_history1;

    @FXML
    private Label date_history11;

    @FXML
    private Label duration_history1;

    @FXML
    private Label duration_history11;

    @FXML
    private Label fandb_history1;

    @FXML
    private Label fandb_history11;

    @FXML
    private Label hall_history1;

    @FXML
    private Label hall_history11;

    @FXML
    private Label moviename_history1;

    @FXML
    private Label moviename_history11;

    @FXML
    private Label seatBooked_history1;

    @FXML
    private Label seatBooked_history11;

    @FXML
    private Label ticket_p_num_history1;

    @FXML
    private Label ticket_p_num_history11;

    @FXML
    private Label ticket_r_num_history1;

    @FXML
    private Label ticket_r_num_history11;

    @FXML
    private Label ticket_s_num_history1;

    @FXML
    private Label ticket_s_num_history11;

    @FXML
    private Label timeend_history1;

    @FXML
    private Label timeend_history11;

    @FXML
    private Label timestart_history1;

    @FXML
    private Label timestart_history11;

    @FXML
    private Label totalprice_history1;

    @FXML
    private Label totalprice_history11;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CustomerInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customer Interface");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    void nextButtonClicked(ActionEvent event) {
        vbox_history1.setVisible(false);
        vbox_history1.setManaged(false);
        vbox_history11.setManaged(true);
        vbox_history11.setVisible(true);
        nextButton.setDisable(true);
        previousButton.setDisable(false);
    }

    @FXML
    void previousButtonClicked(ActionEvent event) {
        vbox_history1.setVisible(true);
        vbox_history1.setManaged(true);
        vbox_history11.setManaged(false);
        vbox_history11.setVisible(false);
        nextButton.setDisable(false);
        previousButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateLabel[0] = date_history1;   dateLabel[1] = date_history11;
        durationLabel[0] = duration_history1;   durationLabel[1] = duration_history11;
        fandbLabel[0] = fandb_history1; fandbLabel[1] = fandb_history11;
        hallLabel[0] = hall_history1;   hallLabel[1] = hall_history11;
        movienameLabel[0] = moviename_history1; movienameLabel[1] = moviename_history11;
        seatBookedLabel[0] = seatBooked_history1;   seatBookedLabel[1] = seatBooked_history11;
        ticket_p_numLabel[0] = ticket_p_num_history1;   ticket_p_numLabel[1] = ticket_p_num_history11;
        ticket_r_numLabel[0] = ticket_r_num_history1;   ticket_r_numLabel[1] = ticket_r_num_history11;
        ticket_s_numLabel[0] = ticket_s_num_history1;   ticket_s_numLabel[1] = ticket_s_num_history11;
        timeendLabel[0] = timeend_history1; timeendLabel[1] = timeend_history11;
        timestartLabel[0] = timestart_history1; timestartLabel[1] = timestart_history11;
        totalpriceLabel[0] = totalprice_history1;   totalpriceLabel[1] = totalprice_history11;

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currentloggeduser");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                username = resultSet.getString("username");
            }
            preparedStatement.close();
            resultSet.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM usersbookinghistory WHERE username = '"+username+"';");
            ResultSet resultSet1 = preparedStatement1.executeQuery();

                    while(resultSet1.next()) {
                        dateLabel[i].setText("Date : " + resultSet1.getString("date"));
                        durationLabel[i].setText("Duration : " + resultSet1.getString("duration"));
                        fandbLabel[i].setText("F&B Total Price : " + resultSet1.getString("f&btotalprice"));
                        hallLabel[i].setText("Hall : " + Integer.toString(resultSet1.getInt("hall")));
                        movienameLabel[i].setText("Movie Name : " + resultSet1.getString("moviename"));
                        seatBookedLabel[i].setText("Seats Booked : " + resultSet1.getString("bookedseats"));
                        ticket_p_numLabel[i].setText("Premium Ticket : x" + Integer.toString(resultSet1.getInt("ticket_p_num")));
                        ticket_r_numLabel[i].setText("Regular Ticket : x" + Integer.toString(resultSet1.getInt("ticket_r_num")));
                        ticket_s_numLabel[i].setText("Student Ticket : x" + Integer.toString(resultSet1.getInt("ticket_s_num")));
                        timeendLabel[i].setText("Time End : " + resultSet1.getString("timeend"));
                        timestartLabel[i].setText("Time Start : " + resultSet1.getString("timestart"));
                        totalpriceLabel[i].setText("TOTAL PRICE : " + Double.toString(resultSet1.getDouble("totalprice")));
                        i++;
                    }
            preparedStatement1.close();
            resultSet1.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        vbox_history1.setVisible(true);
        vbox_history1.setManaged(true);
        vbox_history11.setManaged(false);
        vbox_history11.setVisible(false);
    }

}
