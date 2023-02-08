package com.toppan.EmployeeManagement;

import java.util.List;

public class FilteredEmployeeResponse {

	private Integer countOfEmployees;
	private List<Employee> employees;
	public Integer getCountOfEmployees() {
		return countOfEmployees;
	}
	public void setCountOfEmployees(Integer countOfEmployees) {
		this.countOfEmployees = countOfEmployees;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public FilteredEmployeeResponse(Integer countOfEmployees, List<Employee> employees) {
		super();
		this.countOfEmployees = countOfEmployees;
		this.employees = employees;
	}
	public FilteredEmployeeResponse() {
		super();
	}
	
}
