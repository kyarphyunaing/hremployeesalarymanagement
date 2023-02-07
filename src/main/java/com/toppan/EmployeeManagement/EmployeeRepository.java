package com.toppan.EmployeeManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	public Optional<Employee> findByLogin(String login);
	public Optional<Employee>  findByIdAndLogin(String id,String login);
	@Query(value="SELECT * FROM employee e WHERE e.salary > ?1  AND e.salary <= ?2 ", nativeQuery = true)
	public List<Employee> findBySalaryBetweenContaining(double minSalary,double maxSalary,Pageable page);
}
