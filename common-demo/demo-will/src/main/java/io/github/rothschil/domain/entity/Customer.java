package io.github.rothschil.domain.entity;

import io.github.rothschil.common.po.BasePo;
import lombok.*;

/**
 * 客户表
 * 
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2022-01-19 15:08:39
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BasePo<Long> {

	private Long id;

	private String phone;

	private String address;


	@Override
	public void setId(Long integer) {
		this.id=id;
	}
}
