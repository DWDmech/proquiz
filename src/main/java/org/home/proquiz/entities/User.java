package org.home.proquiz.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@JsonRootName(value = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 4633165277370389654L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Lazy
	@Column(name="active")
	private Boolean active = true;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Roles> roles = Arrays.asList(Roles.ROLE_USER);
	
	@NotEmpty(message = "Введіть прізвище та ім'я")
	@Size(max = 100, message = "Прізвище  та ім'я має бути менше 100 символів")
	@NotNull(message="Error: User - name is null")
	@Column(name="name")
	private String name;
	
	@Email(message = "Email введенно невірно")
	@NotEmpty(message = "Введіть email")
	@Size(max = 100, message = "Email має будти менше 100 символів")
	@NotNull(message="Error: User - email is null")
	@Column(name="email")
	private String email;

	@NotEmpty(message = "Введіть пароль")
	@Size(max = 255, message = "Пароль має бути менше 255 символів")
	@NotNull(message="Error: User - password is null")
	@Lazy
	@Column(name="password", length = 255)
	private String password;
	
}
