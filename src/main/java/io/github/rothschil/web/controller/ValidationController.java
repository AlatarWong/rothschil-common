package io.github.rothschil.web.controller;

import io.github.rothschil.common.constant.Constant;
import io.github.rothschil.common.utils.DateUtils;
import io.github.rothschil.domain.vo.UserVo;
import io.github.rothschil.web.compoent.TestCompoent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * //todo 添加类描述
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 */
@Slf4j
@RestController
public class ValidationController {


    TestCompoent testCompoent;

    protected ValidationController(TestCompoent testCompoent){
        this.testCompoent=testCompoent;
    }


    @PostMapping("/addUser")
    public String addUser(@RequestBody @Validated UserVo user) {
        return Constant.NUM_1;
    }

    @GetMapping(value = "one")
    public UserVo query(){
        int id = new Random().nextInt(90) + 10;
        String acct = DateUtils.getDate(DateUtils.TRANS_PATTERN_MM);
        log.info("[User account ] {}",acct);
        UserVo userVo = UserVo.builder().email("wongs@qq.com").account(acct).password("取你狗命").id(id).build();
        return testCompoent.get(userVo);
    }

}
