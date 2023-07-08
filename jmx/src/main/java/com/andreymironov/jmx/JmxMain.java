package com.andreymironov.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * When app started, go to jconsole or Visualvm (with MBeans plugin), connect and find related MBean
 */
public class JmxMain {
    public interface ImportantLogSupplierMBean {
        String supply();
    }

    public static class ImportantLogSupplier implements ImportantLogSupplierMBean {

        @Override
        public String supply() {
            System.out.println("Supplying important log");
            return "Important log";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting JmxMain...");
        addMBean();
        Thread.sleep(Long.MAX_VALUE);
    }

    private static void addMBean() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("com.andreymironov.jmx:name=importantLogSupplier");
            server.registerMBean(new ImportantLogSupplier(), objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                 MBeanRegistrationException | NotCompliantMBeanException e) {
            System.out.println(e.getMessage());
        }
    }
}
