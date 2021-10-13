package xyz.wongs.drunkard.jwt.po;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/1/6 - 10:22
 * @since 1.0.0
 */
public class User implements Serializable {
    private String id;
    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
