package xyz.wongs.drunkard.oauth2.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xyz.wongs.drunkard.common.po.BasePo;

/**
 * @ClassName OauthUser
 * @Description 
 * @author WCNGS@QQ.COM
 *
 * @date 20/12/2 16:18
 * @since 1.0.0
*/
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OauthUser extends BasePo<Long> {

    private Long id;

    private String uName;

    private String passWord;
}