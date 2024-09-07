package fun.xiantiao.mcservermanage.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
