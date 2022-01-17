package OnlineCinemaTicketingSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    String moviename ="", date = "", timestart = "", timeend = "", duration = "", seatbooked = "", username= "";
    int ticket_r_num = 0, ticket_p_num = 0, ticket_s_num = 0, hall = 0;
    double fandbtotalprice = 0.0, totalprice = 0.0, ticket_r_price = 0.0, ticket_p_price = 0.0, ticket_s_price = 0.0;

    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    private Label date_invoice;

    @FXML
    private Label duration_invoice;

    @FXML
    private Label fandb_invoice;

    @FXML
    private Label hall_invoice;

    @FXML
    private Label moviename_invoice;

    @FXML
    private Label seatBooked_invoice;

    @FXML
    private Label ticket_p_num_invoice;

    @FXML
    private Label ticket_r_num_invoice;

    @FXML
    private Label ticket_s_num_invoice;

    @FXML
    private Label timeend_invoice;

    @FXML
    private Label timestart_invoice;

    @FXML
    private Label totalprice_invoice;

    @FXML
    private Label payment_label;

    @FXML
    private TextField payment_invoice;

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("PickSeatsInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Pick Seats Window");
        stage.show();
    }

    @FXML
    void paymentButtonClicked(ActionEvent event) throws IOException{
        String seatsBooked_db = "";
        if(Double.parseDouble(payment_invoice.getText().trim()) < totalprice){
            payment_label.setText("* INSUFFICIENT PAYMENT! *");
        }
        else{
            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userbookinghistory(username, moviename, date, hall, timestart, timeend, duration, bookedseats, ticketprice_p,ticketprice_r,ticketprice_s, ticket_r_num,ticket_s_num,ticket_p_num, `f&btotalprice`, totalprice)" +
                        "VALUES('"+username+"', '"+moviename+"','"+date+"',"+hall+", '"+timestart+"','"+timeend+"','"+duration+"', '"+seatbooked+"','"+ticket_p_price+"','"+ticket_r_price+"','"+ticket_s_price+"',"+ticket_r_num+","+ticket_s_num+","+ticket_p_num+", "+fandbtotalprice+","+totalprice+");");
                preparedStatement.execute();
                preparedStatement.close();

                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM moviesbookedseats WHERE moviename = '"+moviename+"' AND   `date` = '"+date+"' AND hall ="+hall+" AND timestart = '"+timestart+"' AND timeend = '"+timeend+"';");
                ResultSet resultSet = preparedStatement1.executeQuery();
                while(resultSet.next()){
                    seatsBooked_db = resultSet.getString("seatsbooked");
                    seatbooked += seatsBooked_db;
                }
                preparedStatement1.close();
                resultSet.close();

                PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE moviesbookedseats SET seatsbooked = '"+seatbooked+"' WHERE moviename = '"+moviename+"' AND   `date` = '"+date+"' AND hall ="+hall+" AND timestart = '"+timestart+"' AND timeend = '"+timeend+"';");
                preparedStatement2.execute();
                preparedStatement2.close();
                connection.close();
            } catch(Exception e){
                System.err.println();
                System.err.println(e.getMessage());
            }

            root = FXMLLoader.load(getClass().getResource("CustomerInterface.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Customer Main Page");
            stage.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currentloggeduser");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                username = resultSet.getString("username");
                moviename = resultSet.getString("moviename");
                date = resultSet.getString("date");
                hall = resultSet.getInt("hall");
                timestart = resultSet.getString("timestart");
                timeend = resultSet.getString("timeend");
                seatbooked = resultSet.getString("seatschosen");
                fandbtotalprice = resultSet.getDouble("fandbtotalprice");
                ticket_r_num = resultSet.getInt("ticket_r_num");
                ticket_s_num = resultSet.getInt("ticket_s_num");
                ticket_p_num = resultSet.getInt("ticket_p_num");

            }
            preparedStatement.close();
            resultSet.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM moviesticketprice WHERE moviename = '"+moviename+"';");
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while(resultSet1.next()){
                ticket_p_price = resultSet1.getDouble("ticketprice_p");
                ticket_s_price = resultSet1.getDouble("ticketprice_s");
                ticket_r_price = resultSet1.getDouble("ticketprice_r");
                System.out.println(ticket_p_price);
            }
            preparedStatement1.close();
            resultSet1.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        totalprice = fandbtotalprice + (ticket_p_price*ticket_p_num) + (ticket_r_price*ticket_r_num) + (ticket_s_price*ticket_s_num);
        MoviesShowtime moviesShowtime = new MoviesShowtime();
        moviename_invoice.setText("Movie Name : " + moviename);
        date_invoice.setText("Movie Date : " + date);
        hall_invoice.setText("Hall : " + Integer.toString(hall));
        timestart_invoice.setText("Time Start : " + timestart);
        timeend_invoice.setText("Time End : " + timeend);
        duration = moviesShowtime.durationCalculator(timestart, timeend);
        duration_invoice.setText("Duration : " + duration);
        seatBooked_invoice.setText("Seats Chosen : " + seatbooked);
        ticket_p_num_invoice.setText("Premium Ticket : x" + Integer.toString(ticket_p_num));
        ticket_r_num_invoice.setText("Regular Ticket : x" + Integer.toString(ticket_r_num));
        ticket_s_num_invoice.setText("Student Ticket : x" + Integer.toString(ticket_s_num));
        fandb_invoice.setText("Total F&B price : RM" + Double.toString(fandbtotalprice));
        totalprice_invoice.setText("TOTAL PRICE : RM" + Double.toString(totalprice));

    }
}
