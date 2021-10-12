package xyz.wongs.drunkard.common.utils;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wongs.drunkard.common.constant.Constants;
import xyz.wongs.drunkard.common.json.JSON;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * 处理并记录日志文件
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/10 - 0:06
 * @since 1.0.0
 */
public class LogUtils {
    public static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");
    public static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");


    /** 记录访问日志 [username][jsessionid][ip][accept][UserAgent][url][params][Referer]
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:42
     * @param request 请求
     * @return
     **/
    public static void logAccess(HttpServletRequest request) throws Exception {

        String username = getUsername();
        String jsessionId = request.getRequestedSessionId();
        String ip = IpUtils.getIpAddr(request);
        String accept = request.getHeader("accept");
        String userAgent = request.getHeader(Constants.USER_TYPE);
        String url = request.getRequestURI();
        String params = getParams(request);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(username));
        s.append(getBlock(jsessionId));
        s.append(getBlock(ip));
        s.append(getBlock(accept));
        s.append(getBlock(userAgent));
        s.append(getBlock(url));
        s.append(getBlock(params));
        s.append(getBlock(request.getHeader("Referer")));
        getAccessLog().info(s.toString());
    }

    /**
     * 记录异常错误 格式 [exception]
     *
     * @param message 消息内容
     * @param e 异常Bean
     */
    public static void logError(String message, Throwable e) {
        String username = getUsername();
        StringBuilder s = new StringBuilder();
        s.append(getBlock("exception"));
        s.append(getBlock(username));
        s.append(getBlock(message));
        ERROR_LOG.error(s.toString(), e);
    }

    /**
     * 记录页面错误 错误日志记录 [page/eception][username][statusCode][errorMessage][servletName][uri][exceptionName][ip][exception]
     *
     * @param request Request对象
     */
    public static void logPageError(HttpServletRequest request) {
        String username = getUsername();

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (statusCode == null) {
            statusCode = 0;
        }

        StringBuilder s = new StringBuilder();
        s.append(getBlock(t == null ? "page" : "exception"));
        s.append(getBlock(username));
        s.append(getBlock(statusCode));
        s.append(getBlock(message));
        s.append(getBlock(IpUtils.getIpAddr(request)));

        s.append(getBlock(uri));
        s.append(getBlock(request.getHeader("Referer")));
        StringWriter sw = new StringWriter();

        while (t != null) {
            t.printStackTrace(new PrintWriter(sw));
            t = t.getCause();
        }
        s.append(getBlock(sw.toString()));
        getErrorLog().error(s.toString());

    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

    protected static String getParams(HttpServletRequest request) throws Exception {
        Map<String, String[]> params = request.getParameterMap();
        return JSON.marshal(params);
    }

    protected static String getUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    public static Logger getAccessLog() {
        return ACCESS_LOG;
    }

    public static Logger getErrorLog() {
        return ERROR_LOG;
    }
}
