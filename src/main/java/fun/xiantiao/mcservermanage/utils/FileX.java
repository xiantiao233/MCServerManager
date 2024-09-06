package fun.xiantiao.mcservermanage.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

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
}
