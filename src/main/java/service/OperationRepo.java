package service;

import java.util.List;

import domain.Operation;

public interface OperationRepo {
    public List<Operation> getListForUser(int userID) throws Exception;
    public void create(Operation operation) throws Exception;
}
