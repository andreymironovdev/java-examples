package com.andreymironov.designpatterns.bridge;

public class BridgeMain {
    /**
     * Client for underlying OS operations
     */
    public interface OSClient {
        String getHomeDir();
    }

    /**
     * Bridge from OSClient to platform specific methods
     */
    public interface OSApi {

        /**
         * @return path to home dir
         */
        String getHomePath();

    }

    public static class WindowsApi implements OSApi {

        @Override
        public String getHomePath() {
            return "C:\\\\Users\\\\john_doe";
        }

    }

    public static class LinuxApi implements OSApi {

        @Override
        public String getHomePath() {
            return "/home/john_doe";
        }

    }

    public static class WindowsOSCLient implements OSClient {
        private final WindowsApi api = new WindowsApi();

        @Override
        public String getHomeDir() {
            return api.getHomePath();
        }
    }

    public static class LinuxOSClient implements OSClient {
        private final LinuxApi api = new LinuxApi();

        @Override
        public String getHomeDir() {
            return api.getHomePath();
        }
    }

    public static void main(String[] args) {
        // use linux implementation
        OSClient client = new LinuxOSClient();
        client.getHomeDir();

        // use windows implementation
        client = new WindowsOSCLient();
        client.getHomeDir();
    }
}
