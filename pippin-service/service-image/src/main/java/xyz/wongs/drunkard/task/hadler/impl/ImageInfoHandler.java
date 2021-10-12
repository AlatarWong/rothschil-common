package xyz.wongs.drunkard.task.hadler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.moon.entity.ImageInfo;
import xyz.wongs.drunkard.moon.service.ImageInfoService;
import xyz.wongs.drunkard.task.hadler.IntfImageInfoHandler;

import java.util.List;

/** 异步处理图像信息接口
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 *
 * @date 2018/4/17 - 15:41
 * @since 1.0.0
 */
@Component
public class ImageInfoHandler implements IntfImageInfoHandler {

    private List<ImageInfo> lists;

    @Autowired
    private ImageInfoService imageInfoService;

    @Override
    public void processData(){
        imageInfoService.insert(lists);
    }

    public void setLists(List<ImageInfo> lists) {
        this.lists = lists;
    }
}
