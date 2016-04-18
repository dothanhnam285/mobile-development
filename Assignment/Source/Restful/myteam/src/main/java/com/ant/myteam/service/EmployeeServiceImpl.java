package com.ant.myteam.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ant.myteam.dao.EmployeeDao;
import com.ant.myteam.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService,Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmployeeDao empDao;
	
	public Employee findEmployeeById(long empId) {
		return empDao.findEmployeeById(empId);
	}

	public boolean addEmployee(Employee emp) {
		return empDao.addEmployee(emp);
	}
	
	public boolean deleteEmployee(Employee emp) {
		return empDao.deleteEmployee(emp);
	}
	
	public boolean updateEmployee(Employee emp) {
		return empDao.updateEmployee(emp);
	}
	
	public List<Employee> findAllEmployees() {
		return empDao.findAllEmployees();
	}

}
