package com.andreymironov.yandex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Input has the following rows:
 * The first row is the number of methods, for example N
 * The rest N rows contains the name of the method, then the number of its dependencies, and then their names.
 * All values are divided by space
 * We need to output a single string, which contains all methods, and every method is listed after its dependencies
 */
// TODO: Not all Yandex tests were passed
public class RefactorUtils {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String countRow = scanner.nextLine();

        int totalCount = Integer.parseInt(countRow);

        List<MethodInfo> methodInfoList = new ArrayList<>();

        for (int i = 0; i < totalCount; i++) {
            String row = scanner.nextLine();
            String[] split = row.split(" ");
            String name = split[0];

            MethodInfo methodInfo = methodInfoList.stream()
                    .filter(m -> m.name.equals(name)).findFirst()
                    .orElseGet(() -> {
                        MethodInfo result = new MethodInfo(name, new ArrayList<>());
                        methodInfoList.add(result);
                        return result;
                    });

            int n = Integer.parseInt(split[1]);

            if (n > 0) {
                for (int j = 2; j < split.length; j++) {
                    String depName = split[j];

                    MethodInfo dep = methodInfoList.stream().filter(m -> m.name.equals(depName))
                                    .findFirst()
                                            .orElseGet(() -> {
                                                MethodInfo result = new MethodInfo(depName, new ArrayList<>());
                                                methodInfoList.add(result);
                                                return result;
                                            });
                    if (!methodInfo.deps.contains(dep)) {
                        methodInfo.deps.add(dep);
                    }
                }
            }
        }

        System.out.println(getMethodNamesToRefactor(methodInfoList));
    }

    public static class MethodInfo implements Comparable<MethodInfo> {
        public MethodInfo(String name, List<MethodInfo> deps) {
            this.name = name;
            this.deps = deps;
        }

        public String name;
        public List<MethodInfo> deps;

        @Override
        public int compareTo(MethodInfo another) {
            if (this.name.equals(another.name)) {
                return 0;
            }

            int thisSize = this.deps.size();
            int anotherSize = another.deps.size();

            if (thisSize == 0 || anotherSize == 0) {
                return thisSize - anotherSize;
            }

            if (this.deps.contains(another)) {
                return 1;
            }

            if (another.deps.contains(this)) {
                return -1;
            }

            for (int i = 0; i < this.deps.size(); i++) {
                MethodInfo dep = deps.get(i);

                if (dep.compareTo(another) > 0) {
                    return 1;
                }
            }

            return -1;
        }
    }

    /**
     * @param methodInfoList list of methods information
     * @return string in which every method is listed after its dependencies
     */
    public static String getMethodNamesToRefactor(List<MethodInfo> methodInfoList) {
        List<MethodInfo> sorted = methodInfoList.stream()
                .sorted()
                .collect(Collectors.toList());

        return sorted.stream()
                .map(i -> i.name)
                .collect(Collectors.joining(" "));
    }
}
