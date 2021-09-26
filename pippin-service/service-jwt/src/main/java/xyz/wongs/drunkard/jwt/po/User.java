package xyz.wongs.drunkard.jwt.po;

import java.io.Serializable;

/**
 * @Author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 *
 * @date `2021/7/6` - 10:22
 * @Version 1.0.0
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
