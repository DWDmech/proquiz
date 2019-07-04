package org.home.proquiz.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="interview")
@Getter
@Setter
public class Interview implements Serializable {
	private static final long serialVersionUID = 164124113280775575L;

	@Id @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="isAnonymous")
	private Boolean isAnonymous = true;
	
	@Column(name="isComment")
	private Boolean isComment = true;
	
	@Column(name="active")
	private Boolean active = true;
	
	@Column(name="date")
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private LocalDateTime date;
	
	@NotNull(message = "Error: forgot set user")
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User author;
	
	@NotNull(message = "Error: Interview - title is null")
	@NotEmpty(message = "Введіть назву опитування")
	@Column(name="title")
	private String title;
	
	@Valid
	@Size(min = 1, max = 30, message = "кількість запитань має бути від 1 до 30")
	@NotNull(message = "Error: Interview - questions is null")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="questions",
				joinColumns=
	            	@JoinColumn(name="interview_id", referencedColumnName="id"),
	            inverseJoinColumns=
	            	@JoinColumn(name="question_id", referencedColumnName="id"))
	private List<Question> questions;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="comments",
				joinColumns=
	            	@JoinColumn(name="interview_id", referencedColumnName="id"),
	            inverseJoinColumns=
	            	@JoinColumn(name="comment_id", referencedColumnName="id"))
	private List<Comment> comments;

	@Transient
	@JsonProperty("count")
	private Short count = 0;
	
	@Override
	public String toString() {
		return title;
	}
}
