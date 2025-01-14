package service;

import domain.User;

public interface UserRepo {
    public User getById(int userID) throws Exception;
    public User getByToken(String token) throws Exception;
    public void create(User user) throws Exception;
    public void update(User user) throws Exception;
}
