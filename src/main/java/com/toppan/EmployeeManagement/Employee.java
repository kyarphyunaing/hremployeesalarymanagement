package com.toppan.EmployeeManagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee{
	
	@Id
	@Column(name = "id",unique=true,nullable = false)
	private String id;

	@Column(name = "login",unique=true,nullable = false)
	private String login;

	@Column(name = "name",nullable = false)
	private String name;

	@Column(name = "salary",nullable = false)
	private double salary;

	public Employee() {

	}

	public Employee(String id,String login, String name, double salary) {
		super();
		this.id=id;
		this.login = login;
		this.name = name;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [ id=" + id + ", login=" + login + ", name=" + name + ", salary="
				+ salary + "]";
	}
	
}
