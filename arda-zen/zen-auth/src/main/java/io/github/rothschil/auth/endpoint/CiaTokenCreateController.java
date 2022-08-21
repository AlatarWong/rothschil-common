package io.github.rothschil.auth.endpoint;

import cn.ffcs.cia.auth.service.CiaTokenCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cia
 * @date 2020/9/3
 * <p>
 * token 自定义认证，生成token
 */
@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class CiaTokenCreateController {

	private final CiaTokenCreateService ciaTokenCreateService;

	@PostMapping(value = "/signIn")
	public ResponseEntity<OAuth2AccessToken> tokenCreate(@RequestParam("account") String account,@RequestParam("rndCode") String rndCode,@RequestParam("passWord") String passWord) throws HttpRequestMethodNotSupportedException {
		return ciaTokenCreateService.tokenCreate(account,rndCode,passWord);
	}

}
