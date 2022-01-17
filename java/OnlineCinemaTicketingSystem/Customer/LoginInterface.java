package OnlineCinemaTicketingSystem.Customer;

import java.sql.*;

public class LoginInterface {
    public static void Customer(String username, String password_cust) {
        String db_username, db_password_cust;

        String sql_statement = "SELECT * FROM userlogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_username = rs.getString("Username");
                db_password_cust = rs.getString("Password");
                if(db_password_cust.equals(password_cust) && db_username.equals(username)){
                    System.out.println("\nLogin successful, YAY!!!!");
                }
            }
            statement.close();
            connection.close();

        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
    }
    public static void Customer_throughUsername(String username, String password_cust) {
        String db_username, db_password_cust;

        String sql_statement = "SELECT * FROM userlogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_username = rs.getString("Username");
                db_password_cust = rs.getString("Password");
                if(db_password_cust.equals(password_cust) && db_username.equals(username)){
                    System.out.println("\nLogin successful, YAY!!!!");
                }
            }
            statement.close();
            connection.close();

        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
    }
    public static void customerRegistration(String username_signup, String phoneNumber_signup,String password_signup, String email_signup){
        String sql_statement;

        sql_statement = "INSERT INTO userlogin(Username, PhoneNumber, Email, Password) VALUES('"+ username_signup +"','"+phoneNumber_signup+"','"+ email_signup +"','"+ password_signup +"')";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement statement = connection.prepareStatement(sql_statement);
            statement.execute();

            connection.close();
        } catch(Exception e){
            System.err.println("SQL Problem");
            System.err.println(e.getMessage());
        }
        System.out.println("Registration successful!!");
    }
    public static boolean Staff(int staff_ID, String password_staff){
        String db_password_staff, sql_statement;
        int db_staffID;

        sql_statement = "SELECT * FROM stafflogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_staffID = rs.getInt("staffID");
                db_password_staff = rs.getString("Password");
                if(db_staffID == staff_ID && db_password_staff.equals(password_staff)){
                    return true;
                }
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
        return false;
    }
    public static boolean sameEmail(String email_signup, int index){
        String db_email, sql_statement = "";

        switch(index){
            case 0:
                sql_statement = "SELECT * FROM userlogin";

                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(sql_statement);
                    while(rs.next()){
                        db_email = rs.getString("Email");
                        if(db_email.equals(email_signup)){
                            return true;
                        }
                    }
                    statement.close();
                    connection.close();

                }catch (Exception e){
                    System.err.println("SQL Error");
                    System.err.println(e.getMessage());
                }
                return false;

            case 1:
                sql_statement = "SELECT * FROM stafflogin";

                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(sql_statement);
                    while(rs.next()){
                        db_email = rs.getString("Email");
                        if(db_email.equals(email_signup)){
                            return true;
                        }
                    }
                    statement.close();
                    connection.close();

                }catch (Exception e){
                    System.err.println("SQL Error");
                    System.err.println(e.getMessage());
                }
                return false;
        }
        return false;
    }
    public static boolean sameUsername(String username_signup){
        String db_username;

        String sql_statement = "SELECT * FROM userlogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_username = rs.getString("Username");
                if(db_username.equals(username_signup)){
                    return true;
                }
            }
            statement.close();
            connection.close();

        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
        return false;
    }
    public static boolean sameStaffID(String staffID_signup){
        int db_staffID;

        String sql_statement = "SELECT * FROM stafflogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_staffID = rs.getInt("staffID");
                if(db_staffID == Integer.parseInt(staffID_signup)){
                    return true;
                }
            }
            statement.close();
            connection.close();

        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
        return false;
    }
    public static boolean samePassword(String password_signup){
        String db_password;

        String sql_statement = "SELECT * FROM userlogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_password = rs.getString("Password");
                if(db_password.equals(password_signup)){
                    return true;
                }
            }
            statement.close();
            connection.close();

        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
        return false;
    }
    public static boolean samePhoneNumber(String phoneNumber_signup){
        String db_phoneNumber;

        String sql_statement = "SELECT * FROM userlogin";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql_statement);
            while(rs.next()){
                db_phoneNumber = rs.getString("PhoneNumber");
                if(db_phoneNumber.equals(phoneNumber_signup)){
                    return true;
                }
            }
            statement.close();
            connection.close();

        }catch (Exception e){
            System.err.println("SQL Error");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static void staffRegistration(String staffID_signup, String fullname_signup, String email_signup, String password_signup){

        String sql_statement = "INSERT INTO stafflogin(staffID, FullName, Email, Password) VALUES("+Integer.parseInt(staffID_signup)+",'"+fullname_signup+"','"+ email_signup +"','"+ password_signup +"')";

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment fop", "root", "FOPAssignment");
            PreparedStatement statement = connection.prepareStatement(sql_statement);
            statement.execute();

            connection.close();
        } catch(Exception e){
            System.err.println("SQL Problem");
            System.err.println(e.getMessage());
        }

    }


}
