package io.github.rothschil.mail.web;

import io.github.rothschil.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import io.github.rothschil.base.vo.MailVo;

/** 发送邮件
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 15:07
 * @since 1.0.0
 */
@RequestMapping("mail")
@Controller
public class MailController {


    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("mail");
        mv.addObject("from", mailService.getMailSendFrom());
        return mv;
    }

    /** 多文件上传
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/11-14:43
     * @param mailVo    邮件实体
     * @param files 文件列表
     * @return ModelAndView
     **/
    @PostMapping(value ="/send")
    public ModelAndView send(MailVo mailVo, @RequestParam("files") MultipartFile[] files) {
        mailVo.setMultipartFiles(files);
        mailService.send(mailVo);
        ModelAndView mv = new ModelAndView("mail");
        mv.addObject("from", mailService.getMailSendFrom());
        return mv;
    }

}
