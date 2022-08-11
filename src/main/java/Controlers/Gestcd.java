package Controlers;

import Dao.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CD;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class Gestcd {

    //table of CD searched
    @FXML
    private TableView<CD> tablecd;
    @FXML
    private TableColumn<CD, Number> numCdt;
    @FXML
    private TableColumn<CD, String> nomAlbumt;
    @FXML
    private TableColumn<CD, String> nomInterpretet;
    @FXML
    private TableColumn<CD, String> nomEditeurt;

    //fieled input
    @FXML
    private TextField nomAlbum;
    @FXML
    private TextField nomInterprete;
    @FXML
    private TextField nomEditeur;
    @FXML
    private TextField titretosearch;
    @FXML
    private TextField numtosup;

    //button used
    @FXML
    private Button afficherCD;
    @FXML
    public Button retour;

    CDDao cdDao= new CDDaoImpliment();

    //CD Add function
    public void onclickAjouterCD() {
        if(nomAlbum.getText().isEmpty()){
            //test album's name
            JOptionPane.showMessageDialog(null,"Enter le nomAlbum");
        }else if(nomInterprete.getText().isEmpty()){
            //test interpreter's name
            JOptionPane.showMessageDialog(null,"Enter le nomInterprete");
        }else if(nomEditeur.getText().isEmpty()){
            //test Editor's name
            JOptionPane.showMessageDialog(null,"Enter le nomEditeur");
        }else{
            //creat new CD and save it using CDDao.save Function
            CD cd = new CD(0,this.nomAlbum.getText(), this.nomInterprete.getText(), this.nomEditeur.getText());
            cdDao.save(cd);
            JOptionPane.showMessageDialog(null,"CD est ajouté");
        }
        //reset input fields
        nomAlbum.setText("");
        nomInterprete.setText("");
        nomEditeur.setText("");
    }

    //Show All Cds Exist in a new scene
    public void onclickAfficherCd() {
        Stage stage=(Stage) afficherCD.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("Affichercd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //delete CD using his ID
    public void onclickSupprimerCD() {
        if(!numtosup.getText().matches("[1-9]\\d{0,3}")){
            //test CD'ID matching
            JOptionPane.showMessageDialog(null,"Entre un chiffre reel");
        }else if(numtosup.getText().isEmpty()){
            //test CD'ID field
            JOptionPane.showMessageDialog(null,"Enter le Numero de CD");
        }else if(cdDao.findBynum(parseInt(numtosup.getText()))==null){
            //test CD'ID existing
            JOptionPane.showMessageDialog(null,"Cette Numero de CD n'est pas liee a un CD!");
        }else{
            //delete CD using CDDao.deleteByNumeroCd Function
            CDDao dvdDao1= new CDDaoImpliment();
            dvdDao1.deleteByNumeroCd(parseInt(numtosup.getText()));
            JOptionPane.showMessageDialog(null,"CD est supprimer");
        }
        //reset input field
        numtosup.setText("");
    }

    //creat an observable list to fill it with CDs
    public ObservableList<CD> data= FXCollections.observableArrayList();

    //Show CD using his title
    public void onclickAfficherunCD() {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM cd where nomAlbum= '"+titretosearch.getText()+"'";//return searched CD
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with the CD
                data.add(new CD(rs.getInt("numCd"),rs.getString("nomAlbum"),
                        rs.getNString("nomInterprete"), rs.getNString("nomEditeur")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with CD's informations
        numCdt.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getNumCd()));
        nomAlbumt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomAlbum()));
        nomInterpretet.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomInterprete()));
        nomEditeurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomEditeur()));
        tablecd.setItems(data);
        titretosearch.setText("");
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("systeme.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }
}






