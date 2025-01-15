package http.handler.operation;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Request;

public class IndexReq extends Request {
    private String token;


    public IndexReq(String token) throws InvalidRequestException {
        this.token = token;
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (token.isEmpty()) {
            throw new InvalidRequestException("invalid token");        
        }
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public String getToken() {
        return token;
    }
}
