package Controlers;

import Dao.DBConnection;
import Dao.LivreDao;
import Dao.LivreDaoImpliment;
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
import model.Livre;

import javax.swing.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class Gestlivre {

    //table of Book searched
    @FXML
    private TableView<Livre> tablelivre;
    @FXML
    private TableColumn<Livre, String> numeroLivre1;
    @FXML
    private TableColumn<Livre, String> titre1;
    @FXML
    private TableColumn<Livre, String> auteurs1;
    @FXML
    private TableColumn<Livre, String> maisonEdition1;
    @FXML
    private TableColumn<Livre, String> nombrePages1;
    @FXML
    private TableColumn<Livre, String> prix1;

    //fieled input
    @FXML
    private TextField titretosearch;
    @FXML
    private TextField titre;
    @FXML
    private TextField auteurs;
    @FXML
    private TextField maisonEdition;
    @FXML
    private TextField nombrePages;
    @FXML
    private TextField prix;
    @FXML
    private TextField numtosup;

    //button used
    @FXML
    private Button afficherLivre;
    @FXML
    private Button retour;

    LivreDao livreDao= new LivreDaoImpliment();

    //add Book function
    public void onclickAjouterLivre(){
        //test field input
        if(titre.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le titre");
        }else if(auteurs.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le auteurs");
        }else if(maisonEdition.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le maisonEdition");
        }else if(nombrePages.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le nombrePages");
        }else if(prix.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le prix");
        }else{
            //create a new Book
            Livre livre = new Livre("0",this.titre.getText(), this.auteurs.getText(), this.maisonEdition.getText(),this.nombrePages.getText() , this.prix.getText());
            //save Book using LivreDao.save
            livreDao.save(livre);
            JOptionPane.showMessageDialog(null,"Livre est ajouté");
        }
        //reset input fieled
        titre.setText("");
        auteurs.setText("");
        maisonEdition.setText("");
        nombrePages.setText("");
        prix.setText("");
    }

    //Show All Books Exist in a new scene
    public void onclickAfficherLivre() {
        Stage stage=(Stage) afficherLivre.getScene().getWindow() ;
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("afficherlivres.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //delete Book using his ID
    public void onclickSupprimerLivre() {
        //test field input
        if(!numtosup.getText().matches("[1-9]\\d{0,3}")){
            JOptionPane.showMessageDialog(null,"Entre un chiffre reel");
        }else if(numtosup.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de livre");
        }else if(livreDao.findByNumeroLivre(parseInt(numtosup.getText()))==null){
            JOptionPane.showMessageDialog(null,"Cette Numero de livre n'est pas liee a un Livre!");
        }else{
            //delete Book using LivreDao.deleteByNumeroLivre Function
            LivreDao livredao= new LivreDaoImpliment();
            livredao.deleteByNumeroLivre(parseInt(numtosup.getText()));
            JOptionPane.showMessageDialog(null,"Livre est supprimer");
        }
        //reset input field
        numtosup.setText("");
    }

    //creat an observable list to fill it with Books
    public ObservableList<Livre> data= FXCollections.observableArrayList();
    //Show Book using his title
    public void onclickAfficherunLivre(){
            Connection conn = DBConnection.getConnection();
            try{
                String query = "SELECT * FROM livre where titre= '"+titretosearch.getText()+"'";//return searched Book
                PreparedStatement preparedStatement = conn.prepareStatement(query);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    //fill up the observable list with the Book
                    data.add(new Livre(rs.getString("numeroLivre"), rs.getString("titre"),
                            rs.getNString("auteurs"), rs.getNString("maisonEdition"),
                            rs.getString("nombrePages"), rs.getString("prix")));
                }

                conn.close();//close connection with database
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //fill up the table with CD's informations
            numeroLivre1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNumeroLivre()));
            titre1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getTitre()));
            auteurs1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getAuteurs()));
            maisonEdition1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getMaisonEdition()));
            nombrePages1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNombrePages()));
            prix1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getPrix()));
            tablelivre.setItems(data);
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