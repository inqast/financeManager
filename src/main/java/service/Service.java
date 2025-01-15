package service;

import java.security.MessageDigest;
import java.util.List;

import domain.Budget;
import domain.Category;
import domain.Operation;
import domain.Operation.OperationType;
import domain.User;
import domain.exception.AccessException;
import domain.exception.NotFoundException;

public class Service implements IService {
    private MessageDigest md;
    private UserRepo userRepo;
    private OperationRepo operationRepo;
    private CategoryRepo categoryRepo;

    public Service() {}

    @Override
    public String signUp(String login, String password) throws Exception {
        try {
            userRepo.getByLogin(login);

            throw new AccessException("already exists");
        } catch (NotFoundException e) {
            // Это ожидаемый сценарий.
        }

        User user = new User(login);
        String token = user.signUp(md, password);

        userRepo.create(user);

        return token;
    }

    @Override
    public String logIn(String login, String password) throws Exception {
        User user = userRepo.getByLogin(login);

        return user.login(md, password);
    }

    @Override
    public void addCategory(int limit, String name, String token) throws Exception {
        User user = userRepo.getByToken(token);

        Category category = new Category(limit, name, limit, user.getID());

        categoryRepo.create(category);
    }

    @Override
    public Budget addIncome(int amount, String categoryName, String token) throws Exception {
        User user = userRepo.getByToken(token);
        List<Category> categories = categoryRepo.getListForUser(user.getID());
        List<Operation> operations = operationRepo.getListForUser(user.getID());

        Category category;
        try {
            category = categoryRepo.getForUserByName(categoryName, user.getID());
        } catch (NotFoundException e) {
            category = categoryRepo.getCommonCategory(user.getID());
        }

        Operation operation = new Operation(amount, OperationType.INCOME, category.getID(), amount, user.getID());

        operationRepo.create(operation);
        operations.add(operation);

        return new Budget(operations, categories);
    }

    @Override
    public Budget addOutcome(int amount, String categoryName, String token) throws Exception {
        User user = userRepo.getByToken(token);
        List<Category> categories = categoryRepo.getListForUser(user.getID());
        List<Operation> operations = operationRepo.getListForUser(user.getID());

        Category category;
        try {
            category = categoryRepo.getForUserByName(categoryName, user.getID());
        } catch (NotFoundException e) {
            category = categoryRepo.getCommonCategory(user.getID());
        }

        Operation operation = new Operation(amount, OperationType.OUTCOME, category.getID(), amount, user.getID());

        operationRepo.create(operation);
        operations.add(operation);

        return new Budget(operations, categories);
    }

    @Override
    public Budget getBudget(String token) throws Exception {
        User user = userRepo.getByToken(token);
        List<Category> categories = categoryRepo.getListForUser(user.getID());
        List<Operation> operations = operationRepo.getListForUser(user.getID());

        return new Budget(operations, categories);
    }
}
