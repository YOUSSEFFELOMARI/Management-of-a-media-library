package Controlers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class sysControler {
    public Button GestionCD;
    public Button GestionSanction;
    @FXML
    private Button GestionDVD;
    @FXML
    private Button GestionDdherent;
    @FXML
    private Button retour;

    public void onclosesys(){
        Platform.exit();
    }

    public void onclickgestadherent() {
        Stage stage=(Stage) GestionDdherent.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestadherent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    public void onclickgestlivre() {
        Stage stage=(Stage) GestionDdherent.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestlivre.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    public void onclickgestdvd() {
        Stage stage = (Stage) GestionDVD.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestdvd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }

    public void onclickgestcd() {
        Stage stage = (Stage) GestionDVD.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestcd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }

    public void onclickgestEmpruntRendre() {
        Stage stage=(Stage) GestionDdherent.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestemprt.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    public void onclickgestSanction() {
        Stage stage = (Stage) GestionSanction.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestsanct.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }

    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("App-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }
}
