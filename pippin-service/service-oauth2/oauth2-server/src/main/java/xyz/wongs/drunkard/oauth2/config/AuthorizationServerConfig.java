package xyz.wongs.drunkard.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import xyz.wongs.drunkard.oauth2.filter.OAuthTokenAuthenticationFilter;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * 配置认证服务中心
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/27 11:03
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * webSecurityConfig 中配置的AuthenticationManager
     */

    private AuthenticationManager authenticationManager;

    /**
     * 此项目使用数据库保存 token 等信息所以要配置数据源
     */
    private DataSource dataSource;

    /**
     * webSecurityConfig 中配置的 userDetailsService
     */
    private UserDetailsService userDetailsService;

    /**
     * webSecurityConfig 中配置的 passwordEncoder(使用MD5加密)
     */
    private PasswordEncoder passwordEncoder;

    private TokenStore tokenStore;

    private ClientDetailsService clientDetailsService;

    private OAuthTokenAuthenticationFilter oAuthTokenAuthenticationFilter;

    @Autowired
    @Qualifier("authenticationManagerBean")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Autowired
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Autowired
    public void setoAuthTokenAuthenticationFilter(OAuthTokenAuthenticationFilter oAuthTokenAuthenticationFilter) {
        this.oAuthTokenAuthenticationFilter = oAuthTokenAuthenticationFilter;
    }

    /**
     * 使用Jdbctoken store，若需要使用内存 token Store，则用 return new InMemoryTokenStore();
     *
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @date 20/11/27 11:05
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 对 oauth_client_details 表的一些操作
     *
     * @return ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }


    /**
     * 直接使用 clients.jdbc(dataSource);
     *
     * @param clients ClientDetailsServiceConfigurer
     * @date 20/11/27 11:07
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 请求token的时候会将client_id,client_secret等信息保存到 oauth_client_details 表中，所以需要手动创建该表
        // 注意：以下注释的代码在请求了一次 token 之后则可以注释掉，否则如果不换 client 名字的话会因为主键冲突无法插入 client 信息。也可以一开始就注释，手动添加记录到数据库
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                //允许check_token访问
                .checkTokenAccess("permitAll()")
                //允许表单登录
                .allowFormAuthenticationForClients()
        //oauth/token端点过滤器
//                .addTokenEndpointAuthenticationFilter(oAuthTokenAuthenticationFilter)
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore());
        endpoints.userDetailsService(userDetailsService);
        endpoints.setClientDetailsService(clientDetailsService);
        //配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        //是否支持refresh_token，默认false
        tokenServices.setSupportRefreshToken(true);
        //客户端信息
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        //自定义token生成
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        //access_token 的有效时长 (秒), 默认 12 小时
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
        //是否复用refresh_token,默认为true(如果为false,则每次请求刷新都会删除旧的refresh_token,创建新的refresh_token)
//      tokenServices.setReuseRefreshToken(true);
        //refresh_token 的有效时长 (秒), 默认 30 天
//      tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(20));

        endpoints.tokenServices(tokenServices);
//        endpoints.tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
//                //必须设置 UserDetailsService 否则刷新token 时会报错
//                .userDetailsService(userDetailsService);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }
}
