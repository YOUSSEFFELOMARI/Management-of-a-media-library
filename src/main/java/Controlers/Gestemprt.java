package Controlers;

import Dao.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class Gestemprt {
    @FXML
    private TextField numAdhEmp;
    @FXML
    private TextField numLivEmp;
    @FXML
    private TextField numDvdEmp;
    @FXML
    private TextField numCdEmp;
    @FXML
    private TextField numAdhRen;
    @FXML
    private TextField numLivRen;
    @FXML
    private TextField numDvdRen;
    @FXML
    private TextField numCdRen;
    @FXML
    private Button retour;

    Connection conn = DBConnection.getConnection();
    LocalDate datelivre=  LocalDate.now().plusDays(20);//limmit date for books
    LocalDate dateDvdCd=  LocalDate.now().plusDays(15);//limmit date for dvd/cd
    LocalDate currentedate=  LocalDate.now();
    public void OnClickEmprunterLivre() {

        if(conn == null) return;
        if(numAdhEmp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'Id d'adherent");
        }else if(numLivEmp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de livre");
        }else{
            //check adherent's id exist
            try(PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM `adherent` WHERE id=?")){
                preparedStatement.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                ResultSet rs= preparedStatement.executeQuery();
                if(rs.next()){
                    //check if book's id exist
                    try(PreparedStatement preparedStatement2=conn.prepareStatement("SELECT * FROM `livre` WHERE numeroLivre=?")){
                        preparedStatement2.setInt(1, Integer.parseInt(numLivEmp.getText()));
                        ResultSet rs2= preparedStatement2.executeQuery();
                        if(rs2.next()){
                            //return Adherent who borrow books in last 20 days ago
                            try(PreparedStatement preparedStatement3 = conn.prepareStatement("SELECT idAdh,COUNT(idAdh) as nombredeemprunt20jours FROM `emprunte` WHERE idAdh=? " +
                                    "AND type=\"Livre\" AND dateEmprunt BETWEEN ? AND ? GROUP BY idAdh;")){
                                preparedStatement3.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                                preparedStatement3.setDate(2,Date.valueOf(currentedate.plusDays(-20).toString()));
                                preparedStatement3.setDate(3,Date.valueOf(currentedate.toString()));
                                ResultSet rs3= preparedStatement3.executeQuery();
                                if (rs3.next()){
                                    //return Adherent who returned the books in last 20 days ago
                                    try(PreparedStatement preparedStatement4 = conn.prepareStatement("SELECT idAdh,COUNT(idAdh) as nombredeemprunt20jours FROM `rendre` WHERE idAdh=? " +
                                            "AND type=\"Livre\" AND daterendre BETWEEN ? AND ? GROUP BY idAdh")) {
                                        preparedStatement4.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                                        preparedStatement4.setDate(2, Date.valueOf(currentedate.plusDays(-20).toString()));
                                        preparedStatement4.setDate(3, Date.valueOf(currentedate.toString()));
                                        ResultSet rs4 = preparedStatement4.executeQuery();
                                        if (rs4.next()){
                                            //check if the adherent have borowed 5 books in 20 days
                                            int a =Integer.parseInt(rs3.getString(2))-Integer.parseInt(rs4.getString(2));
                                            if (a>4){//the adherent have borrow 5 books
                                                JOptionPane.showMessageDialog(null,"l'adherent n'as pas de le droit d'emprunter");
                                            }else {//the adherent have borrowed 5 books and returned one or more
                                                String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";//insert adherent with book's ID that have borrowed
                                                try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                                    preparedStatement5.setInt(1, 0);
                                                    preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                                    preparedStatement5.setInt(3, Integer.parseInt(numLivEmp.getText()));
                                                    preparedStatement5.setString(4, "Livre");
                                                    preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                                    preparedStatement5.setDate(6, Date.valueOf(datelivre.toString()));
                                                    preparedStatement5.executeUpdate();
                                                    JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                numLivEmp.setText("");
                                            }
                                        }else if(Integer.parseInt(rs3.getString(2)) < 5) {//the adherent have borrowed less than 5 books
                                            String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                            try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                                preparedStatement5.setInt(1, 0);
                                                preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                                preparedStatement5.setInt(3, Integer.parseInt(numLivEmp.getText()));
                                                preparedStatement5.setString(4, "Livre");
                                                preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                                preparedStatement5.setDate(6, Date.valueOf(datelivre.toString()));

                                                preparedStatement5.executeUpdate();
                                                JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            numLivEmp.setText("");
                                        }else {//adherent have exceed 5 books
                                            JOptionPane.showMessageDialog(null, "Vous etez depasse 5 livre par 20 jour");
                                            numLivEmp.setText("");
                                        }
                                    }catch (SQLException e){
                                        throw new RuntimeException();
                                    }
                                }else {//adherent haven't borrowed any book befor
                                    String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                    try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                        preparedStatement5.setInt(1, 0);
                                        preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                        preparedStatement5.setInt(3, Integer.parseInt(numLivEmp.getText()));
                                        preparedStatement5.setString(4, "Livre");
                                        preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                        preparedStatement5.setDate(6, Date.valueOf(datelivre.toString()));
                                        preparedStatement5.executeUpdate();
                                        JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    numLivEmp.setText("");
                                }
                            }catch (SQLException e){
                                throw new RuntimeException();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null,"Numero de livre nexiste pas");//id of book not exist
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    numLivEmp.setText("");
                    numAdhEmp.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Numero d'adherent n'existe pas");//id of adherent not exist
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void OnClickEmprunterDvd() {

        if(conn == null) return;
        if(numAdhEmp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'Id d'adherent");
        }else if(numDvdEmp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de DVD");
        }else{
            //check adherent's id exist
            try(PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM `adherent` WHERE id=?")){
                preparedStatement.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                ResultSet rs= preparedStatement.executeQuery();
                if(rs.next()){
                    //check if DVD's id exist
                    try(PreparedStatement preparedStatement2=conn.prepareStatement("SELECT * FROM `dvd` WHERE numDvd=?")){
                        preparedStatement2.setInt(1, Integer.parseInt(numDvdEmp.getText()));
                        ResultSet rs2= preparedStatement2.executeQuery();
                        if(rs2.next()){
                            //return Adherent who borrow DVDs in last 15 days ago
                            try(PreparedStatement preparedStatement3 = conn.prepareStatement("SELECT idAdh,COUNT(idAdh) as nombredeemprunt15jours FROM `emprunte` WHERE idAdh=? AND type=\"DVD\" AND dateEmprunt BETWEEN ? AND ? GROUP BY idAdh;")){
                                preparedStatement3.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                                preparedStatement3.setDate(2,Date.valueOf(currentedate.plusDays(-15).toString()));
                                preparedStatement3.setDate(3,Date.valueOf(currentedate.toString()));
                                ResultSet rs3= preparedStatement3.executeQuery();
                                if (rs3.next()){
                                    //return Adherent who returned the DVDs in last 15 days ago
                                    try(PreparedStatement preparedStatement4 = conn.prepareStatement("SELECT idAdh,COUNT(idAdh) as nombredeemprunt15jours FROM `rendre` WHERE idAdh=? AND type=\"DVD\" AND daterendre BETWEEN ? AND ? GROUP BY idAdh")) {
                                        preparedStatement4.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                                        preparedStatement4.setDate(2, Date.valueOf(currentedate.plusDays(-15).toString()));
                                        preparedStatement4.setDate(3, Date.valueOf(currentedate.toString()));
                                        ResultSet rs4 = preparedStatement4.executeQuery();
                                        if (rs4.next()){
                                            //check if the adherent have borowed 5 DVDs in 15 days
                                            int a =Integer.parseInt(rs3.getString(2))-Integer.parseInt(rs4.getString(2));
                                            if (a>2){//the adherent have borrowed 5 DVDs
                                                JOptionPane.showMessageDialog(null,"l'adherent n'as pas de le droit d'emprunter");
                                            }else {//the adherent have borrowed 5 DVDs and returned one or more
                                                String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                                try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                                    preparedStatement5.setInt(1, 0);
                                                    preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                                    preparedStatement5.setInt(3, Integer.parseInt(numDvdEmp.getText()));
                                                    preparedStatement5.setString(4, "DVD");
                                                    preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                                    preparedStatement5.setDate(6, Date.valueOf(dateDvdCd.toString()));
                                                    preparedStatement5.executeUpdate();
                                                    JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                numDvdEmp.setText("");
                                            }
                                        }else if(Integer.parseInt(rs3.getString(2)) < 3) {//the adherent have borrowed less than 3 DVDs
                                            String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                            try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                                preparedStatement5.setInt(1, 0);
                                                preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                                preparedStatement5.setInt(3, Integer.parseInt(numDvdEmp.getText()));
                                                preparedStatement5.setString(4, "DVD");
                                                preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                                preparedStatement5.setDate(6, Date.valueOf(dateDvdCd.toString()));

                                                preparedStatement5.executeUpdate();
                                                JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            numDvdEmp.setText("");
                                        }else {
                                            JOptionPane.showMessageDialog(null, "Vous etez depasse 3 DVD par 15 jour");
                                            numDvdEmp.setText("");
                                        }
                                    }catch (SQLException e){
                                        throw new RuntimeException();
                                    }
                                }else {//adherent haven't borrowed any DVD befor
                                    String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                    try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                        preparedStatement5.setInt(1, 0);
                                        preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                        preparedStatement5.setInt(3, Integer.parseInt(numDvdEmp.getText()));
                                        preparedStatement5.setString(4, "DVD");
                                        preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                        preparedStatement5.setDate(6, Date.valueOf(dateDvdCd.toString()));

                                        preparedStatement5.executeUpdate();
                                        JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    numDvdEmp.setText("");
                                }
                            }catch (SQLException e){
                                throw new RuntimeException();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null,"Numero de DVD nexiste pas");//id of DVD not exist
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    numDvdEmp.setText("");
                    numAdhEmp.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Numero d'adherent n'existe pas");//id of adherent not exist
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void OnClickEmprunterCd() {
        if(conn == null) return;
        if(numAdhEmp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'Id d'adherent");
        }else if(numCdEmp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de CD");
        }else{
            //check adherent's id exist
            try(PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM `adherent` WHERE id=?")){
                preparedStatement.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                ResultSet rs= preparedStatement.executeQuery();
                if(rs.next()){
                    //check if CD's id exist
                    try(PreparedStatement preparedStatement2=conn.prepareStatement("SELECT * FROM `cd` WHERE numCd=?")){
                        preparedStatement2.setInt(1, Integer.parseInt(numCdEmp.getText()));
                        ResultSet rs2= preparedStatement2.executeQuery();
                        if(rs2.next()){
                            //return Adherent who borrow CDs in last 15 days ago
                            try(PreparedStatement preparedStatement3 = conn.prepareStatement("SELECT idAdh,COUNT(idAdh) as nombredeemprunt20jours FROM `emprunte` WHERE idAdh=? AND type=\"CD\" AND dateEmprunt BETWEEN ? AND ? GROUP BY idAdh;")){
                                preparedStatement3.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                                preparedStatement3.setDate(2,Date.valueOf(currentedate.plusDays(-15).toString()));
                                preparedStatement3.setDate(3,Date.valueOf(currentedate.toString()));
                                ResultSet rs3= preparedStatement3.executeQuery();
                                if (rs3.next()){
                                    //return Adherent who returned the CDs in last 15 days ago
                                    try(PreparedStatement preparedStatement4 = conn.prepareStatement("SELECT idAdh,COUNT(idAdh) as nombredeemprunt20jours FROM `rendre` WHERE idAdh=? AND type=\"CD\" AND daterendre BETWEEN ? AND ? GROUP BY idAdh")) {
                                        preparedStatement4.setInt(1, Integer.parseInt(numAdhEmp.getText()));
                                        preparedStatement4.setDate(2, Date.valueOf(currentedate.plusDays(-15).toString()));
                                        preparedStatement4.setDate(3, Date.valueOf(currentedate.toString()));
                                        ResultSet rs4 = preparedStatement4.executeQuery();
                                        if (rs4.next()){
                                            //check if the adherent have borowed 5 CDs in 15 days
                                            int a =Integer.parseInt(rs3.getString(2))-Integer.parseInt(rs4.getString(2));
                                            if (a>2){//the adherent have borrowed 5 CDs
                                                JOptionPane.showMessageDialog(null,"l'adherent n'as pas de le droit d'emprunter");
                                            }else {//the adherent have borrowed 5 CDs and returned one or more
                                                String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                                try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                                    preparedStatement5.setInt(1, 0);
                                                    preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                                    preparedStatement5.setInt(3, Integer.parseInt(numCdEmp.getText()));
                                                    preparedStatement5.setString(4, "CD");
                                                    preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                                    preparedStatement5.setDate(6, Date.valueOf(dateDvdCd.toString()));
                                                    preparedStatement5.executeUpdate();
                                                    JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                numCdEmp.setText("");

                                            }
                                        }else if(Integer.parseInt(rs3.getString(2)) < 3) {//the adherent have borrowed less than 3 CDs
                                            String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                            try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                                preparedStatement5.setInt(1, 0);
                                                preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                                preparedStatement5.setInt(3, Integer.parseInt(numCdEmp.getText()));
                                                preparedStatement5.setString(4, "CD");
                                                preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                                preparedStatement5.setDate(6, Date.valueOf(dateDvdCd.toString()));
                                                preparedStatement5.executeUpdate();
                                                JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            numCdEmp.setText("");
                                        }else {
                                            JOptionPane.showMessageDialog(null, "Vous etez depasse 3 CD par 15 jour");
                                            numCdEmp.setText("");
                                        }
                                    }catch (SQLException e){
                                        throw new RuntimeException();
                                    }
                                }else {//adherent haven't borrowed any CD befor
                                    String query = "INSERT INTO `emprunte`(`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`,`datelimite`) VALUES (?,?,?,?,?,?)";
                                    try (PreparedStatement preparedStatement5 = conn.prepareStatement(query)) {
                                        preparedStatement5.setInt(1, 0);
                                        preparedStatement5.setInt(2, Integer.parseInt(numAdhEmp.getText()));
                                        preparedStatement5.setInt(3, Integer.parseInt(numCdEmp.getText()));
                                        preparedStatement5.setString(4, "CD");
                                        preparedStatement5.setDate(5, Date.valueOf(currentedate.toString()));
                                        preparedStatement5.setDate(6, Date.valueOf(dateDvdCd.toString()));

                                        preparedStatement5.executeUpdate();
                                        JOptionPane.showMessageDialog(null, "L'emprunte est ajouté");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    numCdEmp.setText("");
                                }
                            }catch (SQLException e){
                                throw new RuntimeException();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null,"Numero de CD nexiste pas");//id of CD not exist
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    numLivEmp.setText("");
                    numAdhEmp.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Numero d'adherent n'existe pas");//id of adherent not exist
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void OnClickRendreLivre() {
        if(conn == null) return;
        if(numAdhRen.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'Id d'adherent");
        }else if(numLivRen.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de livre");
        }else{
            //check adherent's id exist
            try(PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM `emprunte` WHERE idAdh=?")){
                preparedStatement.setInt(1, Integer.parseInt(numAdhRen.getText()));
                ResultSet rs= preparedStatement.executeQuery();
                if(rs.next()){
                    //check book's id if exist
                    try(PreparedStatement preparedStatement2=conn.prepareStatement("SELECT * FROM `emprunte` WHERE idDocum=? and idAdh=? and type= \"Livre\"")){
                        preparedStatement2.setInt(1, Integer.parseInt(numLivRen.getText()));
                        preparedStatement2.setInt(2, Integer.parseInt(numAdhRen.getText()));
                        ResultSet rs2= preparedStatement2.executeQuery();
                        if(rs2.next()){
                            //put adherent who return the book into database
                            String query = "INSERT INTO `rendre`(`idrendre`, `idAdh`, `idDocum`, `type`, `daterendre`) VALUES (?,?,?,?,?)";
                            try(PreparedStatement preparedStatement3 = conn.prepareStatement(query)) {
                                preparedStatement3.setInt(1, 0);
                                preparedStatement3.setInt(2, Integer.parseInt(numAdhRen.getText()));
                                preparedStatement3.setInt(3, Integer.parseInt(numLivRen.getText()));
                                preparedStatement3.setString(4, "Livre");
                                preparedStatement3.setDate(5, Date.valueOf(currentedate.toString()));
                                preparedStatement3.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Livre est rendu");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            numLivRen.setText("");
                        }
                        else {//adherent doesn't borrow The book referred to
                            JOptionPane.showMessageDialog(null,"l'adherent de id: "+numAdhRen.getText()+
                                    " n'as pas emprunte ce livre");
                            numLivRen.setText("");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    numLivRen.setText("");
                    numAdhRen.setText("");
                }
                else {//adherent doesn't borrow the indexed book any book
                    JOptionPane.showMessageDialog(null,"l'adherent n'as pas emprunter un livre");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void OnClickRendreDvd() {
        if(conn == null) return;
        if(numAdhRen.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'Id d'adherent");
        }else if(numDvdRen.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de DVD");
        }else{
            //check adherent's id exist
            try(PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM `emprunte` WHERE idAdh=?")){
                preparedStatement.setInt(1, Integer.parseInt(numAdhRen.getText()));
                ResultSet rs= preparedStatement.executeQuery();
                if(rs.next()){
                    //check DVD's id if exist
                    try(PreparedStatement preparedStatement2=conn.prepareStatement("SELECT * FROM `emprunte` WHERE idDocum=? and idAdh=? and type= \"DVD\"")){
                        preparedStatement2.setInt(1, Integer.parseInt(numDvdRen.getText()));
                        preparedStatement2.setInt(2, Integer.parseInt(numAdhRen.getText()));
                        ResultSet rs2= preparedStatement2.executeQuery();
                        if(rs2.next()){
                            //put adherent who return the DVD into database
                            String query = "INSERT INTO `rendre`(`idrendre`, `idAdh`, `idDocum`, `type`, `daterendre`) VALUES (?,?,?,?,?)";
                            try(PreparedStatement preparedStatement3 = conn.prepareStatement(query)) {
                                preparedStatement3.setInt(1, 0);
                                preparedStatement3.setInt(2, Integer.parseInt(numAdhRen.getText()));
                                preparedStatement3.setInt(3, Integer.parseInt(numDvdRen.getText()));
                                preparedStatement3.setString(4, "DVD");
                                preparedStatement3.setDate(5, Date.valueOf(currentedate.toString()));
                                preparedStatement3.executeUpdate();
                                JOptionPane.showMessageDialog(null,"DVD est rendu");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            numDvdRen.setText("");
                        }
                        else {//adherent doesn't borrow The DVD referred to
                            JOptionPane.showMessageDialog(null,"l'adherent de id: "+numAdhRen.getText()+
                                    " n'as pas emprunte ce DVD");
                            numDvdRen.setText("");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    numDvdRen.setText("");
                    numAdhRen.setText("");
                }
                else {//adherent doesn't borrow the indexed DVD any book
                    JOptionPane.showMessageDialog(null,"l'adherent n'as pas emprunter un DVD");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void OnClickRendreCd() {
        if(conn == null) return;
        if(numAdhRen.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter l'Id d'adherent");
        }else if(numCdRen.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter le Numero de CD");
        }else{
            //check adherent's id exist
            try(PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM `emprunte` WHERE idAdh=?")){
                preparedStatement.setInt(1, Integer.parseInt(numAdhRen.getText()));
                ResultSet rs= preparedStatement.executeQuery();
                if(rs.next()){
                    //check CD's id if exist
                    try(PreparedStatement preparedStatement2=conn.prepareStatement("SELECT * FROM `emprunte` WHERE idDocum=? and idAdh=? and type= \"CD\"")){
                        preparedStatement2.setInt(1, Integer.parseInt(numCdRen.getText()));
                        preparedStatement2.setInt(2, Integer.parseInt(numAdhRen.getText()));
                        ResultSet rs2= preparedStatement2.executeQuery();
                        if(rs2.next()){
                            //put adherent who return the CD into database
                            String query = "INSERT INTO `rendre`(`idrendre`, `idAdh`, `idDocum`, `type`, `daterendre`) VALUES (?,?,?,?,?)";
                            try(PreparedStatement preparedStatement3 = conn.prepareStatement(query)) {
                                preparedStatement3.setInt(1, 0);
                                preparedStatement3.setInt(2, Integer.parseInt(numAdhRen.getText()));
                                preparedStatement3.setInt(3, Integer.parseInt(numCdRen.getText()));
                                preparedStatement3.setString(4, "CD");
                                preparedStatement3.setDate(5, Date.valueOf(currentedate.toString()));
                                preparedStatement3.executeUpdate();
                                JOptionPane.showMessageDialog(null,"CD est rendu");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            numCdRen.setText("");
                        }
                        else {//adherent doesn't borrow The CD referred to
                            JOptionPane.showMessageDialog(null,"l'adherent de id: "+numAdhRen.getText()+
                                    " n'as pas emprunte ce CD");
                            numCdRen.setText("");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    numCdRen.setText("");
                    numAdhRen.setText("");
                }
                else {//adherent doesn't borrow the indexed CD any book
                    JOptionPane.showMessageDialog(null,"l'adherent n'as pas emprunter un CD");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //show adherents who borrowed books
    public void OnClickAfficherEmprunteursDeLivres() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("adhrentELiv.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //show adherents who borrowed DVDs
    public void OnClickAfficherEmprunteursDeDvd() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("adhrentEDvd.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //show adherents who borrowed CDs
    public void OnClickAfficherEmprunteursDeCd() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("adhrentECD.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //show borowwed Books
    public void OnClickAfficherLivreEmprinté() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LivreEmprinte.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //show borowwed Books
    public void OnClickAfficherDvdEmprinté() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("dvdEmprinte.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //show borowwed Books
    public void OnClickAfficherCdEmprinte() {
        Stage stage = (Stage) retour.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("cdEmprinte.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    //return to the last scene
    public void OnClickRetour() {
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
