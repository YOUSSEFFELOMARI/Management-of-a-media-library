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
import model.DVD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Show all DVD exist
public class Afficherdvd implements Initializable{

    //table of DVDs exist
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

    //button used
    @FXML
    private Button retour;

    //creat an observable list to fill it with DVDs
    public ObservableList<DVD> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM `dvd` ";//return all DVD exist
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with DVDs
                data.add(new DVD(rs.getInt("numDvd"),rs.getString("nomFilm"),
                        rs.getNString("nomDocumentaire"), rs.getNString("nomRealisateur") ,
                        rs.getString("nomActeur") , rs.getString("nomEditeurdvd"),
                        rs.getString("nomProducteur")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with Adherent's informations
        numDvdt.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getNumDvd()));
        nomFilmt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomFilm()));
        nomDocumentairet.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomDocumentaire()));
        nomRealisateurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomRealisateur()));
        nomActeurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomActeur()));
        nomEditeurdvdt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomEditeurdvd()));
        nomProducteurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomProducteur()));
        tabledvd.setItems(data);
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestdvd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }
}
