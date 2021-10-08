package xyz.wongs.drunkard.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import xyz.wongs.drunkard.alipay.model.builder.RequestBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**服务实现类抽象类，所有的服务实现类的父类
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/26 - 13:56
 * @since 1.0.0
 */
abstract class AbsAlipayService {
    Log log = LogFactory.getLog(getClass());

    /** 验证bizContent对象
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/22-10:51
     * @param builder 请求抽象类
     * @return
     **/
    protected void validateBuilder(RequestBuilder builder) {

        if (builder == null) {
            throw new NullPointerException("builder should not be NULL!");
        }

        if (!builder.validate()) {
            throw new IllegalStateException("builder validate failed! " + builder.toString());
        }
    }

    /** 调用 {@link com.alipay.api.AlipayClient } 的 execute 方法，进行远程调用
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/22-10:51
     * @param client 请求客户端
     * @param request 请求接口
     * @return AlipayResponse
     **/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected AlipayResponse getResponse(AlipayClient client, AlipayRequest request) {

        try {
            AlipayResponse response = client.execute(request);
            if (response != null) {
                log.info(response.getBody());
            }
            return response;

        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
