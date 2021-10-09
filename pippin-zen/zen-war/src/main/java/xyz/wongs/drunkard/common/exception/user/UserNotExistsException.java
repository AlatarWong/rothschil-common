package xyz.wongs.drunkard.common.exception.user;

/** 用户不存在异常类
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:29
 * @since 1.0.0
 */
public class UserNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}
