package xyz.wongs.drunkard.base.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/** 邮件内容信息
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 14:23
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class MailVo implements Serializable {

    private String id;
    private String from;

    private String to;
    private String subject;
    private String text;
    private Date sentDate;
    private String cc;
    private String bcc;
    private String status;
    private String error;
    @JsonIgnore
    private MultipartFile[] multipartFiles;

}
