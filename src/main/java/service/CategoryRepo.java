package service;

import java.util.List;

import domain.Category;

public interface CategoryRepo {
    public List<Category> getListForUser(int userID) throws Exception;
    public Category getForUserByName(String name, int userID) throws Exception;
    public void create(Category category) throws Exception;
}
