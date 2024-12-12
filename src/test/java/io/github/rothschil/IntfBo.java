package io.github.rothschil;

import lombok.Data;

import java.io.Serializable;

@Data
public class IntfBo implements Serializable {


    private String interfaceName;

    private String address;

    private String remark;



}
