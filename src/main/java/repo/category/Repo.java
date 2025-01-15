package repo.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Category;
import domain.OperationType;
import domain.exception.DuplicateException;
import domain.exception.InvalidArgumentException;
import domain.exception.NotFoundException;
import service.CategoryRepo;

public class Repo implements CategoryRepo {
    private Connection con;

    public Repo(Connection con) {
        this.con = con;
    }

    @Override
    public List<Category> getListForUser(int userID) throws Exception {
        String query = "SELECT id, type, name, amount_limit, owner FROM categories WHERE owner = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userID);

            if (!statement.execute()) {
                throw new NotFoundException("categories not found");
            }

            ResultSet resultSet = statement.getResultSet();
            ArrayList<Category> categories = new ArrayList<>();
        
            while (resultSet.next()) {
                Category category = new Category(
                    resultSet.getInt(Keys.ID),
                    getTypeFromString(resultSet.getString(Keys.TYPE)),
                    resultSet.getString(Keys.NAME),
                    resultSet.getInt(Keys.LIMIT),
                    resultSet.getInt(Keys.OWNER)
                );

                categories.add(category);
            }            

            return categories;
        }
    }

    @Override
    public Category getForUserByName(String name, int userID) throws Exception {
        String query = "SELECT id, type, name, amount_limit, owner FROM categories WHERE name = ? AND owner = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, userID);


            if (!statement.execute()) {
                throw new NotFoundException("category not found");
            }

            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                throw new NotFoundException("Категория не найдена!");
            }
            
            return new Category(
                resultSet.getInt(Keys.ID),
                getTypeFromString(resultSet.getString(Keys.TYPE)),
                resultSet.getString(Keys.NAME),
                resultSet.getInt(Keys.LIMIT),
                resultSet.getInt(Keys.OWNER)
            );
        }
    }

    @Override
    public void create(Category category) throws Exception {
        String query = "INSERT INTO categories (type, name, amount_limit, owner) " + 
        "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, getStringFromType(category.getType()));
            statement.setString(2, category.getName());
            statement.setInt(3, category.getAmount());
            statement.setInt(4, category.getUserID());

            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new DuplicateException("try again");
            }
        }
    }

    private String getStringFromType(OperationType type) throws InvalidArgumentException {
        switch (type) {
            case INCOME:
                return "income";
            case OUTCOME:
                return "outcome";
            default:
                throw new InvalidArgumentException("Неверный тип операции");
        }
    }

    private OperationType getTypeFromString(String type) throws InvalidArgumentException {
        switch (type) {
            case "income":
                return OperationType.INCOME;
            case "outcome":
                return OperationType.OUTCOME;
            default:
                throw new InvalidArgumentException("Неверный тип операции");
        }
    }
}
