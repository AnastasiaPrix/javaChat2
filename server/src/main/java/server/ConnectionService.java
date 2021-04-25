package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConnectionService {

    private static Connection connection;
    private static Statement stmt;

    public static void Connection() throws SQLException {
        if (connection == null) {
            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/hbstudent/chat", "hbstudent", "hbstudent");
                stmt = connection.createStatement();
                System.out.println(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<UserData> selectExistUsers() throws SQLException {
        List<UserData> users = new ArrayList<>();
        String sql = String.format("SELECT * FROM users;");
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res);
            users.add( new UserData(res.getString("login"),res.getString("password"),res.getString("nickname")));
        }
        return users;
    }

    private static void insertEntity(String login, String pasword, String nickname) throws SQLException {
        PreparedStatement ps ;
        ps = connection.prepareStatement("INSERT INTO users (login,password,nickname) VALUES (?, ?, ?);");
        ps.setString(1, login);
        ps.setString(2,pasword);
        ps.setString(3,nickname);
        ps.executeQuery();
    }
    public static void insertIntoBlackList(String user, String blackUser)  {
        PreparedStatement ps ;
        try {
            ps = connection.prepareStatement("INSERT INTO blacklist (user,blackUser) VALUES (?, ?);");
            ps.setString(1, user);
            ps.setString(2,blackUser);
            ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void deleteFromBlackList(String user, String blackUser)  {
        PreparedStatement ps ;
        try {
            ps = connection.prepareStatement("DELETE FROM blacklist where VALUES (?, ?);");
            ps.setString(1, user);
            ps.setString(2,blackUser);
            ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<String> getBlackList(String user) {
        PreparedStatement ps ;
        List<String> blacklist = new ArrayList<>();
        ResultSet res = null;
        try {
            ps = connection.prepareStatement("DELETE FROM blacklist where blackuser=?");
            ps.setString(1, user);
            res = ps.executeQuery();
        while (res.next()) {
            System.out.println(res);
            blacklist.add(res.getString(3));
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return blacklist;
    }
}
