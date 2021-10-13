package xyz.wongs.drunkard.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.common.utils.file.FileUtil;
import xyz.wongs.drunkard.common.utils.thread.ThreadPoolsUtil;
import xyz.wongs.drunkard.moon.entity.ImageInfo;
import xyz.wongs.drunkard.task.hadler.impl.ImageInfoHandler;
import xyz.wongs.drunkard.task.queue.ImageInfoQueue;
import xyz.wongs.drunkard.task.thread.FileSizeThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author WCNGS@QQ.COM
 * @date 20/12/30 12:58
 * @since 1.0.0
 */
@Component
public class RunFileTask {

    public static final String THREAD_NAME = "RUN_FILE_NAME";


    private ImageInfoQueue imageInfoQueue;

    private ImageInfoHandler imageInfoHandler;

    @Autowired
    public void setImageInfoQueue(ImageInfoQueue imageInfoQueue) {
        this.imageInfoQueue = imageInfoQueue;
    }

    @Autowired
    public void setImageInfoHandler(ImageInfoHandler imageInfoHandler) {
        this.imageInfoHandler = imageInfoHandler;
    }

    private final ThreadPoolExecutor executor = ThreadPoolsUtil.doCreate(3, 5, THREAD_NAME);

    public void run(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            return;
        }
        listFiles(file);
        //关闭线程池
        executor.shutdown();
    }

    public void listFiles(File file) {

        File[] files = file.listFiles();
        List<ImageInfo> lists = new ArrayList<>();
        assert files != null;
        for (File fl : files) {
            if (fl.isDirectory()) {
                listFiles(fl);
                continue;
            }
            String suffixName = FileUtil.getSuffix(fl);
            if (!ImageConst.LIST_SUFFIX.contains(suffixName.toUpperCase())) {
                continue;
            }
            Future<String> result = executor.submit(new FileSizeThread(fl));
            String fileName = FileUtil.getName(fl);
            long size = fl.length();
            String filePath = FileUtil.getAbsolutePath(fl);
            try {
                ImageInfo imageInfo = ImageInfo.builder().fileName(fileName).filePath(filePath).fileSize(size).suffixName(suffixName)
                        .md5(result.get()).build();
                lists.add(imageInfo);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (!lists.isEmpty()) {
            imageInfoHandler.setLists(lists);
            imageInfoQueue.addQueue(imageInfoHandler);
        }
    }
}
