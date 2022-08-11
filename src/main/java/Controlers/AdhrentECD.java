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
import model.AdherentEmpCD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Adherents who borrow CDs
public class AdhrentECD implements Initializable {

    //table of Adherents who borrow CDs
    @FXML
    private TableView<AdherentEmpCD> tableAdherent;
    @FXML
    private TableColumn<AdherentEmpCD, Number> id;
    @FXML
    private TableColumn<AdherentEmpCD, String> name;
    @FXML
    private TableColumn<AdherentEmpCD, String> lastname;
    @FXML
    private TableColumn<AdherentEmpCD, String> adresse;
    @FXML
    private TableColumn<AdherentEmpCD, String> nomalbum;

    //button used
    @FXML
    private Button retour;

    //creat an observable list to fill it with Adherents
    public ObservableList<AdherentEmpCD> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT adherent.id,adherent.name,adherent.lastname,adherent.adresse, cd.nomAlbum " +
                    "FROM CD ,adherent,emprunte " +
                    "WHERE CD.numCD=emprunte.idDocum " +
                    "AND emprunte.idAdh=adherent.id " +
                    "AND emprunte.type=\"CD\"";//return Adherents who borrow CDs
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with the Adherents who borrow CDs
                data.add(new AdherentEmpCD(rs.getInt("id"), rs.getString("lastname"),
                        rs.getNString("name"), rs.getNString("adresse"), rs.getNString("nomAlbum")));
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
        nomalbum.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomAlbum()));
        tableAdherent.setItems(data);
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestemprt.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }
}
