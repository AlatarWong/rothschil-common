package xyz.wongs.drunkard.base.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件实体信息
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 14:23
 * @since 1.0.0
 */
@AllArgsConstructor
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
     * 状态，参考 {@link xyz.wongs.drunkard.common.constant.Constants}
     */
    private String status;

    /**
     * 发送失败的异常信息
     */
    private String error;

    /**
     * 附件
     */
    private MultipartFile[] multipartFiles;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JSONField(serialize = false)
    public MultipartFile[] getMultipartFiles() {
        return multipartFiles;
    }

    public void setMultipartFiles(MultipartFile[] multipartFiles) {
        this.multipartFiles = multipartFiles;
    }
}
