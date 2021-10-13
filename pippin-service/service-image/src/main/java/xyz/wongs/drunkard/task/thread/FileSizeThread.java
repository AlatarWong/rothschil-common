package xyz.wongs.drunkard.task.thread;

import org.apache.commons.codec.digest.DigestUtils;
import xyz.wongs.drunkard.common.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 异步获取文件名
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/30 13:16
 * @since 1.0.0
 */
public class FileSizeThread implements Callable<String> {

    private File file;

    public FileSizeThread() {

    }

    public FileSizeThread(File file) {
        this.file = file;
    }

    @Override
    public String call() throws Exception {
        try {
            return DigestUtils.md5Hex(new FileInputStream(file));
        } catch (IOException e) {
            return StringUtils.EMPTY;
        }
    }
}
