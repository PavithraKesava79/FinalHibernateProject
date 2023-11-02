package com.deeptech.hibernate;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ManageEmployee {
	
	private static SessionFactory sf;

	public static void main(String[] args) {
		
		try
		{
			sf = new Configuration().configure().addAnnotatedClass(Employee.class).buildSessionFactory();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Creating Objects 
		ManageEmployee ME = new ManageEmployee();
		Integer emp1 = ME.addEmployee("Pavithra","K","Developer",250000);
		Integer emp2 = ME.addEmployee("Dhanush","Arjunan","Admin",350000);
		Integer emp3 = ME.addEmployee("Naveen","Kumar","Developer",35000);
		Integer emp4 = ME.addEmployee("Madhan","Kumar","Tester",250000);
		Integer emp5 = ME.addEmployee("Kumar","Dinesg","Developer",65000);
		//ME.listEmployee();
		ME.updateEmployee(emp1,45000);
		ME.listEmployee();
		ME.deleteEmployee(emp5);
		//ME.listEmployee();
	}
	public void deleteEmployee(Integer emp)
	{
		Session s = sf.openSession();
		Transaction tx = null;
		try
		{
			tx = s.beginTransaction();
			Employee empdata = s.get(Employee.class, emp);
			s.delete(empdata);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void updateEmployee(Integer e1,double sal)
	{
		Session s = sf.openSession();
		Transaction tx = null;
		try
		{
			
			tx = s.beginTransaction();
			Employee emp = s.get(Employee.class, e1);
			emp.setSalary(sal);
			s.update(emp);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void listEmployee()
	{
		Session s = sf.openSession();
		Transaction tx = null;
		
		try
		{
			tx = s.beginTransaction();
			Query q = s.createQuery("from Employee");
			List<Employee> e = q.list();
			for(Employee emp :e)
			{
				System.out.println(emp.getFirstname()+"\t"+emp.getLastname()+"\t"+emp.getDesignation()+"\t"+emp.getSalary());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Integer addEmployee(String fname,String lname,String des,double sal)
	{
		Session s = sf.openSession();
		Transaction tx = null;
		Integer employeeID=null;
		try
		{
			tx = s.beginTransaction();
			Employee emp = new Employee();
			emp.setFirstname(fname);
			emp.setLastname(lname);
			emp.setDesignation(des);
			emp.setSalary(sal);
			employeeID = (Integer) s.save(emp);
			System.out.println("Employee Record is Saved");
			tx.commit();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			s.close();
			//sf.close();
		}
		
		return employeeID;
	}
	

}
