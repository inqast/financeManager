package http.resonse.link;

import java.util.List;

import org.json.JSONObject;

import domain.link.Link;
import http.resonse.Response;

public class IndexResp extends Response {
    private List<Link> links;
    

    public IndexResp(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public List<Link> getLinks() {
        return links;
    }
}
