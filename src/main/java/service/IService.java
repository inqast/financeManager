package service;

import domain.Budget;
import domain.OperationType;

public interface IService {
    public String signUp(String login, String password) throws Exception;
    public String logIn(String login, String password) throws Exception;
    public void addCategory(int limit, String name, OperationType type, String token) throws Exception;
    public Budget addIncome(int amount, String categoryName, String token) throws Exception;
    public Budget addOutcome(int amount, String categoryName, String token) throws Exception;
    public Budget getBudget(String token) throws Exception;
}