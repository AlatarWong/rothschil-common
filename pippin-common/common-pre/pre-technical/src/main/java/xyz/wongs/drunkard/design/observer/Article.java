package xyz.wongs.drunkard.design.observer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 文章
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 14:00
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {

    private String titile;

    private String context;

    private String createTime;

}
