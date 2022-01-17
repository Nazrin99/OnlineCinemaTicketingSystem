package OnlineCinemaTicketingSystem;

import OnlineCinemaTicketingSystem.Customer.LoginInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private ImageView imageView = new ImageView("C:\\Users\\HUAWEI\\IdeaProjects\\Testing\\src\\main\\resources\\OnlineCinemaTicketingSystem\\stylebutton.css");
    
    @FXML
    private Label signinLabel_un;

    @FXML
    private Label signinLabel_pn;

    @FXML
    private Label signinLabel_em;

    @FXML
    private TextField staffID_input;
    
    @FXML
    private TextField password_staff;

    @FXML
    private Label staffSigninLabel;
    
    @FXML
    private Label staffLabel;

    @FXML
    private Label signupLabel;

    @FXML
    private Label signupLabel_staff;


    @FXML
    private HBox twobuttons;
    
    @FXML
    private HBox twobuttons2;

    @FXML
    private VBox staffsigninVBox;

    @FXML
    private VBox staffSignupForm;

    @FXML
    private VBox gobackVBox;

    @FXML
    private VBox staffSigninForm;
    

    @FXML
    private VBox SignUpForm;

    @FXML
    private VBox signinByPhoneNumber;

    @FXML
    private VBox signinByEmail;

    @FXML
    private VBox signinByUsername;

    @FXML
    private Button staffSigninPageButton;

    @FXML
    private TextField email_signup;

    @FXML
    private TextField email_signin;

    @FXML
    private TextField phoneNumber_signin;

    @FXML
    private PasswordField password_signin_un;

    @FXML
    private TextField fullName_signup_staff;

    @FXML
    private TextField staffID_signup;

    @FXML
    private TextField email_signup_staff;

    @FXML
    private PasswordField password_signin_pn;

    @FXML
    private PasswordField password_signup_staff;

    @FXML
    private PasswordField password_signin_em;

    @FXML
    private PasswordField password_signup;

    @FXML
    private TextField username_signin;

    @FXML
    private TextField username_signup;

    @FXML
    private TextField phoneNumber_signup;

    @FXML
    void signinButtonClicked_un(ActionEvent event) throws IOException {
        Boolean invalid_username = !(LoginInterface.sameUsername(username_signin.getText().trim()));
        Boolean invalid_password = !(LoginInterface.samePassword(password_signin_un.getText().trim()));
        if(invalid_username || invalid_password){
            signinLabel_un.setText("* Invalid login credentials *");
        }
        else{
            String username = username_signin.getText().trim();

            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO currentloggeduser(username) VALUES('"+username+"');");
                preparedStatement1.execute();
                preparedStatement1.close();
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
            stage.setTitle("GCS Login Page");
            stage.show();
        }
    }
    @FXML
    void signinButtonClicked_pn(ActionEvent event) throws IOException{
        Boolean invalid_phoneNumber = !(LoginInterface.samePhoneNumber(phoneNumber_signin.getText().trim()));
        Boolean invalid_password = !(LoginInterface.samePassword(password_signin_pn.getText().trim()));
        if(invalid_phoneNumber || invalid_password){
            signinLabel_pn.setText("* Invalid login credentials *");
        }
        else{
            String phonenumber = phoneNumber_signin.getText().trim();
            String username = "";

            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userlogin WHERE PhoneNumber = '"+phonenumber+"';");
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    username = resultSet.getString("Username");
                }
                preparedStatement.close();
                resultSet.close();

                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO currentloggeduser(username) VALUES('"+username+"');");
                preparedStatement1.execute();
                preparedStatement1.close();
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
            stage.setTitle("GCS Login Page");
            stage.show();
        }
    }
    @FXML
    void signinButtonClicked_em(ActionEvent event) throws IOException{
        Boolean invalid_email = !(LoginInterface.sameEmail(email_signin.getText().trim(),0));
        Boolean invalid_password = !(LoginInterface.samePassword(password_signin_em.getText().trim()));
        if(invalid_email || invalid_password){
            signinLabel_em.setText("* Invalid login credentials *");
        }
        else{
            String email = email_signin.getText().trim();
            String username = "";

            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userlogin WHERE Email = '"+email+"'");
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    username = resultSet.getString("Username");
                }
                preparedStatement.close();
                resultSet.close();

                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO currentloggeduser(username) VALUES('"+username+"');");
                preparedStatement1.execute();
                preparedStatement1.close();
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
            stage.setTitle("GCS Login Page");
            stage.show();
        }
    }


    @FXML
    void signinByUsernameClicked_em(ActionEvent event){
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        signinByUsername.setVisible(true);
        signinByUsername.setManaged(true);
        signinLabel_em.setText("");
        signinLabel_pn.setText("");
        phoneNumber_signin.clear();
        email_signin.clear();
    }

    @FXML
    void signinByUsernameClicked_pn(ActionEvent event){
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        signinByUsername.setVisible(true);
        signinByUsername.setManaged(true);
        signinLabel_em.setText("");
        signinLabel_pn.setText("");
        phoneNumber_signin.clear();
        email_signin.clear();
    }

    @FXML
    void signinByPhoneNumberClicked_em(ActionEvent event){
        signinByPhoneNumber.setVisible(true);
        signinByPhoneNumber.setManaged(true);
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        signinByUsername.setVisible(false);
        signinByUsername.setManaged(false);
        signinLabel_em.setText("");
        signinLabel_un.setText("");
        email_signin.clear();
        username_signin.clear();
    }

    @FXML
    void signinByPhoneNumberClicked_un(ActionEvent event){
        signinByPhoneNumber.setVisible(true);
        signinByPhoneNumber.setManaged(true);
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        signinByUsername.setVisible(false);
        signinByUsername.setManaged(false);
        signinLabel_em.setText("");
        signinLabel_un.setText("");
        email_signin.clear();
        username_signin.clear();

    }

    @FXML
    void signinByEmailClicked_un(ActionEvent event) {
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        signinByEmail.setVisible(true);
        signinByEmail.setManaged(true);
        signinByUsername.setVisible(false);
        signinByUsername.setManaged(false);
        signinLabel_pn.setText("");
        signinLabel_un.setText("");
        username_signin.clear();
        phoneNumber_signin.clear();
    }
    @FXML
    void signinByEmailClicked_pn(ActionEvent event) {
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        signinByEmail.setVisible(true);
        signinByEmail.setManaged(true);
        signinByUsername.setVisible(false);
        signinByUsername.setManaged(false);
        signinLabel_pn.setText("");
        signinLabel_un.setText("");
        username_signin.clear();
        phoneNumber_signin.clear();
    }



    @FXML
    void signinFormButtonClicked(ActionEvent event) {
        signinByUsername.setManaged(true);
        signinByUsername.setVisible(true);
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        SignUpForm.setVisible(false);
        SignUpForm.setManaged(false);
        staffSigninForm.setVisible(false);
        staffSigninForm.setManaged(false);
        gobackVBox.setVisible(false);
        gobackVBox.setManaged(false);
        staffSigninPageButton.setVisible(true);
        staffSigninPageButton.setManaged(true);
        staffsigninVBox.setVisible(true);
        staffsigninVBox.setManaged(true);
        twobuttons.setVisible(true);
        twobuttons.setManaged(true);
        twobuttons2.setManaged(false);
        twobuttons2.setVisible(false);
        staffSignupForm.setVisible(false);
        staffSignupForm.setManaged(false);
        

        signupLabel.setText("");
        username_signup.clear();
        email_signup.clear();
        phoneNumber_signup.clear();
        password_signup.clear();
    }

    @FXML
    void signupFormButtonClicked(ActionEvent event) {
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        signinByUsername.setVisible(false);
        signinByUsername.setManaged(false);
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        SignUpForm.setVisible(true);
        SignUpForm.setManaged(true);
        staffSigninForm.setVisible(false);
        staffSigninForm.setManaged(false);
        staffSignupForm.setVisible(false);
        staffSignupForm.setManaged(false);
        twobuttons.setVisible(true);
        twobuttons.setManaged(true);
        twobuttons2.setVisible(false);
        twobuttons2.setManaged(false);

    }

    @FXML
    void signupButtonClicked(ActionEvent event) {
        Boolean email_exist = LoginInterface.sameEmail(email_signup.getText().trim().toLowerCase(),0);
        Boolean username_exist = LoginInterface.sameUsername(username_signup.getText().trim());
        Boolean phoneNumber_exist = LoginInterface.samePhoneNumber(phoneNumber_signup.getText().trim());

        if(email_exist || username_exist || phoneNumber_exist){
            signupLabel.setText("* Credentials already exists, please sign in instead *");
        }
        else{
            LoginInterface.customerRegistration(username_signup.getText().trim(),phoneNumber_signup.getText().trim(),password_signup.getText().trim(),email_signup.getText().trim());
        }

    }


    @FXML
    void staffSigninPageButtonClicked(ActionEvent event) {
        signinByEmail.setManaged(false);
        signinByEmail.setVisible(false);
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        signinByUsername.setVisible(false);
        signinByUsername.setManaged(false);
        SignUpForm.setVisible(false);
        SignUpForm.setManaged(false);
        staffSigninForm.setVisible(true);
        staffSigninForm.setManaged(true);
        gobackVBox.setVisible(true);
        gobackVBox.setManaged(true);
        staffsigninVBox.setVisible(false);
        staffsigninVBox.setManaged(false);
        twobuttons.setManaged(false);
        twobuttons.setVisible(false);
        twobuttons2.setVisible(true);
        twobuttons2.setManaged(true);
        staffSignupForm.setVisible(false);
        staffSignupForm.setVisible(false);

    }
    @FXML
    void signinFormButtonClicked_staff(ActionEvent event){
        staffSigninPageButtonClicked(event);
        staffSignupForm.setVisible(false);
        staffSignupForm.setManaged(false);
    }

    @FXML
    void signupFormButtonClicked_staff(ActionEvent event){
        staffSigninForm.setVisible(false);
        staffSigninForm.setManaged(false);
        gobackVBox.setVisible(true);
        gobackVBox.setManaged(true);
        staffsigninVBox.setVisible(false);
        staffsigninVBox.setManaged(false);
        twobuttons.setManaged(false);
        twobuttons.setVisible(false);
        twobuttons2.setVisible(true);
        twobuttons2.setManaged(true);
        staffSignupForm.setVisible(true);
        staffSignupForm.setManaged(true);
    }

    @FXML
    void staffSigninButtonClicked(ActionEvent event) throws IOException {
        Boolean invalid_input = !(LoginInterface.Staff(Integer.parseInt(staffID_input.getText().trim()),password_staff.getText().trim()));
        if(invalid_input){
            staffSigninLabel.setText("* Invalid staff ID or password *");
        }
        else{
            root = FXMLLoader.load(getClass().getResource("StaffInterface.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Staff Main Page");
            stage.show();
        }
    }
    @FXML
    void staffSignupButtonClicked(ActionEvent event) throws IOException{
        Boolean email_exists = LoginInterface.sameEmail(email_signup_staff.getText().trim(),1);
        Boolean staffID_exists = LoginInterface.sameStaffID(staffID_signup.getText().trim());
        if(email_exists || staffID_exists){
            signupLabel_staff.setText("* Login credentials exists, please sign in instead! *");
        }
        else{
            LoginInterface.staffRegistration(staffID_signup.getText().trim(),fullName_signup_staff.getText().trim(),email_signup_staff.getText().trim(),password_signup_staff.getText().trim());
            signupLabel_staff.setText("* Registration successful, please sign in now! *");
        }
    }


    @FXML
    void backbuttonClicked(ActionEvent event){
        signinFormButtonClicked(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM currentloggeduser");
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch(Exception e){
            System.err.println();
            System.err.println(e.getMessage());
        }
        SignUpForm.setVisible(false);
        SignUpForm.setManaged(false);
        staffSigninForm.setVisible(false);
        staffSigninForm.setManaged(false);
        gobackVBox.setVisible(false);
        gobackVBox.setManaged(false);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        signinByPhoneNumber.setVisible(false);
        signinByPhoneNumber.setManaged(false);
        signinByEmail.setVisible(false);
        signinByEmail.setManaged(false);
        staffSignupForm.setVisible(false);
        staffSignupForm.setManaged(false);
    }


}

