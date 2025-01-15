package http.handler.user;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Request;

public class UserReq extends Request {
    private String login;
    private String password;

    public UserReq(String body) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        login = parseString(obj, "login");
        password = parseString(obj, "password");
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (login.isEmpty()) {
            throw new InvalidRequestException("invalid login");        
        }

        if (password.isEmpty()) {
            throw new InvalidRequestException("invalid password");        
        }
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
