package dataaccess;

import model.UserData;

import java.util.HashMap;

public class UserDAOMemory implements UserDAO {

    private final HashMap<String, UserData> userMap = new HashMap<>();

    @Override
    public void createUser(UserData userData) throws DataAccessException {
        userMap.put(userData.username(), userData);

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        if (userMap.containsKey(username)) {
            return userMap.get(username);
        }

        return null;
    }
}
