package http.resonse.link;

import org.json.JSONObject;

import http.resonse.Response;

public class GetResp extends Response {
    private String link;

    public GetResp(String link) {
        this.link = link;
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public String getLink() {
        return link;
    }
}
