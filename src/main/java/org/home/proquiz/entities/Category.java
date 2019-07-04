package org.home.proquiz.entities;

import java.io.Serializable;
import java.util.List;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="category")
@Getter
@Setter
public class Category implements Serializable {
	private static final long serialVersionUID = 3857623553831380395L;

	@Id @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="interviews",
				joinColumns=
	            	@JoinColumn(name="category_id", referencedColumnName="id"),
	            inverseJoinColumns=
	            	@JoinColumn(name="interview_id", referencedColumnName="id"))
	private List<Interview> interviews;

	@Override
	public String toString() {
		return title;
	}
}
