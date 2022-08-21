package io.github.rothschil.auth.endpoint;

import cn.ffcs.cia.auth.service.CiaLoginSmsCodeService;
import cn.ffcs.cia.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class CiaLoginSmsCodeController {
	private final CiaLoginSmsCodeService ciaLoginSmsCodeService;
	@GetMapping("/smsCode")
	public R ciaLoginSmsCode(String account){

		return ciaLoginSmsCodeService.loginSmsCode(account);
	}

	@GetMapping("/mobile/{phone}")
	public R mobileCheck(@PathVariable("phone") String account){
		return ciaLoginSmsCodeService.mobileCheck(account);
	}
}
