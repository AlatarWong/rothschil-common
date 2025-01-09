package io.github.rothschil.web.controller;

import io.github.rothschil.common.constant.Constant;
import io.github.rothschil.domain.vo.UserVo;
import io.github.rothschil.web.compoent.TestCompoent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * //todo 添加类描述
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 */
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
        return testCompoent.get(UserVo.builder().account("9999").email("wongs@qq.com").id(2L).build());
    }

}
