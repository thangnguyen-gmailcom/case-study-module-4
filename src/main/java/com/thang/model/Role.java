package com.thang.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "role",fetch = FetchType.LAZY)
	public Set<User> users;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

	public Role(int id, String name, Set<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}

	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Role() {
	}
	
	

}
