package Dao;

import model.DVD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DVDDaoImpliment implements DVDDao{

    //find DVD using ID
    @Override
    public DVD findBynum(int numDvd) {
        Connection conn=DBConnection.getConnection();
        //test database connection
        if (conn==null) {
            return null;
        }

        String query="select * from dvd where numDvd=?;";//return DVD

        try(PreparedStatement preparedStatement=conn.prepareStatement(query)){

            preparedStatement.setInt(1, numDvd);
            ResultSet rs= preparedStatement.executeQuery();
            if(rs.next()){
                return new DVD(rs.getInt("numDvd"),rs.getString("nomFilm"),
                        rs.getNString("nomDocumentaire"), rs.getNString("nomRealisateur") ,
                        rs.getString("nomActeur") , rs.getString("nomEditeurdvd"),
                        rs.getString("nomProducteur"));
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

    //save DVD function
    @Override
    public void save(DVD dvd) {
        Connection conn = DBConnection.getConnection();
        if(conn == null) return;

        if (dvd.getNumDvd() > 0){
            String query = "update dvd set nomFilm=? ,nomDocumentaire=? ,nomRealisateur=?,nomActeur=? ,nomEditeurdvd=?, nomProducteur=? where numDvd =?;";//update DVD using ID
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, dvd.getNomFilm());
                preparedStatement.setString(2, dvd.getNomDocumentaire());
                preparedStatement.setString(3, dvd.getNomRealisateur());
                preparedStatement.setString(4, dvd.getNomActeur());
                preparedStatement.setString(5, dvd.getNomEditeurdvd());
                preparedStatement.setString(6, dvd.getNomProducteur());
                preparedStatement.setInt(7, dvd.getNumDvd());
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
            String query = "INSERT INTO dvd (numDvd, nomFilm, nomDocumentaire, nomRealisateur, nomActeur, nomEditeurdvd, nomProducteur ) VALUES ( ? , ? , ?  ,? , ? , ?, ? )";//create new DVD
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, dvd.getNumDvd());
                preparedStatement.setString(2, dvd.getNomFilm());
                preparedStatement.setString(3, dvd.getNomDocumentaire());
                preparedStatement.setString(4, dvd.getNomRealisateur());
                preparedStatement.setString(5, dvd.getNomActeur());
                preparedStatement.setString(6, dvd.getNomEditeurdvd());
                preparedStatement.setString(7, dvd.getNomProducteur());
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

    //delete DVD using ID
    @Override
    public void deleteByNumeroDvd(int numDvd) {
        Connection con = DBConnection.getConnection();
        if(con == null)
            return;

        String sql = "DELETE FROM dvd WHERE numDvd=?;";
        try {
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setInt(1, numDvd);
            prest.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }
}
