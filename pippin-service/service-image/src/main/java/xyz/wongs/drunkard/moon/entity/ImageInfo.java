package xyz.wongs.drunkard.moon.entity;

import lombok.*;
import xyz.wongs.drunkard.common.po.BasePo;

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