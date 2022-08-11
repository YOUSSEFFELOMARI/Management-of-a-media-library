package Controlers;

import Dao.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.LivreEmp;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Books borrowed
public class LivreEmprinte implements Initializable {

    //table of Books borrowed
    @FXML
    private TableView<LivreEmp> tablelivre;
    @FXML
    private TableColumn<LivreEmp, String> numeroLivre;
    @FXML
    private TableColumn<LivreEmp, String> titre;
    @FXML
    private TableColumn<LivreEmp, String> auteurs;
    @FXML
    private TableColumn<LivreEmp, String> maisonEdition;
    @FXML
    private TableColumn<LivreEmp, String> nombrePages;
    @FXML
    private TableColumn<LivreEmp, String> prix;
    @FXML
    public TableColumn<LivreEmp, String> nomAdherent;

    //button used
    @FXML
    private Button retour;

    //create an observable list to fill it with Books borrowed
    public ObservableList<LivreEmp> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection conn = DBConnection.getConnection();

        try {
            String query = "SELECT livre.numerolivre , livre.titre, livre.auteurs, livre.maisonEdition, livre.nombrePages, livre.prix , " +
                    "adherent.name FROM emprunte, livre, adherent " +
                    "where livre.numerolivre = emprunte.idDocum and emprunte.idAdh=adherent.id and emprunte.type = \"Livre\" ";//return all books borrowed
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with Books
                data.add(new LivreEmp(rs.getString("numeroLivre"), rs.getString("titre"),
                        rs.getNString("auteurs"), rs.getNString("maisonEdition"),
                        rs.getString("nombrePages"), rs.getString("prix"), rs.getString("name")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with Book's informations
        numeroLivre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNumeroLivre()));
        titre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre()));
        auteurs.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuteurs()));
        maisonEdition.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaisonEdition()));
        nombrePages.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombrePages()));
        prix.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrix()));
        nomAdherent.setCellValueFactory(date -> new SimpleStringProperty(date.getValue().getNameadh()));
        tablelivre.setItems(data);
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Gestemprt.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }
}