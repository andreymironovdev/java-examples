package com.andreymironov.annotationprocessing;

import com.squareup.javapoet.*;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassImplementationBuilder {
    private final Element interfaceElement;

    public ClassImplementationBuilder(Element element) {
        ElementKind kind = element.getKind();
        if (kind != ElementKind.INTERFACE) {
            throw new IllegalArgumentException("Cannot construct arithmetic implementation from a kind=" + kind);
        }

        this.interfaceElement = element;
    }

    public String buildPackageName() {
        Element enclosingElement = interfaceElement.getEnclosingElement();

        while (enclosingElement.getKind() != ElementKind.PACKAGE) {
            enclosingElement = interfaceElement.getEnclosingElement();
        }

        return enclosingElement.toString();
    }

    public TypeSpec buildClassImplementation() {
        return TypeSpec
                .classBuilder(buildClassName())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(interfaceElement.asType())
                .addMethods(buildMethods())
                .build();
    }

    private String buildClassName() {
        return interfaceElement.getSimpleName().toString() + "Impl";
    }

    private List<MethodSpec> buildMethods() {
        return interfaceElement.getEnclosedElements().stream()
//                Do not implement static methods
                .filter(m -> !m.getModifiers().contains(Modifier.STATIC))
                .map(this::buildMethod)
                .collect(Collectors.toList());
    }

    private MethodSpec buildMethod(Element method) {
        ExecutableElement executableElement = (ExecutableElement) method;

        if (executableElement.getReturnType().getKind() != TypeKind.INT) {
            throw new IllegalArgumentException("Cannot process method with return type other than int");
        }

        ParameterSpec parameterSpec = buildParameter(executableElement);
        return MethodSpec.methodBuilder(method.getSimpleName().toString())
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(parameterSpec)
                .addCode(buildCode(executableElement, parameterSpec))
                .returns(TypeName.INT)
                .build();
    }

    private ParameterSpec buildParameter(ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();

        if (parameters.size() != 1) {
            throw new IllegalArgumentException("Cannot process method with parameters size other than 1");
        }

        VariableElement parameter = parameters.get(0);

        if (parameter.asType().getKind() != TypeKind.INT) {
            throw new IllegalArgumentException("Cannot process method with parameter type other than int");
        }

        return ParameterSpec.builder(TypeName.INT, parameter.getSimpleName().toString()).build();
    }

    private static final Map<String, String> OPERATION_TO_SIGN = Map.of(
            "add", "+",
            "subtract", "-",
            "multiplyby", "*",
            "divideby", "/"
    );

    private static final String NEGATE_OPERATION = "negate";

    private CodeBlock buildCode(ExecutableElement method, ParameterSpec parameterSpec) {
        CodeBlock.Builder codeBuilder = CodeBlock.builder();
        String parameterName = parameterSpec.name;
        codeBuilder.addStatement("int result = " + parameterName);

        String methodName = method.getSimpleName().toString().toLowerCase();

        whileloop:
        while (!methodName.isEmpty()) {
            if (methodName.startsWith(NEGATE_OPERATION)) {
                codeBuilder.addStatement("result = -result");
                methodName = methodName.substring(NEGATE_OPERATION.length());
            } else {
                for (String operation : OPERATION_TO_SIGN.keySet()) {
                    if (methodName.startsWith(operation)) {
                        methodName = methodName.substring(operation.length());

                        int ciphersIndex = -1;
                        while (ciphersIndex + 1 < methodName.length() && Character.isDigit(
                                methodName.charAt(ciphersIndex + 1))) {
                            ciphersIndex++;
                        }
                        if (ciphersIndex < 0) {
                            throw new IllegalArgumentException("Number is absent for operation " + operation);
                        }
                        codeBuilder.addStatement(
                                "result = result " + OPERATION_TO_SIGN.get(operation) + " " + methodName.substring(0,
                                        ciphersIndex + 1));

                        methodName = methodName.substring(ciphersIndex + 1);
                        continue whileloop;
                    }
                }

                throw new IllegalArgumentException("Unknown operation for method name part " + methodName);
            }
        }

        codeBuilder.addStatement("return result");
        return codeBuilder.build();
    }
}
