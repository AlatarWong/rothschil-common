package io.github.rothschil.image.task.hadler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.github.rothschil.image.entity.ImageInfo;
import io.github.rothschil.image.service.ImageInfoService;
import io.github.rothschil.image.task.hadler.IntfImageInfoHandler;

import java.util.List;

/**
 * 异步处理图像信息接口
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/17 - 15:41
 * @since 1.0.0
 */
@Component
public class ImageInfoHandler implements IntfImageInfoHandler {

    private List<ImageInfo> lists;

    private ImageInfoService imageInfoService;

    @Autowired
    public void setImageInfoService(ImageInfoService imageInfoService) {
        this.imageInfoService = imageInfoService;
    }

    @Override
    public void processData() {
        imageInfoService.insert(lists);
    }

    public void setLists(List<ImageInfo> lists) {
        this.lists = lists;
    }
}
