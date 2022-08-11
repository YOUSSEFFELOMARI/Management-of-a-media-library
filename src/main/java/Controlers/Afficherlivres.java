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
import model.Livre;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Show all DVD exist
public class Afficherlivres implements Initializable {

    //table of Books exist
    @FXML
    private TableView<Livre> tablelivre;
    @FXML
    private TableColumn<Livre, String> numeroLivre;
    @FXML
    private TableColumn<Livre, String> titre;
    @FXML
    private TableColumn<Livre, String> auteurs;
    @FXML
    private TableColumn<Livre, String> maisonEdition;
    @FXML
    private TableColumn<Livre, String> nombrePages;
    @FXML
    private TableColumn<Livre, String> prix;

    //button used
    @FXML
    private Button retour;

    //creat an observable list to fill it with Books
    public ObservableList<Livre> data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = DBConnection.getConnection();
        try{
            String query = "SELECT * FROM `livre` ";//return all books exist
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //fill up the observable list with Books
                data.add(new Livre(rs.getString("numeroLivre"), rs.getString("titre"),
                        rs.getNString("auteurs"), rs.getNString("maisonEdition"),
                        rs.getString("nombrePages"), rs.getString("prix")));
            }
            conn.close();//close connection with database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //fill up the table with Adherent's informations
        numeroLivre.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNumeroLivre()));
        titre.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getTitre()));
        auteurs.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getAuteurs()));
        maisonEdition.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getMaisonEdition()));
        nombrePages.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNombrePages()));
        prix.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getPrix()));
        tablelivre.setItems(data);
    }

    //return to the last scene
    public void onretour() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("gestlivre.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }
}
