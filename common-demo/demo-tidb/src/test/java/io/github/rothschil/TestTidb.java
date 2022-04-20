package io.github.rothschil;

import io.github.rothschil.task.FileTaskComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("静态统计")
@Slf4j
public class TestTidb extends BaseTest{

    @DisplayName("统计")
    @Test
    public void bunkOn() {
       String path = "H:\\Download\\temp\\ct_portal\\ct_portal.2022-01-07.log";
        fileTaskComponent.executeJsonByFile(path);
    }


    @Autowired
    private FileTaskComponent fileTaskComponent;
}
