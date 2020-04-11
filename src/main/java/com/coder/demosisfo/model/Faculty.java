package com.coder.demosisfo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "fakultas")
public class Faculty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer fakultasId;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "nama_fakultas")
	private String namaFakultas;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_fakultas", joinColumns = {
			@JoinColumn(name = "fakultas_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "data_id", referencedColumnName = "id")})
	private Set<DataRole> data = new HashSet<>();
	
	public Faculty() {
		
	}

	public Faculty(@NotBlank @Size(max = 50) String namaFakultas) {
		super();
		this.namaFakultas = namaFakultas;
	}
	
	/*
	 * @OneToMany(cascade=CascadeType.ALL)
	 * 
	 * @JoinTable(name = "role_jurusan", joinColumns = {
	 * 
	 * @JoinColumn(name = "fakultas_id", referencedColumnName = "id")},
	 * inverseJoinColumns = {@JoinColumn(name = "jurusan_id", referencedColumnName =
	 * "id")}) private List<Major> major = new ArrayList<>();
	 */

}
