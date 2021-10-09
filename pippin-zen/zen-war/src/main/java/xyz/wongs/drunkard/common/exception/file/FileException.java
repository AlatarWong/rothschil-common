package xyz.wongs.drunkard.common.exception.file;


import xyz.wongs.drunkard.common.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:28
 * @since 1.0.0
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
