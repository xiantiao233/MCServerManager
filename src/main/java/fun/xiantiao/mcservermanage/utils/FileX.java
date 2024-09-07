package fun.xiantiao.mcservermanage.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static fun.xiantiao.mcservermanage.Main.getLogger;

public class FileX extends File {
    public FileX(@NotNull String pathname) {
        super(pathname);
    }
    public FileX(@NotNull File file) {
        super(file.getAbsolutePath());
    }

    public FileX[] listFiles() {
        File[] files = super.listFiles();
        if (files == null) return new FileX[0];
        FileX[] fileXs = new FileX[files.length];
        for (int i = 0; i < files.length; i++) {
            fileXs[i] = new FileX(files[i]);
        }
        return fileXs;
    }

    /**
     * 获取指定目录下的所有文件
     *
     * @param maxDepth      最大层级深度，-1 表示不限制
     * @return 文件列表
     */
    public List<FileX> allListFiles(int maxDepth) {
        List<FileX> fileList = new ArrayList<>();

        // 创建一个访问器以遍历文件树
        try {
            Files.walkFileTree(this.toPath(), new SimpleFileVisitor<Path>() {
                private int currentDepth = 0;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // 检查是否超过最大层级深度
                    if (maxDepth >= 0 && currentDepth > maxDepth) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    currentDepth++;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileX fileX = new FileX(file.toFile());
                    fileList.add(fileX);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    currentDepth--;
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            getLogger().error(e);
        }

        return fileList;
    }

    public boolean renameTo(@NotNull String newPath) {
        File newFile = new File(newPath);

        // Check if the new path is a directory
        if (newFile.isDirectory()) {
            getLogger().debug("Error: The new path is a directory, not a file.[{}]", newPath);
            return false;
        }

        // Attempt to rename
        boolean success = this.renameTo(newFile);
        if (!success) {
            getLogger().debug("Rename failed: {} -> {}", this.getAbsolutePath(), newPath);
        }
        return success;
    }

    public boolean moveFile(File target) {
        return moveFile(target.getAbsolutePath());
    }
    public boolean moveFile(String target) {
        Path targetPath = new File(target+"/"+this.getName()).toPath();
        try {
            Files.move(this.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            getLogger().debug("File moved successfully. {} -> {}", this.getAbsolutePath(), targetPath);
            return true;
        } catch (IOException e) {
            getLogger().error(e);
            getLogger().debug("Failed to move file. {} -> {}", this.getAbsolutePath(), targetPath);
            return false;
        }
    }

    public boolean copyFile(File file) {
        return copyFile(file.getAbsolutePath());
    }
    public boolean copyFile(String target) {
        Path targetPath = new File(target+"/"+this.getName()).toPath();
        try {
            Files.copy(this.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            getLogger().debug("File copied successfully. {} -> {}", this.getAbsolutePath(), targetPath);
            return true;
        } catch (IOException e) {
            getLogger().error(e);
            getLogger().debug("Failed to copy file. {} -> {}", this.getAbsolutePath(), targetPath);
            return false;
        }
    }
}
