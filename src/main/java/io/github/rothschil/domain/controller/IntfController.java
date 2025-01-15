package io.github.rothschil.domain.controller;

import io.github.rothschil.common.annotation.SelectorDataSource;
import io.github.rothschil.common.constant.DataSourceNamesConstant;
import io.github.rothschil.domain.entity.Intf;
import io.github.rothschil.domain.service.IntfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "测试接口", tags = "XX", description = "XXX")
@Slf4j
@RestController
@RequestMapping("/intf")
public class IntfController {


    @Autowired
    private IntfService intfService;

    @RequestMapping(value = "/findOne/{id}",method = RequestMethod.GET)
    public Intf findOne(@PathVariable(value = "id") Long id){
        return intfService.findById(id);
    }


    /**
     * @description: //TODO
     * @param id
     * @return Boolean
     * @date: 2024/12/20 14:35
     **/
    @ApiOperation(value = "是否存在判断", notes = "对象的是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "对象属性例如：id", required = true)
    })
    @RequestMapping(value = "/exists/{id}",method = RequestMethod.GET)
    public Boolean exists(@PathVariable(value = "id") Long id){
        return intfService.exists(id);
    }
}
