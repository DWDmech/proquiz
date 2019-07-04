package org.home.proquiz.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="question")
@Getter
@Setter
public class Question implements Serializable {
	private static final long serialVersionUID = -3957283518127543998L;

	@Id @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Error: Question - text is null")
	@NotEmpty(message = "Введіть текст запитання")
	@Column(name="text")
	private String text;
	
	@Valid
	@Size(min = 1, max = 5, message = "кількість віповідей має бути від 1 до 5")
	@NotNull(message = "Error: Question - answers is null")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="answers",
				joinColumns=
	            	@JoinColumn(name="question_id", referencedColumnName="id"),
	            inverseJoinColumns=
	            	@JoinColumn(name="answer_id", referencedColumnName="id"))
	private List<Answer> answers;

	@Override
	public String toString() {
		return text;
	}
}
