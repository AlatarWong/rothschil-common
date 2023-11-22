package io.github.rothschil.common.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Version {
    public static final String module  = "MMCC_MAINTAIN";
    public static final String version = "v1.07.000";

    public static void printlnVersionInfo() {
        log.info(" {} ",version);
    }

    public static String getVersion() {
        return module + "  " + version;
    }
}
