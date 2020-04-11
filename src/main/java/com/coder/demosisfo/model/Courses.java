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
@Table(name = "matakuliah")
public class Courses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer matakuliahId;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "nama_matakuliah", unique = true)
	private String namaMatakuliah;
	
	@Column(name = "semester")
	private Integer semester;
	
	@Column(name = "sks")
	private Integer sks;
	
	@Column(name = "kuota")
	private Integer kuota;
	
	@Column(name = "nama_fakultas")
	private String namaFakultas;
	
	@Column(name = "nama_jurusan")
	private String namaJurusan;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_matkul", joinColumns = {
			@JoinColumn(name = "matakuliah_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "data_id", referencedColumnName = "id")})
	private Set<DataRole> data = new HashSet<>();
	
	public Courses() {
		
	}

	public Courses(@NotBlank @Size(max = 50) String namaMatakuliah, Integer semester, Integer sks, Integer kuota, String namaFakultas,
			String namaJurusan, Set<DataRole> data) {
		super();
		this.namaMatakuliah = namaMatakuliah;
		this.semester = semester;
		this.sks = sks;
		this.kuota = kuota;
		this.namaFakultas = namaFakultas;
		this.namaJurusan = namaJurusan;
		this.data = data;
	}
	
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinTable(name = "role_mk", joinColumns = {
	 * 
	 * @JoinColumn(name = "matakuliah_id", referencedColumnName = "id")},
	 * inverseJoinColumns = {@JoinColumn(name = "jurusan_id", referencedColumnName =
	 * "id")}) private Major major;
	 */

}
