package Controlers;

import Dao.DBConnection;
import javafx.beans.property.SimpleIntegerProperty;
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
import model.AdherentEmpLiv;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Adherents who borrow Books
public class AdhrentELiv implements Initializable {

    //table of Adherents who borrow Books
    @FXML
    private TableView<AdherentEmpLiv> tableAdherent;
    @FXML
    private TableColumn<AdherentEmpLiv, Number> id;
    @FXML
    private TableColumn<AdherentEmpLiv, String> name;
    @FXML
    private TableColumn<AdherentEmpLiv, String> lastname;
    @FXML
    private TableColumn<AdherentEmpLiv, String> adresse;
    @FXML
    private TableColumn<AdherentEmpLiv, String> titrelivre;

    //button used
    @FXML
    private Button retour;

    //creat an observable list to fill it with Adherents
    public ObservableList<AdherentEmpLiv> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT adherent.id,adherent.name,adherent.lastname,adherent.adresse, livre.titre " +
                    "FROM livre ,adherent,emprunte " +
                    "WHERE livre.numeroLivre=emprunte.idDocum " +
                    "AND emprunte.idAdh=adherent.id " +
                    "AND emprunte.type=\"LIVRE\"";//return Adherents who borrow Books(Livre)
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                //fill up the observable list with the Adherents who borrow Books
                data.add(new AdherentEmpLiv(rs.getInt("id"), rs.getString("lastname"),
                        rs.getNString("name"), rs.getNString("adresse"), rs.getNString("titre")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with Adherent's informations
        id.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getId()));
        name.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getName()));
        lastname.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getLastName()));
        adresse.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getAdresse()));
        titrelivre.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getTitre()));
        tableAdherent.setItems(data);
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
