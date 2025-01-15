package http.handler.operation;

import com.sun.net.httpserver.HttpExchange;

import domain.Budget;
import http.handler.Handler;
import service.IService;

public class OperationHandler extends Handler {

    private IService service;

    public OperationHandler(IService service) {
        this.service = service;
    }

    @Override
    public String handleMethods(HttpExchange t) throws Exception {
        switch (t.getRequestMethod()) {
            case ("GET"):
                return handleIndex(t);
            case ("POST"):
            switch (getPathParam(t.getRequestURI(), 2)) {
                case "income":
                    return handleCreateIncome(t);
                case "outcome":
                    return handleCreateOutcome(t);
                default:
                    throw new AssertionError("unknown method");
            }
            default:
                throw new AssertionError("unknown method");
        }
    }

    private String handleCreateIncome(HttpExchange t) throws Exception {
        CreateReq req = new CreateReq(readBody(t), getAuthHeader(t));
        req.validate();

        Budget budget = service.addIncome(req.getAmount(), req.getCategory(), req.getToken());

        budget.validate();

        return "";
    }

    private String handleCreateOutcome(HttpExchange t) throws Exception {
        CreateReq req = new CreateReq(readBody(t), getAuthHeader(t));
        req.validate();

        Budget budget = service.addOutcome(req.getAmount(), req.getCategory(), req.getToken());

        budget.validate();

        return "";
    }

    private String handleIndex(HttpExchange t) throws Exception {
        IndexReq req = new IndexReq(getAuthHeader(t));
        req.validate();

        Budget budget = service.getBudget(req.getToken());

        IndexResp resp = new IndexResp(budget);

        return resp.toJSON();
    }
}
