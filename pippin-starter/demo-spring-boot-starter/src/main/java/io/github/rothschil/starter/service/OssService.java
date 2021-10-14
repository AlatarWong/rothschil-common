package io.github.rothschil.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import io.github.rothschil.starter.prop.OssProperties;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 10:07
 * @since 1.0.0
 */
public class OssService {

    private OssProperties ossProperties;

    @Autowired
    public void setOssProperties(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    public String getOss(String who) {
        return who + " Get The " + ossProperties.toString();
    }
}
