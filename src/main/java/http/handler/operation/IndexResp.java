package http.handler.operation;

import org.json.JSONObject;

import domain.Budget;
import http.resonse.Response;

public class IndexResp extends Response {
    private Budget budget;

    public IndexResp(Budget budget) {
        this.budget = budget;
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public Budget getBudget() {
        return budget;
    }
}
