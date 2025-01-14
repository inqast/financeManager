package http.request;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

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

    protected UUID parseUUID(JSONObject obj, String key) throws InvalidRequestException {
        try {
            String s = obj.getString(key);
            
            return UUID.fromString(s);
        } catch (JSONException e) {
            return UUID.randomUUID();
        } catch (Exception e) {
            throw new InvalidRequestException("wrong UUID format");
        }
    }

    protected UUID parseUUID(String value) throws InvalidRequestException {
        try {            
            return UUID.fromString(value);
        } catch (Exception e) {
            throw new InvalidRequestException("wrong UUID format");
        }
    }

    protected Date parseDate(JSONObject obj, String key) throws InvalidRequestException {
        try {
            String s = obj.getString(key);
            
            return DateFormat.getDateInstance(DateFormat.SHORT).parse(s);
        } catch (JSONException e) {
            return null;
        } catch (Exception e) {
            throw new InvalidRequestException("wrong date format, dd/mm/yy expected");
        }
    }
}
