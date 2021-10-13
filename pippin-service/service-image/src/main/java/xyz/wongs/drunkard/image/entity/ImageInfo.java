package xyz.wongs.drunkard.image.entity;

import lombok.*;
import xyz.wongs.drunkard.common.po.BasePo;

/**
 * 图像基本信息
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 11:53
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper=false)
@Builder(toBuilder=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo extends BasePo<Long> {

    private Long id;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private String suffixName;

    private String md5;
}