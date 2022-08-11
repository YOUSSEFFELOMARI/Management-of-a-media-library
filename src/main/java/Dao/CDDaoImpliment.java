package Dao;

import model.CD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CDDaoImpliment implements CDDao {
    //find CD using ID
    @Override
    public CD findBynum(int numCd) {
        Connection conn=DBConnection.getConnection();
        //test database connection
        if (conn==null) {
            return null;
        }
        String query="select * from cd where numCd=?;";//return CD

        try(PreparedStatement preparedStatement=conn.prepareStatement(query)){

            preparedStatement.setInt(1, numCd);
            ResultSet rs= preparedStatement.executeQuery();
            if(rs.next()){
                return new CD(rs.getInt("numCd"),rs.getString("nomAlbum"),
                        rs.getNString("nomInterprete"), rs.getNString("nomEditeur"));
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
    public void save(CD cd) {
        Connection conn = DBConnection.getConnection();
        if(conn == null) return;

        if (cd.getNumCd() > 0){
            String query = "updae cd set nomAlbum=? ,nomInterprete=? ,nomEditeur=? where numCd =?;";//update CD using ID
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, cd.getNomAlbum());
                preparedStatement.setString(2, cd.getNomInterprete());
                preparedStatement.setString(3, cd.getNomEditeur());
                preparedStatement.setInt(7, cd.getNumCd());
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
            String query = "INSERT INTO cd (numCd, nomAlbum, nomInterprete, nomEditeur ) VALUES ( ? , ? , ?  ,? )";//create new CD
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, cd.getNumCd());
                preparedStatement.setString(2, cd.getNomAlbum());
                preparedStatement.setString(3, cd.getNomInterprete());
                preparedStatement.setString(4, cd.getNomEditeur());
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
    //delete CD using ID
    @Override
    public void deleteByNumeroCd(int numCd){
        Connection con = DBConnection.getConnection();
        if(con == null)
            return;

        String sql = "DELETE FROM cd WHERE numCd=?;";
        try {
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setInt(1, numCd);
            prest.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }
}
