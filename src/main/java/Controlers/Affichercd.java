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
import model.CD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Show all DVD exist
public class Affichercd implements Initializable {

    //table of CD exist
    @FXML
    private TableView<CD> tablecd;
    @FXML
    private TableColumn<CD, Number> numCdt;
    @FXML
    private TableColumn<CD, String> nomAlbumt;
    @FXML
    private TableColumn<CD, String> nomInterpretet;
    @FXML
    private TableColumn<CD, String> nomEditeurt;

    //button used
    @FXML
    private Button retour;

    //creat an observable list to fill it with CDs
    public ObservableList<CD> data= FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM cd ";//return all CDs exist
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with CDs
                data.add(new CD(rs.getInt("numCd"),rs.getString("nomAlbum"),
                        rs.getNString("nomInterprete"), rs.getNString("nomEditeur")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with Adherent's informations
        numCdt.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getNumCd()));
        nomAlbumt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomAlbum()));
        nomInterpretet.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomInterprete()));
        nomEditeurt.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNomEditeur()));
        tablecd.setItems(data);
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestcd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }
}
