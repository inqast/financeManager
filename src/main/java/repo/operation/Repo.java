package repo.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Operation;
import domain.Operation.OperationType;
import domain.exception.DuplicateException;
import domain.exception.InvalidArgumentException;
import domain.exception.NotFoundException;
import service.OperationRepo;

public class Repo implements OperationRepo {

    private Connection con;

    public Repo(Connection con) {
        this.con = con;
    }

    @Override
    public List<Operation> getListForUser(int userID) throws Exception {
        String query = "SELECT id, type, category_id, amount, owner FROM operations WHERE owner = ?";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userID);

            if (!statement.execute()) {
                throw new NotFoundException("operations not found");
            }

            ResultSet resultSet = statement.getResultSet();
            ArrayList<Operation> operations = new ArrayList<>();
        
            while (resultSet.next()) {
                Operation operation = new Operation(
                    resultSet.getInt(Keys.ID),
                    getTypeFromString(resultSet.getString(Keys.TYPE)),
                    resultSet.getInt(Keys.CATEGORY_ID),
                    resultSet.getInt(Keys.AMOUNT),
                    resultSet.getInt(Keys.OWNER)
                );

                operations.add(operation);
            }            

            return operations;
        }
    }

    @Override
    public void create(Operation operation) throws Exception {
        String query = "INSERT INTO operations (type, category_id, amount, owner) " + 
        "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, getStringFromType(operation.getType()));
            statement.setInt(2, operation.getCategoryID());
            statement.setInt(3, operation.getAmount());
            statement.setInt(4, operation.getUserID());

            statement.execute();

            if (statement.getUpdateCount() == 0) {
                throw new DuplicateException("try again");
            }
        }
    }

    private String getStringFromType(Operation.OperationType type) throws InvalidArgumentException {
        switch (type) {
            case INCOME:
                return "income";
            case OUTCOME:
                return "outcome";
            default:
                throw new InvalidArgumentException("Неверный тип операции");
        }
    }

    private Operation.OperationType getTypeFromString(String type) throws InvalidArgumentException {
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
