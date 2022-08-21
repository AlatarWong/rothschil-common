/*
 *
 *      Copyright (c) 2018-2025, cia All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: cia
 *
 */

package io.github.rothschil.auth;

import cn.ffcs.cia.common.feign.annotation.EnableCiaFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cia
 * @date 2020-02-08 认证授权中心
 */
@EnableCiaFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CiaAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiaAuthApplication.class, args);
	}

}
