package http.request.link;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Keys;
import http.request.Request;

public class CreateReq extends Request {
    private String link;
    private UUID user;
    private int limit;
    private Date deadline;

    public CreateReq(String body) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        link = parseString(obj, Keys.LINK);
        limit = parseInt(obj, Keys.LIMIT);
        user = parseUUID(obj,Keys.USER);
        deadline = parseDate(obj, Keys.DEADLINE);
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (link.isEmpty()) {
            throw new InvalidRequestException("invalid link");        
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

    public String getLink() {
        return link;
    }

    public int getLimit() {
        return limit;
    }

    public UUID getUser() {
        return user;
    }

    public Date getDeadline() {
        return deadline;
    }
}
