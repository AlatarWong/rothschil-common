package io.github.rothschil.task;

import com.alibaba.fastjson.JSONObject;
import io.github.rothschil.base.aop.queue.AbstractQueue;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.constant.Constants;
import io.github.rothschil.domain.entity.NgxLog;
import io.github.rothschil.domain.service.NgxLogService;
import io.github.rothschil.queue.handler.NgxLogHandler;
import io.github.rothschil.util.ParsingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class FileTaskComponent {

    private final static int MAX_TIMES = 0X7D0;

    public Status executeJsonByFile(File file){
        Status status;
        if (!ParsingUtil.exists(file)) {
            status = Status.FILE_NOT_EXIST;
            return status;
        }
        int times = 0;
        List<NgxLog> logList = new ArrayList<>(MAX_TIMES);
        //可以反序列化的数量
        long accords = 0;
        //文件总行数
        long total = 0;
        try {
            LineIterator it = FileUtils.lineIterator(file, Constants.UTF8);
            boolean flag = true;
            while (it.hasNext()) {
                total = total + 1;
                String line = it.nextLine();
                NgxLog tmp;
                try {
                    tmp = JSONObject.parseObject(line, NgxLog.class);
                    SimpleDateFormat input_date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                    SimpleDateFormat output_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date parse_date = input_date.parse(tmp.getTime());
                    tmp.setTime(output_date.format(parse_date));
                } catch (Exception e) {
                    // JSON 反序列化错误，考虑可能存在更多此类，都打印，耗费IO缓冲区性能
                    // 且容易堆栈溢出，所以只打印一次，
                    if (flag) {
                        log.error("JSONObject Parse Exception Content is {}", line);
                        flag = false;
                    }
                    continue;
                }
                // 没有加同步锁的情况下，可能存在内容为空
                times++;
                logList.add(tmp);
                if (MAX_TIMES == times) {
                    accords += times;
                    insert(logList);
                    // 先插入，再将集合清空
                    logList.clear();
                    times = 0;
                }
            }
            // 完成读取，最后数据入库
            if (!logList.isEmpty()) {
                insert(logList);
                logList.clear();
            }
            it.close();
            accords += times;
            log.warn(" 可以反序列化的数量 {}", accords);
        } catch (IOException e) {
            status = Status.IO_EXCEPTION;
            log.error("IO IOException, ERR is {}", e.getMessage());
        } catch (RuntimeException e) {
            status = Status.ELASTIC_ERR;
            log.error("Insert Batch Fail , ERR is {}", file);
        }
        return null;
    }

    public void executeJsonByFile(String path){
        executeJsonByFile(new File(path));
    }
    /**
     * 写入ElasticSearch
     *
     * @param logList 日志列表
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-20:48
     **/
    protected void insert(List<NgxLog> logList) {
        try {
            ngxLogService.insertBatchByOn(logList);
            log.error("logList Size is {}",logList.size());
        } catch (Exception e) {
            log.error("Insert Batch Fail length is {}", logList.size());
            e.printStackTrace();
        }
    }

    private NgxLogService ngxLogService;

    private AbstractQueue abstractQueue;
    private NgxLogHandler ngxLogHandler;

    @Autowired
    public void setAbstractQueue(AbstractQueue abstractQueue) {
        this.abstractQueue = abstractQueue;
    }

    @Autowired
    public void setNgxLogHandler(NgxLogHandler ngxLogHandler) {
        this.ngxLogHandler = ngxLogHandler;
    }

    @Autowired
    public void setNgxLogService(NgxLogService ngxLogService) {
        this.ngxLogService = ngxLogService;
    }
}
