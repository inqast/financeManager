package service;

import domain.User;

public class Service implements IService {
    private UserRepo userRepo;
    private OperationRepo operationRepo;
    private CategoryRepo categoryRepo;


    private static final long MILLISECONDS_IN_DAY = 86400000;

    public Service() {}

    @Override
    public User signUp(String login, String password) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public User login(String login, String password) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void addIncome(int amount, String category, String token) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void addOutcome(int amount, String category, String token) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void addCategory(int limit, String name, String token) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void getBudget(String token) throws Exception {
        throw new Exception("not implemented");
    }
}
