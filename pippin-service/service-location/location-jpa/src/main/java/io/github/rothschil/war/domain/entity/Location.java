package io.github.rothschil.war.domain.entity;

import lombok.*;
import io.github.rothschil.base.persistence.jpa.entity.BasePo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区域实体类
 *
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 15:27
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_locations")
public class Location extends BasePo<Long> {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "flag")
    private String flag;

    @Column(name = "local_code")
    private String localCode;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "lv")
    private Integer lv;

    @Column(name = "sup_local_code")
    private String supLocalCode;

    @Column(name = "url")
    private String url;

}