package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Request;

public class IndexReq extends Request {
    private UUID user;

    public IndexReq(String user) throws InvalidRequestException {
        this.user = parseUUID(user);
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (user == null) {
            throw new InvalidRequestException("invalid link");
        }
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public UUID getUser() {
        return user;
    }
}
