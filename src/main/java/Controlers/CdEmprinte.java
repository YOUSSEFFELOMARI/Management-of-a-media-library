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
import model.CDEmp;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//CDs borrowed
public class CdEmprinte implements Initializable {

    //table of CDs borrowed

    @FXML
    private TableView<CDEmp> tablecd;
    @FXML
    private TableColumn<CDEmp, Number> numCd;
    @FXML
    private TableColumn<CDEmp, String> nomAlbumt;
    @FXML
    private TableColumn<CDEmp, String> nomInterpretet;
    @FXML
    private TableColumn<CDEmp, String> nomEditeurt;
    @FXML
    private TableColumn<CDEmp, String> nomAdherent;

    //button used
    @FXML
    private Button retour;

    //create an observable list to fill it with CDs borrowed
    public ObservableList<CDEmp> data = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try {
            String query = "SELECT cd.numCd , cd.nomAlbum, cd.nomInterprete, cd.nomEditeur, adherent.name " +
                    "FROM emprunte, cd, adherent where cd.numCd  = emprunte.idDocum and emprunte.idAdh=adherent.id  and emprunte.type = \"CD\"";//return all CDs borrowed
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with CDs
                data.add(new CDEmp(rs.getInt("numCd"), rs.getString("nomAlbum"),
                                rs.getNString("nomInterprete"), rs.getString("nomEditeur"), rs.getString("name")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with CD's informations
        numCd.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNumCd()));
        nomAlbumt.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomAlbum()));
        nomInterpretet.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomInterprete()));
        nomEditeurt.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomEditeur()));
        nomAdherent.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomAdherent()));
        tablecd.setItems(data);
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
