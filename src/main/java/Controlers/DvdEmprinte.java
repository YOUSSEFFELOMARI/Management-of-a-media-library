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
import model.DVDEmp;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//DVDs borrowed
public class DvdEmprinte implements Initializable {

    //table of DVDs borrowed
    @FXML
    private TableView<DVDEmp> tabledvd;
    @FXML
    private TableColumn<DVDEmp, Number> numDvdt;
    @FXML
    private TableColumn<DVDEmp, String> nomFilmt;
    @FXML
    private TableColumn<DVDEmp, String> nomDocumentairet;
    @FXML
    private TableColumn<DVDEmp, String> nomRealisateurt;
    @FXML
    private TableColumn<DVDEmp, String> nomActeurt;
    @FXML
    private TableColumn<DVDEmp, String> nomEditeurdvdt;
    @FXML
    private TableColumn<DVDEmp, String> nomProducteurt;
    @FXML
    private TableColumn<DVDEmp, String> nomAdherent;

    //button used
    @FXML
    private Button retour;

    //create an observable list to fill it with DVDs borrowed
    public ObservableList<DVDEmp> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT dvd.numDvd , dvd.nomFilm, dvd.nomDocumentaire, dvd.nomRealisateur, dvd.nomActeur, dvd.nomEditeurdvd , dvd.nomProducteur ," +
                    " adherent.name FROM emprunte, dvd, adherent where dvd.numDvd = emprunte.idDocum and emprunte.idAdh=adherent.id  and emprunte.type = \"DVD\"";//return all DVDs borrowed
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with DVDs
                data.add(new DVDEmp(rs.getInt("numDvd"),rs.getString("nomFilm"),
                        rs.getNString("nomDocumentaire"), rs.getNString("nomRealisateur") ,
                        rs.getString("nomActeur") , rs.getString("nomEditeurdvd"),
                        rs.getString("nomProducteur"),rs.getString("name")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with DVD's informations
        numDvdt.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getNumDvd()));
        nomFilmt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomFilm()));
        nomDocumentairet.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomDocumentaire()));
        nomRealisateurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomRealisateur()));
        nomActeurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomActeur()));
        nomEditeurdvdt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomEditeurdvd()));
        nomProducteurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomProducteur()));
        nomAdherent.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomAdherent()));
        tabledvd.setItems(data);
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