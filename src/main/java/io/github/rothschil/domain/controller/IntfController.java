package io.github.rothschil.domain.controller;

import io.github.rothschil.domain.entity.Intf;
import io.github.rothschil.domain.service.IntfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/intf")
public class IntfController {


    @Autowired
    private IntfService intfService;

    @RequestMapping(value = "/findOne/{id}",method = RequestMethod.GET)
    public Intf findOne(@PathVariable(value = "id") Long id){
        return intfService.findOne(id);
    }
}
