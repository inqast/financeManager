package http.request;

import org.json.JSONException;
import org.json.JSONObject;

import domain.exception.InvalidRequestException;

public abstract class Request {
    public abstract void validate() throws InvalidRequestException;
    public abstract String toJSON();

    protected String parseString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (JSONException e) {
            return "";
        }
    }

    protected int parseInt(String value) throws InvalidRequestException {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new InvalidRequestException("wrong int format");
        }
    }

    protected int parseInt(JSONObject obj, String key)  throws InvalidRequestException {
        try {
            return obj.getInt(key);
        } catch (JSONException e) {
            return 0;
        } catch (Exception e) {
            throw new InvalidRequestException("wrong int format");
        }
    }
}
