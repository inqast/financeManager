package http.handler.user;

import com.sun.net.httpserver.HttpExchange;

import http.handler.Handler;
import service.IService;

public class UserHandler extends Handler {

    private IService service;

    public UserHandler(IService service) {
        this.service = service;
    }

    @Override
    public String handleMethods(HttpExchange t) throws Exception {
        switch (t.getRequestMethod()) {
            case ("POST"):
                switch (getPathParam(t.getRequestURI(), 2)) {
                    case "login":
                        return handleLogin(t);
                    case "signup":
                        return handleSignUp(t);
                    default:
                        throw new AssertionError("unknown method");
                }
            default:
                throw new AssertionError("unknown method");
        }
    }

    private String handleLogin(HttpExchange t) throws Exception {
        UserReq req = new UserReq(readBody(t));
        req.validate();

        String token = service.logIn(req.getLogin(), req.getPassword());

        UserResp resp = new UserResp(token);

        return resp.toJSON();
    }

    private String handleSignUp(HttpExchange t) throws Exception {
        UserReq req = new UserReq(readBody(t));
        req.validate();

        String token = service.signUp(req.getLogin(), req.getPassword());

        UserResp resp = new UserResp(token);

        return resp.toJSON();
    }
}
