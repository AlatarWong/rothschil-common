package io.github.rothschil.common.remote.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Http 头信息表
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @since 1.0.0
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteIntfHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 头信息表标识
     */
    private Integer id;

    /**
     * Header Key 信息
     */
    private String hdKey;

    /**
     * Header Value 信息
     */
    private String hdValue;



}
