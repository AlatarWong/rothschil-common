package xyz.wongs.drunkard.common.exception.file;

/** 文件名称超长限制异常类
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:28
 * @since 1.0.0
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
