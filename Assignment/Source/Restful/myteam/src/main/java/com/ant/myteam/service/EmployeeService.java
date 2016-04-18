package com.ant.myteam.service;

import java.util.List;

import com.ant.myteam.model.Employee;

public interface EmployeeService {
	
	public boolean addEmployee(Employee emp);
	
	public boolean deleteEmployee(Employee emp);
	
	public boolean updateEmployee(Employee emp);
	
	public Employee findEmployeeById(long empId);
	
	public List<Employee> findAllEmployees();

}
