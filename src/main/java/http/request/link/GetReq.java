package http.request.link;

import org.json.JSONObject;

import domain.exception.InvalidRequestException;
import http.request.Request;

public class GetReq extends Request {
    private int hash;

    public GetReq(String hash) throws InvalidRequestException {
        this.hash = parseInt(hash);
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (hash == 0) {
            throw new InvalidRequestException("invalid hash");        
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
}
