package com.andreymironov.annotationprocessing;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("com.andreymironov.annotationprocessing.Arithmetic")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ArithmeticImplementationGeneratingAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element interfaceElem : annotatedElements) {
                ClassImplementationBuilder implementationBuilder = new ClassImplementationBuilder(interfaceElem);
                TypeSpec newClass = implementationBuilder.buildClassImplementation();
                String packageName = implementationBuilder.buildPackageName();

                JavaFile javaFile = JavaFile.builder(packageName, newClass).build();
                try {
                    javaFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }
}
