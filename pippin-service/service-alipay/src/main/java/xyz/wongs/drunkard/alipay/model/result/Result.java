package xyz.wongs.drunkard.alipay.model.result;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2021/9/23 - 10:09
 * @since 1.0.0
 */
public interface Result {

    /** 判断交易是否在业务上成功, 返回true说明一定成功，但是返回false并不代表业务不成功！
     * 因为还有unknown的状态可能业务已经成功了
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-10:09
     * @param
     * @return boolean
     **/
    boolean isTradeSuccess();
}
