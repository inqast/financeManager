package http.resonse.link;

import java.util.UUID;

import org.json.JSONObject;

import http.resonse.Response;

public class CreateResp extends Response {
    private int hash;
    private UUID user;

    public CreateResp(int hash, UUID user) {
        this.hash = hash;
        this.user = user;
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public int getHash() {
        return hash;
    }

    public UUID getUser() {
        return user;
    }
}
