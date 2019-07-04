package org.home.proquiz.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="answer")
@Getter
@Setter
public class Answer implements Serializable {
	private static final long serialVersionUID = -2503628094650995250L;
	
	@Id @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="correct")
	private Boolean correct = false;
	
	@NotNull(message = "Error: Answer - text is null")
	@NotEmpty(message = "Введіть текст відповіді")
	private String text;
	
//	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
//	@JoinTable(name="user_answers",
//				joinColumns = {@JoinColumn(name="answer_id", referencedColumnName="id")},
//				inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")})
//	private List<User> userAnswer;
	
	@Transient
	private long count;
	
	@Override
	public String toString() {
		return text;
	}
}
