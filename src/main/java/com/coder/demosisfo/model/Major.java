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
@Table(name = "jurusan")
public class Major {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer jurusanId;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "nama_jurusan")
	private String namaJurusan;
	
	@Column(name = "nama_fakultas")
	private String namaFakultas;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_jurusan", joinColumns = {
			@JoinColumn(name = "jurusan_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "data_id", referencedColumnName = "id")})
	private Set<DataRole> data = new HashSet<>();
	
	public Major() {
		
	}

	public Major(@NotBlank @Size(max = 50) String namaJurusan, String namaFakultas, Set<DataRole> data) {
		super();
		this.namaJurusan = namaJurusan;
		this.namaFakultas = namaFakultas;
		this.data = data;
	}

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinTable(name = "role_jurusan", joinColumns = {
	 * 
	 * @JoinColumn(name = "jurusan_id", referencedColumnName = "id")},
	 * inverseJoinColumns = {@JoinColumn(name = "fakultas_id", referencedColumnName
	 * = "id")}) private Faculty faculty;
	 */
	
	/*
	 * @OneToMany
	 * 
	 * @JoinTable(name = "role_mk", joinColumns = {
	 * 
	 * @JoinColumn(name = "jurusan_id", referencedColumnName = "id")},
	 * inverseJoinColumns = {@JoinColumn(name = "matakuliah_id",
	 * referencedColumnName = "id")}) private List<Courses> courses = new
	 * ArrayList<>();
	 */
	
}
