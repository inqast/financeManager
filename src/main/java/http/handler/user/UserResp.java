package http.handler.user;

import org.json.JSONObject;

import http.resonse.Response;

public class UserResp extends Response {
    private String token;

    public UserResp(String token) {
        this.token = token;
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
