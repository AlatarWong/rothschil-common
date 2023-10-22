package io.github.rothschil.base.persistence.mybatis.safe;

public class Encrypt {

    private String value;

    public Encrypt() {
    }

    public Encrypt(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
