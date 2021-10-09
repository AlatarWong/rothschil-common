package xyz.wongs.drunkard.common.exception.user;

/** 用户账号已被删除
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:29
 * @since 1.0.0
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("user.password.delete", null);
    }
}
