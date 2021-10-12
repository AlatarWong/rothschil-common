package xyz.wongs.drunkard.common.utils.sql;


import xyz.wongs.drunkard.base.response.enums.Status;
import xyz.wongs.drunkard.common.exception.DrunkardException;
import xyz.wongs.drunkard.common.utils.StringUtils;

/** sql操作工具类
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 21:38
 * @since 1.0.0
 */
public class SqlUtil {
    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new DrunkardException(Status.PARAMS_IS_INVALID);
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
}
