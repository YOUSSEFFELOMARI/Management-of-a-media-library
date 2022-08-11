package Controlers;

import Dao.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class login implements Initializable {
    public Text textpswd;
    public Button exit;
    @FXML
    private Button loginbt;
    @FXML
    private TextField name;
    @FXML
    private TextField password;

    @FXML
    private void onloginButtonClick() {

        //test field input
        if(name.getText().isEmpty()){
            name.setPromptText("entrer le nom");
        }
        if(name.getText().matches("[a-zA-Z0-9_](4,)")){
            return;
        }
        if(name.getText().isEmpty()){
            textpswd.setText("entrer le Nom");
            return;
        }
        if(password.getText().isEmpty()){
            textpswd.setText("entrer le Password");
            return;
        }

        int status =DBConnection.checkConnectionlogin(name.getText().trim().toLowerCase(),password.getText());//login and remove space between characters in the name field

        switch (status) {
            case 0 -> {
                Stage stage = (Stage) loginbt.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("systeme.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setResizable(false);
                stage.setScene(new Scene(root));
            }
            case -1 -> {
                JOptionPane.showMessageDialog(null, "Connection fieled");
            }
            case 1 -> {
                JOptionPane.showMessageDialog(null, "name or password wrong");
            }
        }
    }

    @FXML
    private void onexitButtonclick() {
        Platform.exit();
    }

    //Terminate the membership of people who have exceeded one year without paying their sanctions each time to administer it when the employer login
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentdate=LocalDate.now();

        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT a.id ,SUM(a.date_difference) AS date_difference " +
                    "FROM (SELECT adherent.id, DATEDIFF( ?, emprunte.datelimite) AS date_difference " +
                    "FROM emprunte, adherent WHERE emprunte.idAdh = adherent.id AND emprunte.datelimite < ? ) AS a " +
                    "GROUP BY a.id HAVING date_difference >365";// return adherent who passed more than one yeaer without paying thier fine
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(currentdate));
            preparedStatement.setString(2, String.valueOf(currentdate));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int b= rs.getInt(1);
                String query1 = "DELETE FROM adherent WHERE id="+b;//delete adherent from databese
                PreparedStatement preparedStatement1 = conn.prepareStatement(query1);
                int rs1 = preparedStatement1.executeUpdate();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}