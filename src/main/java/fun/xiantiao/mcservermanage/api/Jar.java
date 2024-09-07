package fun.xiantiao.mcservermanage.api;

import fun.xiantiao.mcservermanage.utils.FileX;

import java.util.List;

public interface Jar {

    /**
     * 列出 JAR 文件中的所有条目
     *
     * @return JAR 文件中所有条目的列表
     */
    List<String> listEntries();

    /**
     * 读取 JAR 文件中的指定文件内容
     *
     * @param filePathInJar JAR 文件中指定文件的路径
     * @return 文件内容的字符串
     */
    String readFile(String filePathInJar);

    /**
     * 从类路径中读取指定资源的内容
     *
     * @param resourcePath 资源在类路径中的路径
     * @return 资源内容的字符串
     */
    String readResourceFromClasspath(String resourcePath);

    FileX getFile();
}
