package Dao;

import model.Adherent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdherentDaoImpliment implements AdherentDao {

    //find DVD using ID
    @Override
    public Adherent findById(int id) {
        Connection conn=DBConnection.getConnection();
        //test database connection
        if (conn==null) {
            return null;
        }

        String query="SELECT * FROM `adherent` WHERE id=?";//return Adherent

        try(PreparedStatement preparedStatement=conn.prepareStatement(query)){

            preparedStatement.setInt(1, id);
            ResultSet rs= preparedStatement.executeQuery();
            if(rs.next()){
                return new Adherent(rs.getInt("id"),rs.getString("name"),rs.getString("lastname"),rs.getString("adresse"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    //save CD function
    @Override
    public void save(Adherent Adherent) {
        Connection conn = DBConnection.getConnection();
        if(conn == null) return;
        
        if (Adherent.getId() > 0){
            String query = "update adherent set name=?, lastname=? ,adresse=? where id =?;";//update Adherent using ID
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, Adherent.getName());
                preparedStatement.setString(2, Adherent.getLastName());
                preparedStatement.setString(3, Adherent.getAdresse());
                preparedStatement.setInt(4, Adherent.getId());

                preparedStatement.executeUpdate();

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            String query = "INSERT INTO `adherent`(`id`, `name`, `lastname`, `adresse`) VALUES (?,?,?,?)";//create new Adherent
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, Adherent.getId());
                preparedStatement.setString(2, Adherent.getName());
                preparedStatement.setString(3, Adherent.getLastName());
                preparedStatement.setString(4, Adherent.getAdresse());

                preparedStatement.executeUpdate();

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    //delete Adherent using ID
    @Override
    public void deleteById(int id) {
        Connection con = DBConnection.getConnection();
        if(con == null)
            return;

        String sql = "DELETE FROM adherent WHERE id=?;";
        try {
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setInt(1, id);
            prest.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }
}