package service;

import domain.User;

public interface UserRepo {
    public User getByToken(String token) throws Exception;
    public User getByLogin(String login) throws Exception;
    public void create(User user) throws Exception;
}
