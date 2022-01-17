package OnlineCinemaTicketingSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class PickSeatsController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private ImageView ScreenImage = new ImageView("C:\\Users\\HUAWEI\\IdeaProjects\\Testing\\src\\main\\resources\\OnlineCinemaTicketingSystem\\Screen.png");

    @FXML
    private ToggleButton A0 = new ToggleButton();

    @FXML
    private ToggleButton A1 = new ToggleButton();

    @FXML
    private ToggleButton A2 = new ToggleButton();

    @FXML
    private ToggleButton A3 = new ToggleButton();

    @FXML
    private ToggleButton A4 = new ToggleButton();

    @FXML
    private ToggleButton A5 = new ToggleButton();

    @FXML
    private ToggleButton A6 = new ToggleButton();

    @FXML
    private ToggleButton A7 = new ToggleButton();

    @FXML
    private ToggleButton A8 = new ToggleButton();

    @FXML
    private ToggleButton A9 = new ToggleButton();

    @FXML
    private ToggleButton B0 = new ToggleButton();

    @FXML
    private ToggleButton B1 = new ToggleButton();

    @FXML
    private ToggleButton B2 = new ToggleButton();

    @FXML
    private ToggleButton B3 = new ToggleButton();

    @FXML
    private ToggleButton B4 = new ToggleButton();

    @FXML
    private ToggleButton B5 = new ToggleButton();

    @FXML
    private ToggleButton B6 = new ToggleButton();

    @FXML
    private ToggleButton B7 = new ToggleButton();

    @FXML
    private ToggleButton B8 = new ToggleButton();

    @FXML
    private ToggleButton B9 = new ToggleButton();

    @FXML
    private ToggleButton C0 = new ToggleButton();

    @FXML
    private ToggleButton C1 = new ToggleButton();

    @FXML
    private ToggleButton C2 = new ToggleButton();

    @FXML
    private ToggleButton C3 = new ToggleButton();

    @FXML
    private ToggleButton C4 = new ToggleButton();

    @FXML
    private ToggleButton C5 = new ToggleButton();

    @FXML
    private ToggleButton C6 = new ToggleButton();

    @FXML
    private ToggleButton C7 = new ToggleButton();

    @FXML
    private ToggleButton C8 = new ToggleButton();

    @FXML
    private ToggleButton C9 = new ToggleButton();

    @FXML
    private ToggleButton D0 = new ToggleButton();

    @FXML
    private ToggleButton D1 = new ToggleButton();

    @FXML
    private ToggleButton D2 = new ToggleButton();

    @FXML
    private ToggleButton D3 = new ToggleButton();

    @FXML
    private ToggleButton D4 = new ToggleButton();

    @FXML
    private ToggleButton D5 = new ToggleButton();

    @FXML
    private ToggleButton D6 = new ToggleButton();

    @FXML
    private ToggleButton D7 = new ToggleButton();

    @FXML
    private ToggleButton D8 = new ToggleButton();

    @FXML
    private ToggleButton D9 = new ToggleButton();

    @FXML
    private ToggleButton E0 = new ToggleButton();

    @FXML
    private ToggleButton E1 = new ToggleButton();

    @FXML
    private ToggleButton E2 = new ToggleButton();

    @FXML
    private ToggleButton E3 = new ToggleButton();

    @FXML
    private ToggleButton E4 = new ToggleButton();

    @FXML
    private ToggleButton E5 = new ToggleButton();

    @FXML
    private ToggleButton E6 = new ToggleButton();

    @FXML
    private ToggleButton E7 = new ToggleButton();

    @FXML
    private ToggleButton E8 = new ToggleButton();

    @FXML
    private ToggleButton E9 = new ToggleButton();

    @FXML
    private GridPane allButtons = new GridPane();

    @FXML
    private Button confirmSeatButton;

    @FXML
    private ToggleButton wantsFandB;

    @FXML
    ToggleButton[][] toggleButtons = new ToggleButton[5][10];

    String seatsSelected = "";
    public int numberofseats = 0;

    @FXML
    void goBackButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("BookMovieInterface.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Showtimes Window");
        stage.show();
    }

    String seat = "";

    @FXML
    void seatButtonClicked(ActionEvent event){
            if (((ToggleButton) event.getSource()).isSelected()) {
                ToggleButton seatSelected = (ToggleButton) event.getSource();
                seat = seatSelected.getId().toString();
                seatsSelected += seat;
                numberofseats--;
                System.out.println(numberofseats);
                System.out.println(seatsSelected);
                SOPChecker(seat);
            }
            else{
                ToggleButton seatRemoved = (ToggleButton) event.getSource();
                seat = seatRemoved.getId().toString();
                seatsSelected = seatsSelected.replaceAll(seat,"");
                numberofseats++;
                System.out.println(numberofseats);
                System.out.println(seatsSelected);
            }
            if(numberofseats <= 0){
                allButtons.setDisable(true);
                confirmSeatButton.setDisable(false);
            }
            else{
                confirmSeatButton.setDisable(true);
            }
    }

    @FXML
    void SOPChecker(String seathighlighted){
        int rowIndex = getRow(seathighlighted), columnIndex= getColumn(seathighlighted);
        int nextcol = columnIndex+1, previouscol= columnIndex-1;
        if(columnIndex == 0 && toggleButtons[rowIndex][nextcol].isSelected()){
            toggleButtons[rowIndex][nextcol+1].setDisable(true);
        }
        else if(toggleButtons[rowIndex][previouscol].isSelected() && columnIndex == 9){
            toggleButtons[rowIndex][previouscol-1].setDisable(true);
        }
        else if(toggleButtons[rowIndex][previouscol].isSelected() && previouscol != 0 ){
            toggleButtons[rowIndex][previouscol-1].setDisable(true);
            toggleButtons[rowIndex][nextcol].setDisable(true);
        }
        else if(toggleButtons[rowIndex][nextcol].isSelected() && nextcol != 9){
            toggleButtons[rowIndex][nextcol+1].setDisable(true);
            toggleButtons[rowIndex][previouscol].setDisable(true);
        }
        else if(toggleButtons[rowIndex][nextcol].isSelected() && nextcol == 9){
            toggleButtons[rowIndex][previouscol].setDisable(true);
        }
        else{

        }
    }

    @FXML
    void confirmSeatButtonClicked(ActionEvent event) throws IOException{
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE currentloggeduser SET seatschosen = '"+seatsSelected+"';");
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        if(wantsFandB.isSelected()){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FandBInterface.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("F&B Window");
            stage.show();
        }
        else{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PaymentInterface.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Payment Window");
            stage.show();
        }
    }

    @FXML
    void repickButtonClicked(ActionEvent event){
        allButtons.setDisable(false);
        confirmSeatButton.setDisable(true);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                toggleButtons[i][j].setDisable(false);
            }
        }
        initializer();
        numberofseats = 0;
    }

    String seatsBooked = "";

    @FXML
    void initializer(){
        confirmSeatButton.setDisable(true);
        String moviename = "", date = "", timestart = "", timeend = "";
        int hall = 0;
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currentloggeduser");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                moviename = resultSet.getString("moviename");
                date = resultSet.getString("date");
                timestart = resultSet.getString("timestart");
                timeend = resultSet.getString("timeend");
                hall = resultSet.getInt("hall");
                numberofseats += (resultSet.getInt("ticket_r_num") + resultSet.getInt("ticket_s_num") + resultSet.getInt("ticket_p_num"));
            }
            preparedStatement.close();
            resultSet.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM moviesbookedseats WHERE moviename = '"+moviename+"' AND hall = "+hall+" AND `date` = '"+date+"' AND timestart = '"+timestart+"' AND timeend = '"+timeend+"';");
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while(resultSet1.next()){
                seatsBooked = resultSet1.getString("seatsbooked");
            }
            preparedStatement1.close();
            resultSet1.close();
            connection.close();
        } catch (Exception e){
            System.err.println();
            System.err.println();
        }
        allButtons.setDisable(false);
        for(int i = 0; i < seatsBooked.length()/2;i++){
            String removeSeats = seatsBooked.substring(2*i,2*(i+1));
            toggleButtons[getRow(removeSeats)][getColumn(removeSeats)].setText("BOOKED");
            toggleButtons[getRow(removeSeats)][getColumn(removeSeats)].setDisable(true);
        }
    }


    @FXML
    int getRow(String seat){
        switch(seat.charAt(0)){
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
        }
        return 0;
    }

    @FXML
    int getColumn(String seat){
        return Integer.parseInt(String.valueOf(seat.charAt(1)));
    }


    @FXML
    void toggleButtonsArray(){
        toggleButtons[0][0] = A0; toggleButtons[0][1] = A1; toggleButtons[0][2] = A2; toggleButtons[0][3] = A3; toggleButtons[0][4] = A4;
        toggleButtons[0][5] = A5; toggleButtons[0][6] = A6; toggleButtons[0][7] = A7; toggleButtons[0][8] = A8; toggleButtons[0][9] = A9;
        toggleButtons[1][0] = B0; toggleButtons[1][1] = B1; toggleButtons[1][2] = B2; toggleButtons[1][3] = B3; toggleButtons[1][4] = B4;
        toggleButtons[1][5] = B5; toggleButtons[1][6] = B6; toggleButtons[1][7] = B7; toggleButtons[1][8] = B8; toggleButtons[1][9] = B9;
        toggleButtons[2][0] = C0; toggleButtons[2][1] = C1; toggleButtons[2][2] = C2; toggleButtons[2][3] = C3; toggleButtons[2][4] = C4;
        toggleButtons[2][5] = C5; toggleButtons[2][6] = C6; toggleButtons[2][7] = C7; toggleButtons[2][8] = C8; toggleButtons[2][9] = C9;
        toggleButtons[3][0] = D0; toggleButtons[3][1] = D1; toggleButtons[3][2] = D2; toggleButtons[3][3] = D3; toggleButtons[3][4] = D4;
        toggleButtons[3][5] = D5; toggleButtons[3][6] = D6; toggleButtons[3][7] = D7; toggleButtons[3][8] = D8; toggleButtons[3][9] = D9;
        toggleButtons[4][0] = E0; toggleButtons[4][1] = E1; toggleButtons[4][2] = E2; toggleButtons[4][3] = E3; toggleButtons[4][4] = E4;
        toggleButtons[4][5] = E5; toggleButtons[4][6] = E6; toggleButtons[4][7] = E7; toggleButtons[4][8] = E8; toggleButtons[4][9] = E9;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleButtonsArray();
        initializer();

    }


}
