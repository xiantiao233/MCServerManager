package fun.xiantiao.mcservermanage.impl;

import fun.xiantiao.mcservermanage.api.MCServerCore;
import fun.xiantiao.mcservermanage.utils.FileX;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MCServerCoreImpl extends JarImpl implements MCServerCore {

    private final FileX file;

    public MCServerCoreImpl(@NotNull FileX file) {
        super(file.getAbsolutePath());
        this.file = file;
    }


    public MCServerCoreImpl(@NotNull String pathname) {
        super(pathname);
        file = new FileX(pathname);
    }

    @Override
    public String getCoreName() {
        return split(1);
    }

    @Override
    public String getGameVersion() {
        return split(2);

    }

    @Override
    public String getBuildVersion() {
        return split(3);

    }

    private String split(int i) {
        String name = file.getName().replaceAll(".jar","");
        String[] split = name.split("-");
        if (split.length != 4 || !"core".equals(split[0])) throw new IllegalCallerException("jar 文件错误["+file+"]");
        else return split[i];
    }

}
