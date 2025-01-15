package http.handler.category;

import com.sun.net.httpserver.HttpExchange;

import http.handler.Handler;
import service.IService;

public class CategoryHandler extends Handler {

    private IService service;

    public CategoryHandler(IService service) {
        this.service = service;
    }

    @Override
    public String handleMethods(HttpExchange t) throws Exception {
        switch (t.getRequestMethod()) {
            case ("POST"):
                return handleCreate(t);
            default:
                throw new AssertionError("unknown method");
        }
    }

    private String handleCreate(HttpExchange t) throws Exception {
        CreateReq req = new CreateReq(readBody(t), getAuthHeader(t));
        req.validate();

        service.addCategory(req.getLimit(), req.getName(), req.getToken());

        return "";
    }
}
