package io.github.rothschil.oauth2.filter;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token端点过滤器
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/27 14:59
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Component
public class OAuthTokenAuthenticationFilter extends GenericFilterBean {
    private static final String OAUTH_TOKEN_URL = "/oauth2/token";

    private RequestMatcher requestMatcher;

    public OAuthTokenAuthenticationFilter() {
        //OrRequestMatcher or组合多个RequestMatcher
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.POST.name())
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (requestMatcher.matches(request)) {

        }
        filterChain.doFilter(request, response);
    }

}
