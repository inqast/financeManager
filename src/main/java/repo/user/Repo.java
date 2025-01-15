package repo.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.User;
import domain.exception.DuplicateException;
import domain.exception.NotFoundException;

public class Repo implements service.UserRepo {
    private Connection con;

    public Repo(Connection con) {
        this.con = con;
    }

    @Override
    public User getByToken(String token) throws Exception {
        String query = "SELECT id, login, password_hash, token FROM users WHERE token = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, token);

            if (!statement.execute()) {
                throw new NotFoundException("user not found");
            }

            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                throw new NotFoundException("Пользователь не найден!");
            }            
        
            return new User(
                resultSet.getInt(Keys.ID),
                resultSet.getString(Keys.LOGIN),
                resultSet.getString(Keys.PASSWORD_HASH),
                resultSet.getString(Keys.TOKEN)
            );
        }
    }

    @Override
    public User getByLogin(String login) throws Exception {
        String query = "SELECT id, login, password_hash, token FROM users WHERE login = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, login);

            if (!statement.execute()) {
                throw new NotFoundException("user not found");
            }

            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                throw new NotFoundException("Пользователь не найден!");
            }
        
            return new User(
                resultSet.getInt(Keys.ID),
                resultSet.getString(Keys.LOGIN),
                resultSet.getString(Keys.PASSWORD_HASH),
                resultSet.getString(Keys.TOKEN)
            );
        }
    }

    @Override
    public void create(User user) throws Exception {
        String query = "INSERT INTO users (login, password_hash, token) " + 
        "VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getToken());

            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new DuplicateException("try again");
            }
        }
    }
}
