package xyz.wongs.drunkard.alipay.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xyz.wongs.drunkard.alipay.service.MailService;
import xyz.wongs.drunkard.base.vo.MailVo;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 15:07
 * @since 1.0.0
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;


    @GetMapping("/mail")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("mail");
        mv.addObject("from", mailService.getMailSendFrom());
        return mv;
    }

    @PostMapping("/mail/send")
    public MailVo send(MailVo mailVo, @RequestParam("files") MultipartFile[] files) {
        mailVo.setMultipartFiles(files);
        return mailService.send(mailVo);
    }

    @GetMapping("/idx")
    public ModelAndView listUploadedFiles(){
        ModelAndView mv = new ModelAndView("uploadForm");
        return mv;
    }

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        ModelAndView mv = new ModelAndView("redirect:/mail");
        mv.addObject("message","You successfully uploaded " + file.getOriginalFilename() + "!");
        return mv;
    }
}
