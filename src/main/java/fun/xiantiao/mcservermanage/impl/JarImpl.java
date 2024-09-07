package fun.xiantiao.mcservermanage.impl;

import fun.xiantiao.mcservermanage.api.Jar;
import fun.xiantiao.mcservermanage.utils.FileX;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarImpl extends File implements Jar {
    private final String jarFilePath;

    public JarImpl(@NotNull String pathname) {
        super(pathname);
        jarFilePath = pathname;
    }

    public List<String> listEntries() {
        List<String> entriesList = new ArrayList<>();
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                entriesList.add(entry.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entriesList;
    }

    public String readFile(String filePathInJar) {
        StringBuilder fileContent = new StringBuilder();
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            JarEntry entry = jarFile.getJarEntry(filePathInJar);
            if (entry != null) {
                try (InputStream inputStream = jarFile.getInputStream(entry);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append(System.lineSeparator());
                    }
                }
            } else {
                return "文件在 JAR 中未找到: " + filePathInJar;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "读取文件时出错: " + e.getMessage();
        }
        return fileContent.toString();
    }

    public String readResourceFromClasspath(String resourcePath) {
        // Ensure the path starts with '/'
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }

        StringBuilder resourceContent = new StringBuilder();
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                return "资源未找到: " + resourcePath;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    resourceContent.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "读取资源时出错: " + e.getMessage();
        }
        return resourceContent.toString();
    }

    @Override
    public FileX getFile() {
        return new FileX(jarFilePath);
    }
}
