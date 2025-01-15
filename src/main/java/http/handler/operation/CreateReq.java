package http.handler.operation;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Request;

public class CreateReq extends Request {
    private int amount;
    private String category;
    private String token;


    public CreateReq(String body, String token) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        this.amount = parseInt(obj, "amount");
        this.category = parseString(obj, "category");
        this.token = token;
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (amount < 0) {
            throw new InvalidRequestException("invalid amount");        
        }

        if (category.isEmpty()) {
            throw new InvalidRequestException("invalid category");        
        }

        if (token.isEmpty()) {
            throw new InvalidRequestException("invalid token");        
        }
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getToken() {
        return token;
    }
}
