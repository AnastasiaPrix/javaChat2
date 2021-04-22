package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthServise implements AuthService {


    private List<UserData> users;

    public SimpleAuthServise() throws SQLException {

        users = ConnectionService.selectExistUsers();

    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (UserData user : users) {
            if(user.login.equals(login) && user.password.equals(password)){
                return user.nickname;
            }
        }

        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        for (UserData user : users) {
            if(user.login.equals(login) || user.nickname.equals(nickname)){
                return false;
            }
        }

        users.add(new UserData(login, password, nickname));
        return true;
    }

}
