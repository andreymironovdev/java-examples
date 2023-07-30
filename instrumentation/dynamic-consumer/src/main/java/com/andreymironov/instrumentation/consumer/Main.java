package com.andreymironov.instrumentation.consumer;

import com.sun.tools.attach.VirtualMachine;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.andreymironov.instrumentation.consumer.ClassToInstrument.*;

public class Main {
    private static final String JAVA_AGENT_PATH = "/Users/unknownuser/projects/java-examples/instrumentation/java-agent/build/libs/java-agent-1.0.0-SNAPSHOT.jar";

    public static void main(String[] args) {
        System.out.printf("Executing method %s...%n", ReflectionUtils.getCurrentMethodName());

        System.out.println("Invoking method before instrumentation...");
        methodToInstrument();

        attachJavaAgentToThisVM();

        System.out.println("Invoking method after instrumentation...");
        methodToInstrument();
    }

    private static void attachJavaAgentToThisVM() {
        try {
            VirtualMachine currentVM = VirtualMachine.attach(getCurrentVMPid());
            String javaAgentArgs = Stream.concat(
                    Stream.of(ClassToInstrument.class.getName()),
                    Arrays.stream(ClassToInstrument.class.getDeclaredMethods())
                            .map(Method::getName)
            ).collect(Collectors.joining(","));
            currentVM.loadAgent(JAVA_AGENT_PATH, javaAgentArgs);
            currentVM.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentVMPid() {
        String currentVMName = ManagementFactory.getRuntimeMXBean().getName();
        return currentVMName.substring(0, currentVMName.indexOf('@'));
    }
}
