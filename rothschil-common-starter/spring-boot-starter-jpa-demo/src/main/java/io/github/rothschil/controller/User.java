package io.github.rothschil.controller;

import lombok.*;
import javax.persistence.*;
import io.github.rothschil.base.persistence.jpa.entity.BaseJpaPo;
import java.util.Objects;
import org.hibernate.Hibernate;

/**
 * 
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021-12-04 21:03:18
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table ( name ="t_user")
public class User  extends BaseJpaPo<Long> {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name ="u_id")
	private Long id;

   	@Column(name ="u_name")
	private String uName;

   	@Column(name ="pass_word")
	private String passWord;

   	@Column(name ="c_id")
	private Integer cId;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", uName='" + uName + '\'' +
				", passWord='" + passWord + '\'' +
				", cId=" + cId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		User target = (User)o;
		return id != null && Objects.equals(id, target.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, uName, passWord, cId);
	}

}
