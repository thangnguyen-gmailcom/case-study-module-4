package com.thang.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	@NotEmpty(message = "* Full Name not empty")
	@Size(min = 8, max = 100, message = "* Name lengths from 8 to 100")
	private String fullName;

	@Column(name = "date_of_birth")
	private Date dayOfBirth;

	@Column(name = "gender")
	private String gender;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "is_delete")
	private boolean isDelete = false;

	@OneToMany(mappedBy = "user")
	private List<Bill> bills;

	@ManyToOne
	@JoinColumn(name = "id_role")
	private Role role;

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", fullName=" + fullName + ", dayOfBirth=" + dayOfBirth
				+ ", gender=" + gender + ", phoneNumber=" + phoneNumber + ", email=" + email + ", isDelete=" + isDelete
				+ ", role=" + role + "]";
	}

}
