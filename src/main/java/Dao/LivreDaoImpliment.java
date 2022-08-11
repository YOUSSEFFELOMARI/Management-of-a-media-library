package Dao;


import model.Livre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreDaoImpliment implements LivreDao {

    //find Book using ID
    @Override
    public Livre findByNumeroLivre(int numeroLivre) {
        Connection conn=DBConnection.getConnection();
        //test database connection
        if (conn==null) {
            return null;
        }

        String query="select * from livre where numeroLivre=?;";//return Book

        try(PreparedStatement preparedStatement=conn.prepareStatement(query)){

            preparedStatement.setInt(1, numeroLivre);
            ResultSet rs= preparedStatement.executeQuery();
            if(rs.next()){
                return new Livre(rs.getString("numeroLivre"),rs.getString("titre"),
                        rs.getNString("auteurs"), rs.getNString("maisonEdition") ,
                        rs.getString("nombrePages") , rs.getString("prix"));
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

    //save Book function
    @Override
    public void save(Livre Livre) {
        Connection conn = DBConnection.getConnection();
        if(conn == null) return;

        if (Integer.parseInt(Livre.getNumeroLivre()) > 0){
            String query = "update livre set titre= ? ,auteurs=? ,maisonEdition=? ,nombrePages=?,prix=? where numeroLivre =?;";//update Book using ID
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, Livre.getTitre());
                preparedStatement.setString(2, Livre.getAuteurs());
                preparedStatement.setString(3, Livre.getMaisonEdition());
                preparedStatement.setString(4, Livre.getNombrePages());
                preparedStatement.setString(5, Livre.getPrix());
                preparedStatement.setString(6, Livre.getNumeroLivre());
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
            String query = "INSERT INTO livre (numeroLivre, titre, auteurs, maisonEdition, nombrePages, prix ) VALUES ( ? , ? , ?  ,? , ? , ? )";//create Book using ID
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, Livre.getNumeroLivre());
                preparedStatement.setString(2, Livre.getTitre());
                preparedStatement.setString(3, Livre.getAuteurs());
                preparedStatement.setString(4, Livre.getMaisonEdition());
                preparedStatement.setString(5, Livre.getNombrePages());
                preparedStatement.setString(6, Livre.getPrix());
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

    //delete Book using ID
    @Override
    public void deleteByNumeroLivre(int numeroLivre) {
        Connection con = DBConnection.getConnection();
        if(con == null)
            return;

        String sql = "DELETE FROM livre WHERE numeroLivre=?;";
        try {
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setInt(1, numeroLivre);
            prest.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }
}
