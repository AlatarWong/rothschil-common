package io.github.rothschil.unit;

import io.github.rothschil.BaseTest;
import io.github.rothschil.demo.entity.AtConfSource;
import io.github.rothschil.demo.service.AtConfSourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DisplayName("Plus处理")
@Slf4j
public class PlusDemoCase extends BaseTest {

    @Autowired
    private AtConfSourceService atConfSourceService;



    @DisplayName("测试")
    @Test
    public void getAll() {
        List<AtConfSource> lists = atConfSourceService.getAll();
        for (AtConfSource list : lists) {
            System.out.println(list.toString());
        }
    }

    public void pl(){

    }

}
