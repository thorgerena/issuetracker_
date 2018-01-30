package com.fdmgroup.issuetracker.model.impl;


import javax.persistence.*;

import com.fdmgroup.issuetracker.model.IUser;

@Entity
@Table(name = "Users")
@NamedQueries({ 
@NamedQuery(name = "User.findAll", query = "SELECT u from User u"),
@NamedQuery(name = "User.findByName", 
query = "SELECT u FROM User u WHERE u.username = :username"),
@NamedQuery(name = "User.findByRole", 
query = "SELECT u FROM User u WHERE u.role = :role")})

public class User implements IUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "User_id")
	private int userId;
	@ManyToOne (cascade=CascadeType.ALL)
	@JoinColumn(name="Dept_id")
	private Department department;
	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@JoinTable(name="USER_ROLE",
				joinColumns = {@JoinColumn(name="User_id", referencedColumnName="User_id")},
				inverseJoinColumns = {@JoinColumn(name="Role_id", referencedColumnName="Role_id")}
	)
	private Role role;
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User(){}
	public User(Department department, String username, String password, String email, Role role) {
		this.setDepartment(department);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setRole(role);
	}
	
	public int getUserId() {
		return userId;
	}

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
