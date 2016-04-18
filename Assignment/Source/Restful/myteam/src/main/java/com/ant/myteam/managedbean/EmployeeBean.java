package com.ant.myteam.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ant.myteam.dao.EmployeeDaoImpl;
import com.ant.myteam.model.Employee;
import com.ant.myteam.service.EmployeeService;

@Component("empBean")
public class EmployeeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Log log = LogFactory.getLog(EmployeeBean.class);

	private Employee employee = new Employee();

	@Autowired
	private EmployeeService empService;

	private Employee emp1;
	private Employee emp2;

	private List<Employee> emplist = new ArrayList<Employee>();
	
	private String inputfirstname;
	
	private String inputlastname;

	public EmployeeBean() {
/*		emp1 = new Employee();
		emp1.setFirstName("Huong");
		emp1.setLastName("Nguyen");

		emp2 = new Employee();
		emp2.setFirstName("Khang");
		emp2.setLastName("Le");*/

	}

	public void addEmployee() {
		
		if (inputfirstname != null &&
				inputfirstname != "" &&
				inputlastname != null &&
				inputlastname != "") {
			
			Employee emp = new Employee();
			emp.setFirstName(inputfirstname);
			emp.setLastName(inputlastname);
			empService.addEmployee(emp);
			
			inputfirstname = "";
			inputlastname = "";
			
		}
	}
	
	public void deleteEmployee(Employee emp) {
		empService.deleteEmployee(emp);
	}
	
	public void updateEmployee(Employee emp) {
		empService.updateEmployee(emp);
	}
	
	public void show() {
		log.info("oi that la badao");
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public List<Employee> getEmplist() {
		emplist = empService.findAllEmployees();
		return emplist;
	}
	
	public void setEmplist(List<Employee> emplist) {
		this.emplist = emplist;
	}
	
	public String getInputfirstname() {
		return inputfirstname;
	}
	
	public void setInputfirstname(String inputfirstname) {
		this.inputfirstname = inputfirstname;
	}
	
	public String getInputlastname() {
		return inputlastname;
	}
	
	public void setInputlastname(String inputlastname) {
		this.inputlastname = inputlastname;
	}
}
