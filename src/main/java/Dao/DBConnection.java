package Dao;


import java.sql.*;

public class DBConnection {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DB_NAME = "bibliotheque";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    //setconnection to data base
    public static Connection getConnection(){
        try {
            connection= DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static int checkConnectionlogin(String name, String password){

        Connection con=DBConnection.getConnection();
        if(con == null) return -1;//error connection

        String sql="SELECT  name, password FROM bibusers WHERE name=? AND password=?";

        try{
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet rs= preparedStatement.executeQuery();

            while(rs.next()) {
                //scucces
                return 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return 1;
    }


}
