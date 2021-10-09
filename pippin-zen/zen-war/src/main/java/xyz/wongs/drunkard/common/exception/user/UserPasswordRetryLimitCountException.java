package xyz.wongs.drunkard.common.exception.user;

/** 用户错误记数异常类
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:29
 * @since 1.0.0
 */
public class UserPasswordRetryLimitCountException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super("user.password.retry.limit.count", new Object[] { retryLimitCount });
    }
}
