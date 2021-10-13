package xyz.wongs.drunkard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.wongs.drunkard.starter.service.OssService;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/24 - 10:16
 * @since 1.0.0
 */
@SpringBootApplication
public class VerificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerificationApplication.class);
        System.out.println(ossService.getOss("wongs"));
    }


    private static OssService ossService;

    @Autowired
    public void setOssService(OssService ossService) {
        VerificationApplication.ossService = ossService;
    }
}
