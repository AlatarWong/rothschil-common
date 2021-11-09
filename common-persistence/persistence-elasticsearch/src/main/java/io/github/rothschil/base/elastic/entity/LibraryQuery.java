package io.github.rothschil.base.elastic.entity;

import lombok.Data;

/**
 * 字典类查询封装
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/07/29 - 20:41
 * @since 1.0.0
 */
@Data
public class LibraryQuery {

    private int currentPage;

    private int libraryId;

    private String queryText;

    /**
     * 页面显示数据条数,在系统参数中配置
     */
    private int pageSize;
}
