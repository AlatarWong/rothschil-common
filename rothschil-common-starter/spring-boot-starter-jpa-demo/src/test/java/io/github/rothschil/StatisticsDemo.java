package io.github.rothschil;

import io.github.rothschil.controller.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Starter测试")
@Slf4j
public class StatisticsDemo  extends BaseTest {

    @Autowired
    private UserService userService;


    @DisplayName("查询")
    @Test
    public void settingLabel() {
        log.info(userService.getByKey(8L).toString());
    }


}
