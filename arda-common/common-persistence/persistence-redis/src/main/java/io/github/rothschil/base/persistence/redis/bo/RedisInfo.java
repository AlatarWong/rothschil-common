package io.github.rothschil.base.persistence.redis.bo;

import java.util.Map;

public class RedisInfo {


    public RedisInfo() {
    }

    public RedisInfo(String key, String value, String vvl) {
        this.key = key;
        this.value = value;
        this.vvl = vvl;
    }

    private String key;

    private String value;

    public static Map<String,String> map;

    private String vvl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVvl() {
        return vvl;
    }

    public void setVvl(String vvl) {
        this.vvl = vvl;
    }

}
