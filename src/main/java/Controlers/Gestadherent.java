package Controlers;

import Dao.AdherentDao;
import Dao.AdherentDaoImpliment;
import Dao.DBConnection;
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
import model.Adherent;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Gestadherent {

    //fieled input
    @FXML
    private TextField lastnamefld;
    @FXML
    private TextField namefld;
    @FXML
    private TextField adresse;
    @FXML
    private TextField nomitosearch;
    @FXML
    private TextField idtosupp;

    //button used
    @FXML
    private Button affichalladher;
    @FXML
    private Button retour;

    //table of DVD searched
    @FXML
    private TableView<Adherent> tableAdherent;
    @FXML
    private TableColumn<Adherent, Number> id;
    @FXML
    private TableColumn<Adherent, String> name;
    @FXML
    private TableColumn<Adherent, String> lastname;
    @FXML
    private TableColumn<Adherent, String> adresse1;


    AdherentDao adherentdao= new AdherentDaoImpliment();

    //add Adherent function
    public void onclickAjouterAdh() {
        //test field input
        if(namefld.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Prenom");
        }else if(lastnamefld.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Nom");
        }else if(adresse.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'adresse");
        }else{
            //create new DVD
            Adherent adherent = new Adherent(0,this.namefld.getText(), this.lastnamefld.getText(), this.adresse.getText());
            //save DVD using AdherentDao.save
            adherentdao.save(adherent);
            JOptionPane.showMessageDialog(null,"L'adhérent est ajouté");
        }
    }

    //creat an observable list to fill it with Adherents
    public ObservableList<Adherent> data= FXCollections.observableArrayList();
    //Show Adherent using his name
    public void onclickAfficherAdh() {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM `adherent` WHERE `lastname`=\""+nomitosearch.getText()+"\"";//return searched DVD
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with the Adherent
                data.add(new Adherent(rs.getInt("id"), rs.getString("name"),
                        rs.getNString("lastName"), rs.getNString("adresse")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with Adherent's informations
        id.setCellValueFactory(data->new  SimpleIntegerProperty(data.getValue().getId()));
        name.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getName()));
        lastname.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getLastName()));
        adresse1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getAdresse()));
        tableAdherent.setItems(data);
        nomitosearch.setText("");
    }

    //delete Adherent using his ID
    public void onclickSupprimerAdh() {
        //test field input
        if(!idtosupp.getText().matches("[1-9]\\d{0,2}")){
            JOptionPane.showMessageDialog(null,"Entre un chifre reel");
        }else if(idtosupp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Id");
        }else if(adherentdao.findById(Integer.parseInt(idtosupp.getText()))==null){
            JOptionPane.showMessageDialog(null,"Cette Id n'est pas liee a un adherent!");
        }else{
            //delete DVD using AdherentDao.deleteByNumeroDVD Function
            AdherentDao adherentdao= new AdherentDaoImpliment();
            adherentdao.deleteById(Integer.parseInt(idtosupp.getText()));
            JOptionPane.showMessageDialog(null,"L'adhérent est supprimer");
        }
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

    //Show All Adherents Exist in a new scene
    public void onafficheall() {
        Stage stage=(Stage) affichalladher.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("afficheradherent.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }
}
