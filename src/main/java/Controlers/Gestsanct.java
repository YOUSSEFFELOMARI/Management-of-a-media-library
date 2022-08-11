package Controlers;

import Dao.DBConnection;
import javafx.beans.property.SimpleDoubleProperty;
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
import model.AdherentSanc;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Gestsanct implements Initializable {
    @FXML
    private TableView<AdherentSanc> tableAdherent;
    @FXML
    private TableColumn<AdherentSanc, Number> id;
    @FXML
    private TableColumn<AdherentSanc, String> name;
    @FXML
    private TableColumn<AdherentSanc, String> lastname;
    @FXML
    private TableColumn<AdherentSanc, String> adresse;
    @FXML
    private TableColumn<AdherentSanc, Number> date_difference;
    @FXML
    private TableColumn<AdherentSanc, Number> montant;

    //button used
    @FXML
    private Button retour;

    LocalDate currentdate=LocalDate.now();

    //create an observable list to fill it with adherents who borrowed books more than 20 OR DVDs/CDs more than 14 days(sanctioned adherents)
    public ObservableList<AdherentSanc> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT a.id, a.name, a.lastname, a.adresse , SUM(a.date_difference) AS date_difference, SUM(date_difference*20) as Montant " +
                    "FROM (SELECT adherent.id, adherent.name, adherent.lastname, adherent.adresse , DATEDIFF( ?, emprunte.datelimite) AS date_difference " +
                    "FROM emprunte, adherent WHERE emprunte.idAdh = adherent.id AND emprunte.datelimite < ? ) AS a GROUP BY a.id";
                    //return adherents who borrowed books more than 20 OR DVDs/CDs more than 14 days(sanctioned adherents)
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(currentdate));
            preparedStatement.setString(2, String.valueOf(currentdate));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with sanctioned adherents
                data.add(new AdherentSanc(rs.getInt("id"),rs.getString("name"),rs.getString("lastname"),rs.getString("adresse"), rs.getInt("date_difference"),
                        rs.getDouble("montant")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with sanctioned adherent's informations
        id.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getId()));
        name.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getName()));
        lastname.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getLastname()));
        adresse.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getAdresse()));
        date_difference.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getDate_difference()));
        montant.setCellValueFactory(data->new SimpleDoubleProperty(data.getValue().getMontant()));
        tableAdherent.setItems(data);
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
