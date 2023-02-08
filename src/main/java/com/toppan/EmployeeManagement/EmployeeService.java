package com.toppan.EmployeeManagement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository repository;

	public String save(MultipartFile file) {
		String message="OK";
		try {
			List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
			for(Employee employee:employees) {
				//System.out.println(employee.toString());
				checkAndUpdateCSV(employee);
			}repository.saveAll(employees);
			return message;
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}  catch (Exception e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public List<Employee> filteredEmployees(double minSalary, double maxSalary, int offset, int limit, String sort) {
		try {
			
			Pageable sortColumn =sort.contains("-")?PageRequest.of(offset,limit, Sort.Direction.DESC,sort.substring(1)):
				                                    PageRequest.of(offset,limit, Sort.Direction.ASC,sort.substring(1));
			
			return repository.findBySalaryBetweenContaining(minSalary, maxSalary,sortColumn);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		
	}
	public Integer filteredCountOfEmployees(double minSalary, double maxSalary) {
		try {
			return repository.findBySalaryBetweenContaining(minSalary, maxSalary);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	public Optional<Employee> findEmployeeById(String id) {
		return repository.findById(id);
	}

	public Employee saveEmployee(Employee employee) {
		try {
			if (repository.findById(employee.getId()).isPresent())
				return checkAndUpdateEmployee(employee);
			else {
				if (repository.findByLogin(employee.getLogin()).isPresent())
					return null;
				else
					return repository.save(employee);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public Employee checkAndUpdateEmployee(Employee employee) {
		Optional<Employee> tempEmployee = Optional.of(new Employee());
		if (repository.findById(employee.getId()).isPresent()) {
			if(repository.findByIdAndLogin(employee.getId(),employee.getLogin()).isPresent())
				return repository.save(employee);
			else {
				if (repository.findByLogin(employee.getLogin()).isPresent()) {
					tempEmployee=repository.findByLogin(employee.getLogin());
					tempEmployee.ifPresent(temp -> {
						    temp.setLogin(temp.getId());
					        repository.save(temp);
					});
				}
					return repository.save(employee);
			}
		}else
			throw new RuntimeException("fail ");
		
	}
	public Employee checkAndUpdateCSV(Employee employee) {
		Optional<Employee> tempEmployee = Optional.of(new Employee());
		if (repository.findById(employee.getId()).isPresent()) {
			if(repository.findByIdAndLogin(employee.getId(),employee.getLogin()).isPresent())
				return employee;
			else {
				if (repository.findByLogin(employee.getLogin()).isPresent()) {
					tempEmployee=repository.findByLogin(employee.getLogin());
					tempEmployee.ifPresent(temp -> {
						    temp.setLogin(temp.getId());
					        repository.save(temp);
					});
				}
			}
		}
		return employee;
		
	}
	public void deleteEmployee(String id)
	{
	    repository.deleteById(id);
	}
	
}
