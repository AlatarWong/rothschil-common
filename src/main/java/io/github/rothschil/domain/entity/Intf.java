package io.github.rothschil.domain.entity;


import io.github.rothschil.common.base.persistence.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "INTF")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Intf extends AbstractEntity<Long> {


    @Id
    // 主键生成策略（主键自增）
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    String name;
    String password;

    public Intf(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
