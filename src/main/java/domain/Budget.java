package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Operation.OperationType;
import domain.exception.InvalidBudgetException;

public class Budget {
    private Map<Integer, Category> categories;
    private Map<Integer, List<Operation>> incomes;
    private Map<Integer, List<Operation>> outcomes;

    public Budget(List<Operation> operationsList, List<Category> categoriesList) {

        categories = new HashMap<>();
        for (Category category : categoriesList) {
            categories.put(category.getID(), category);
        }

        incomes = new HashMap<>();
        outcomes = new HashMap<>();

        for (Operation operation : operationsList) {
            if (operation.getType() == OperationType.INCOME) {
                if (!incomes.containsKey(operation.getCategoryID())) {
                    incomes.put(operation.getCategoryID(), new ArrayList<>());
                }

                incomes.get(operation.getCategoryID()).add(operation);
            } else if (operation.getType() == OperationType.OUTCOME) {
                if (!outcomes.containsKey(operation.getCategoryID())) {
                    outcomes.put(operation.getCategoryID(), new ArrayList<>());
                }

                outcomes.get(operation.getCategoryID()).add(operation);
            }
        }
    }

    public int getTotalIncome() {
        int total = 0;

        for (var entry : incomes.entrySet()) {
            for (Operation operation : entry.getValue()) {
                total += operation.getAmount();
            }
        }  

        return total;
    }

    public Map<String, Integer> getIncomeByCategory() {
        Map<String, Integer> result = new HashMap<>();

        for (var entry : categories.entrySet()) {
            int total = 0;
            for (Operation operation : incomes.get(entry.getKey())) {
                total += operation.getAmount();
            }

            result.put(entry.getValue().getName(), total);
        }  

        return result;
    }

    public int getTotalOutcome() {
        int total = 0;
        
        for (var entry : outcomes.entrySet()) {
            for (Operation operation : entry.getValue()) {
                total += operation.getAmount();
            }
        }  

        return total;
    }

    public List<Position> getOutcomeByCategory() {
        List<Position> result = new ArrayList<>();

        for (var entry : categories.entrySet()) {
            Category category = entry.getValue();

            Position position = new Position(category.getName(), category.getAmount());


            for (Operation operation : incomes.get(entry.getKey())) {
                position.append(operation.getAmount());
            }

            result.add(position);
        }  

        return result;
    }

    public void validate() throws InvalidBudgetException {
        if (getTotalIncome() < getTotalOutcome()) {
            throw new InvalidBudgetException("Расходы превысили доходы!");
        }

        for (var entry : categories.entrySet()) {
            Category category = entry.getValue();

            if (getOutcomeByCategory(getOutcomeByCategory(category.getID())) > category.getAmount()) {
                throw new InvalidBudgetException("Расходы превысили доходы в категории " + category.getName());
            }
        }  
    }

    public int getOutcomeByCategory(int categoryId) {
        int total = 0;
        
        for (Operation operation : outcomes.get(categoryId)) {
            total += operation.getAmount();
        }

        return total;
    }

    public class Position {
        private String category;
        private int amount;
        private int remaining;

        public Position(String category, int remaining) {
            this.category = category;
            this.remaining = remaining;
        }

        public void append(int amount) {
            this.amount += amount;
            this.remaining -= amount;
        }

        public String getCategory() {
            return category;
        }

        public int getAmount() {
            return this.amount;
        }

        public int getRemaining() {
            return this.remaining;
        }
    }
}
