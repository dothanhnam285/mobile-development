package com.ant.myteam.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ant.myteam.model.Employee;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	Log log = LogFactory.getLog(EmployeeDaoImpl.class);
	
	public boolean addEmployee(Employee emp) {
		try {
			 sessionFactory.getCurrentSession().save(emp);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteEmployee(Employee emp) {
		try {
			 sessionFactory.getCurrentSession().delete(emp);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateEmployee(Employee emp) {
		try {
			 sessionFactory.getCurrentSession().update(emp);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public Employee findEmployeeById(long empId) {
		Employee result = new Employee();
		try {
			result=(Employee) sessionFactory.getCurrentSession().get(Employee.class, empId);
			 return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Employee> findAllEmployees() {
		log.info("Get List Emp");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
		return (List<Employee>) criteria.list();
	}

}
