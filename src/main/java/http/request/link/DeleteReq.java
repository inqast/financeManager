package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Keys;
import http.request.Request;

public class DeleteReq extends Request {
    private int hash;
    private UUID user;

    public DeleteReq(String body) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        hash = parseInt(obj, Keys.HASH);
        user = parseUUID(obj, Keys.USER);
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (hash == 0) {
            throw new InvalidRequestException("invalid hash");        
        }

        if (user == null) {
            throw new InvalidRequestException("invalid user");        
        }
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
