package fun.xiantiao.mcservermanage.impl.mcservercore;

import fun.xiantiao.mcservermanage.api.mcservercore.Mojang;
import fun.xiantiao.mcservermanage.impl.MCServerCoreImpl;
import org.jetbrains.annotations.NotNull;

public class MojangImpl extends MCServerCoreImpl implements Mojang {

    public MojangImpl(@NotNull String pathname) {
        super(pathname);
    }
}
