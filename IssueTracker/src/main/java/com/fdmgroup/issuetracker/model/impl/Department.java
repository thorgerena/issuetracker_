package com.fdmgroup.issuetracker.model.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Department")
@NamedQueries({
@NamedQuery(name = "Department.findByName", 
query = "SELECT u FROM Department u WHERE u.departmentName = :dept_name"),
@NamedQuery(name = "Department.listAll", query = "SELECT u from Department u")})
public class Department {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Dept_id")
	private int departmentId;
	@Column(name="Dept_name", unique = true)
	private String departmentName;
	
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL)
	private Set<User> users = new HashSet<User>();
	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String department_name) {
		this.departmentName = department_name;
	}
	
}
