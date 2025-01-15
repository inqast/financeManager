package http.handler.category;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Request;

public class CreateReq extends Request {
    private int limit;
    private String name;
    private String token;


    public CreateReq(String body, String token) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        this.limit = parseInt(obj, "limit");
        this.name = parseString(obj, "name");
        this.token = token;
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (name.isEmpty()) {
            throw new InvalidRequestException("invalid name");        
        }

        if (limit < 0) {
            throw new InvalidRequestException("invalid limit");        
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

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
