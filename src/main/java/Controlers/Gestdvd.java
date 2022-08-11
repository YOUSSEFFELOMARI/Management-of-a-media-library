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
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DVD;
import static java.lang.Integer.parseInt;

public class Gestdvd {


    //table of DVD searched
    @FXML
    private TableView<DVD> tabledvd;
    @FXML
    private TableColumn<DVD, Number> numDvdt;
    @FXML
    private TableColumn<DVD, String> nomFilmt;
    @FXML
    private TableColumn<DVD, String> nomDocumentairet;
    @FXML
    private TableColumn<DVD, String> nomRealisateurt;
    @FXML
    private TableColumn<DVD, String> nomActeurt;
    @FXML
    private TableColumn<DVD, String> nomEditeurdvdt;
    @FXML
    private TableColumn<DVD, String> nomProducteurt;

    //fieled input
    @FXML
    private TextField nomFilm;
    @FXML
    private TextField nomActeur;
    @FXML
    private TextField nomRealisateur;
    @FXML
    private TextField nomDocumentaire;
    @FXML
    private TextField nomEditeurdvd;
    @FXML
    private TextField nomProducteur;
    @FXML
    private TextField titretosearch;
    @FXML
    private TextField numtosup;

    //button used
    @FXML
    private Button afficherDVD;
    @FXML
    public Button retour;

    DVDDao dvdDao= new DVDDaoImpliment();

    //add DVD function
    public void onclickAjouterDVD() {
        //test field input
        if(nomFilm.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nomFilm");
        }else if(nomDocumentaire.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nomDocumentaire");
        }else if(nomRealisateur.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nomRealisateur");
        }else if(nomActeur.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nomActeur");
        }else if(nomEditeurdvd.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nomEditeurdvd");
        }else if(nomProducteur.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nomProducteur");
        }else{
            //create a new DVD
            DVD dvd = new DVD(0,this.nomFilm.getText(), this.nomDocumentaire.getText(), this.nomRealisateur.getText(),
                    this.nomActeur.getText() , this.nomEditeurdvd.getText(), this.nomProducteur.getText());
            //save DVD using DVDDao.save
            dvdDao.save(dvd);
            JOptionPane.showMessageDialog(null,"Dvd est ajouté");
        }
        //reset input fieled
        nomFilm.setText("");
        nomDocumentaire.setText("");
        nomRealisateur.setText("");
        nomActeur.setText("");
        nomEditeurdvd.setText("");
        nomProducteur.setText("");
    }

    //Show All DVDs Exist in a new scene
    public void onclickAfficherDVD() {
        Stage stage=(Stage) afficherDVD.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("afficherdvd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //delete DVD using his ID
    public void onclickSupprimerDVD() {
        //test field input
        if(!numtosup.getText().matches("[1-9]\\d{0,3}")){
            JOptionPane.showMessageDialog(null,"Entre un chiffre reel");
        }else if(numtosup.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de DVD");
        }else if(dvdDao.findBynum(parseInt(numtosup.getText()))==null){
            JOptionPane.showMessageDialog(null,"Cette Numero de DVD n'est pas liee a un DVD!");
        }else{
            //delete DVD using DVDDao.deleteByNumeroDVD Function
            DVDDao dvdDao1= new DVDDaoImpliment();
            dvdDao1.deleteByNumeroDvd(parseInt(numtosup.getText()));
            JOptionPane.showMessageDialog(null,"DVD est supprimer");
        }
        //reset input field
        numtosup.setText("");
    }

    //creat an observable list to fill it with DVDs
    public ObservableList<DVD> data= FXCollections.observableArrayList();
    //Show DVD using his title
    public void onclickAfficherunDVD() {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM dvd where nomFilm= '"+titretosearch.getText()+"'";//return searched DVD
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with the CD
                data.add(new DVD(rs.getInt("numDvd"),rs.getString("nomFilm"),
                        rs.getNString("nomDocumentaire"), rs.getNString("nomRealisateur") ,
                        rs.getString("nomActeur") , rs.getString("nomEditeurdvd"),
                        rs.getString("nomProducteur")));
            }

            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with CD's informations
        numDvdt.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getNumDvd()));
        nomFilmt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomFilm()));
        nomDocumentairet.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomDocumentaire()));
        nomRealisateurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomRealisateur()));
        nomActeurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomActeur()));
        nomEditeurdvdt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomEditeurdvd()));
        nomProducteurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomProducteur()));
        tabledvd.setItems(data);
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
