package io.github.rothschil.domain.entity;


import io.github.rothschil.common.base.persistence.entity.AbsEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "INTF")
@Data
@NoArgsConstructor
public class Intf extends AbsEntity {


    @Id
    // 主键生成策略（主键自增）
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String password;

    public Intf(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void setId(Serializable serializable) {

    }
}
