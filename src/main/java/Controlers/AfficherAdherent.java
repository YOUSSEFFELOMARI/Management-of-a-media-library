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
import model.Adherent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Show all Adherent exist
public class AfficherAdherent implements Initializable {

    //table of Books exist
    @FXML
    private TableView<Adherent> tableAdherent;
    @FXML
    private TableColumn<Adherent, Number> id;
    @FXML
    private TableColumn<Adherent, String> name;
    @FXML
    private TableColumn<Adherent, String> lastname;
    @FXML
    private TableColumn<Adherent, String> adresse;

    //button used
    @FXML
    private Button retour;

    //creat an observable list to fill it with Adherents
    public ObservableList<Adherent> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM `adherent` ";//return all Adherent exist
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with Adherents
                data.add(new Adherent(rs.getInt("id"), rs.getString("lastname"),
                        rs.getNString("name"), rs.getNString("adresse")));
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
        tableAdherent.setItems(data);
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestadherent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }
}
