package com.andreymironov.designpatterns.delegation;

public class DelegationMain {
    public static class Request {

    }

    public static class RequestHandlerService {
        void handle(Request request) {
            System.out.println("handling request...");
        }
    }

    public static class RequestHandlerController {
        private final RequestHandlerService service;

        public RequestHandlerController(RequestHandlerService service) {
            this.service = service;
        }

        public void handle(Request request) {
            System.out.println("Delegating request handling to service...");
            service.handle(request);
        }
    }

    public static void main(String[] args) {
        RequestHandlerController controller = new RequestHandlerController(new RequestHandlerService());
        controller.handle(new Request());
    }
}
