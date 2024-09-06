import fun.xiantiao.mcservermanage.utils.MissingCheck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static fun.xiantiao.mcservermanage.utils.MissingCheck.coreCommonVersions;

public class Main {
    private static final Logger logger = LogManager.getLogger(fun.xiantiao.mcservermanage.Main.class);

    public static void main(String[] args) throws IOException {
        MissingCheck<String> missingCheck = new MissingCheck<>(coreCommonVersions);

        missingCheck.add("1.12.2");

        logger.info(missingCheck.getMissing());
    }

    private void rename() {
        File directory = new File("/home/xiantiao/flies/Minecraft/ServerCore/ServerCore/Purpur/");
        File[] files = directory.listFiles();

        if (files == null) {
            System.err.println("目录不存在或无法读取");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();

                //String newName = name.replace(".jar", "-1.jar");
                String newName = "core-"+name;

                File renamedFile = new File(directory, newName);
                if (file.renameTo(renamedFile)) {
                    System.out.println("成功重命名文件: " + name + " -> " + newName);
                } else {
                    System.err.println("重命名失败: " + name);
                }
            }
        }
    }
}
