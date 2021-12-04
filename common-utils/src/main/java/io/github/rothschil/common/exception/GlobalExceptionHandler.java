package io.github.rothschil.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.base.response.po.ErR;


/**
 * 全局异常处理 对统一返回实体 进行封装 Handler
 *
 * @author WCNGS@QQ.COM
 * @date 2019/9/23 15:03
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 拦截抛出的异常
     *
     * @param ex 异常
     * @return ErR 异常响应
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-16:54
     **/
    @ExceptionHandler(DrunkardException.class)
    @ResponseBody
    public ErR handleWeathertopException(DrunkardException ex) {
        LOG.error("WeathertopRuntimeException code:{},msg:{}", ex.getStatus(), ex.getMessage());
        return ErR.fail(ex.getStatus(), ex.getMessage());
    }


//    /**
//     * 拦截抛出的异常
//     *
//     * @param e 异常
//     * @return ErR 异常响应
//     * @author <a href="https://github.com/rothschil">Sam</a>
//     * @date 2018/4/26-16:54
//     **/
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Throwable.class)
//    public ErR handlerThrowable(Throwable e) {
//        LOG.error("发生未知异常！原因是: ", e);
//        return ErR.fail(Status.RUNTIME_EXCEPTION, e);
//    }

    /**
     * 参数校验异常
     *
     * @param e 异常
     * @return ErR 异常响应
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-16:54
     **/
    @ExceptionHandler(BindException.class)
    public ErR handleBindException(BindException e) {
        LOG.error("发生参数校验异常！原因是：", e);
        return ErR.fail(Status.API_PARAM_EXCEPTION, e, e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErR handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOG.error("发生参数校验异常！原因是：{}", e);
        return ErR.fail(Status.API_PARAM_EXCEPTION, e, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
