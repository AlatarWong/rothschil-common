package xyz.wongs.drunkard.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import xyz.wongs.drunkard.alipay.model.builder.RequestBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 服务实现类抽象类，所有的服务实现类的父类
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @description //TODO
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 10:13
 * @version 1.0.0
 */
abstract class AbsAlipayService {
    protected Log log = LogFactory.getLog(getClass());

    /** 验证bizContent对象
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @description //TODO
     * @date 2021/9/22-10:51
     * @param builder
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

    /** 调用AlipayClient的execute方法，进行远程调用
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @description //TODO
     * @date 2021/9/22-10:51
     * @param client
     * @param request
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
