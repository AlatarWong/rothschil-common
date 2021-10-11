package xyz.wongs.drunkard.base.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/** 邮件实体信息
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 14:23
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class MailVo implements Serializable {

    /**
     * UUID
     */
    private String id;
    /**
     * 发件人
     */
    private String from;

    /**
     * 收件人
     */
    private String to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 发件日期
     */
    private Date sentDate;

    /**
     * 抄送
     */
    private String cc;

    /**
     * 密送或者私下抄送
     */
    private String bcc;

    /**
     * 状态，参考 {@link xyz.wongs.drunkard.base.constant.Constants}
     */
    private String status;

    /**
     * 发送失败的异常信息
     */
    private String error;

    /**
     * 附件
     */
    @JsonIgnore
    private MultipartFile[] multipartFiles;

}
