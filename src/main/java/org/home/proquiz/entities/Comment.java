package org.home.proquiz.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comment")
@Getter
@Setter
public class Comment implements Serializable {
	private static final long serialVersionUID = 7371721155163193724L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="anonymous")
	private boolean anonymouse = false;
	
//	@NotNull(message = "Error: Comment - date is null")
	@Column(name="date")
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private LocalDateTime date;
	
//	@NotNull(message = "Error: Comment - author is null")
	@OneToOne
	@JoinColumn(name="user_id", nullable=true)
	private User author;
	
	@NotEmpty(message = "Введіть текст")
	@NotNull(message = "Error: Comment - text is null")
	@Column(name="text")
	private String text;
}