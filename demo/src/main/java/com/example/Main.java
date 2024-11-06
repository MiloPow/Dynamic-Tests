package com.example;

import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.tools.JavaCompiler;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Anwar\\Documents\\UWI\\COMP 3607\\Group Project\\Dynamic Tests\\demo\\src\\test\\java\\MyTestFactory.java";

        boolean compilationResult = compileFile(filePath);

        if (compilationResult) {
            runFile("MyTestFactory");
        } else {
            System.out.println("Compilation failed.");
        }
    }

    public static boolean compileFile(String filePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) return false;

        // Define the classpath to include dependencies, particularly JUnit
        String classpath = "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\jupiter\\junit-jupiter-api\\5.7.1\\junit-jupiter-api-5.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\platform\\junit-platform-commons\\1.7.1\\junit-platform-commons-1.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\apiguardian\\apiguardian-api\\1.1.0\\apiguardian-api-1.1.0.jar" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\platform\\junit-platform-launcher\\1.7.1\\junit-platform-launcher-1.7.1" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\jupiter\\junit-jupiter-engine\\5.7.1\\junit-jupiter-engine-5.7.1.jar";

        // Pass classpath and file to compile
        int compilationResult = compiler.run(null, null, null, "-classpath", classpath, filePath);

        return compilationResult == 0;
    }

    public static void runFile(String className) {
        String classpath = "C:\\Users\\Anwar\\Documents\\UWI\\COMP 3607\\Group Project\\Dynamic Tests\\demo\\src\\test\\java" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\platform\\junit-platform-commons\\1.7.1\\junit-platform-commons-1.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\apiguardian\\apiguardian-api\\1.1.0\\apiguardian-api-1.1.0.jar" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\platform\\junit-platform-launcher\\1.7.1\\junit-platform-launcher-1.7.1" +
                           File.pathSeparator + "C:\\Users\\Anwar\\.m2\\repository\\org\\junit\\jupiter\\junit-jupiter-engine\\5.7.1\\junit-jupiter-engine-5.7.1.jar";

        ProcessBuilder processBuilder = new ProcessBuilder("java", "-classpath", classpath, className);
        processBuilder.directory(new File("C:\\Users\\Anwar\\Documents\\UWI\\COMP 3607\\Group Project\\Dynamic Tests\\demo\\src\\test\\java"));

        try {
            Process p = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            int exitCode = p.waitFor();
            System.out.println("Process exited with code: " + exitCode);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
