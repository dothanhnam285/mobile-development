package com.ant.myteam.dao;

import java.util.List;

import com.ant.myteam.model.Employee;

public interface EmployeeDao {

	public boolean addEmployee(Employee emp);
	
	public boolean deleteEmployee(Employee emp);
	
	public boolean updateEmployee(Employee emp);
	
	public Employee findEmployeeById(long empId);
	
	public List<Employee> findAllEmployees();

}
