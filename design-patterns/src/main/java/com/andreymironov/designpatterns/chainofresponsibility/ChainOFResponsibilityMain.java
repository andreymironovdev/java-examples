package com.andreymironov.designpatterns.chainofresponsibility;

import java.util.List;

public class ChainOFResponsibilityMain {
    public enum RequestType {
        POST, GET
    }

    public static class Request {
        final RequestType type;
        final String path;

        public Request(RequestType type, String path) {
            this.type = type;
            this.path = path;
        }
    }

    public interface RequestHandler {
        RequestType getSupportedType();

        void handle(Request request);
    }

    public static class GetRequestHandler implements RequestHandler {

        @Override
        public RequestType getSupportedType() {
            return RequestType.GET;
        }

        @Override
        public void handle(Request request) {
            System.out.println("Handling GET request...");
        }
    }

    public static class PostRequestHandler implements RequestHandler {

        @Override
        public RequestType getSupportedType() {
            return RequestType.POST;
        }

        @Override
        public void handle(Request request) {
            System.out.println("Handling POST request...");
        }
    }

    public static class RequestController {
        private final List<RequestHandler> handlers = List.of(
                new GetRequestHandler(),
                new PostRequestHandler()
        );

        public void process(Request request) {
            RequestHandler handler = handlers.stream()
                    .filter(h -> h.getSupportedType() == request.type)
                    .findFirst()
                    .orElseThrow();

            handler.handle(request);
        }
    }

    public static void main(String[] args) {
        Request getRequest = new Request(RequestType.GET, "/api");
        Request postRequest = new Request(RequestType.POST, "/api");
        RequestController controller = new RequestController();
        controller.process(getRequest);
        controller.process(postRequest);
    }
}
