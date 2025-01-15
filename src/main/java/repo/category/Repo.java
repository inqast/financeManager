package repo.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Category;
import domain.exception.DuplicateException;
import domain.exception.NotFoundException;
import service.CategoryRepo;

public class Repo implements CategoryRepo {
    private Connection con;

    public Repo(Connection con) {
        this.con = con;
    }

    @Override
    public List<Category> getListForUser(int userID) throws Exception {
        String query = "SELECT id, name, limit, owner FROM categories WHERE owner = ?";
        
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
        String query = "SELECT id, name, limit, owner FROM categories WHERE name = ? AND owner = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, userID);


            if (!statement.execute()) {
                throw new NotFoundException("category not found");
            }

            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
        
            return new Category(
                resultSet.getInt(Keys.ID),
                resultSet.getString(Keys.NAME),
                resultSet.getInt(Keys.LIMIT),
                resultSet.getInt(Keys.OWNER)
            );
        }
    }

    @Override
    public void create(Category category) throws Exception {
        String query = "INSERT INTO categories (name, limit, owner) " + 
        "VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getAmount());
            statement.setInt(3, category.getUserID());

            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new DuplicateException("try again");
            }
        }
    }
}
