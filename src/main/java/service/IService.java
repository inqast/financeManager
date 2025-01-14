package service;

import domain.User;

public interface IService {
    public User signUp(String login, String password) throws Exception;
    public User login(String login, String password) throws Exception;
    public void addIncome(int amount, String category, String token) throws Exception;
    public void addOutcome(int amount, String category, String token) throws Exception;
    public void addCategory(int limit, String name, String token) throws Exception;
    public void getBudget(String token) throws Exception;
}