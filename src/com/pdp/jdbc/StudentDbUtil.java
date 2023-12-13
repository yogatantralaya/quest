package com.pdp.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	DataSource dataSource;
	
	//Servlet will pass on the connection pool to the dataSource variable in StudentDbUtil class
	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//add the students to a list and return the list
	public List<Student> getStudents() throws SQLException{
		
		List<Student> studentList = new ArrayList<>();
		
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRes = null;
		
		try {
		//create a connection to the db
		myCon = dataSource.getConnection();
		//create an sql statement
		String query = "select * from student order by last_name";
		myStmt = myCon.createStatement();
		//execute the sql query constructed
		myRes = myStmt.executeQuery(query);
		//extract the data retrieved from the db and add it to the student list and return the list
		while(myRes.next()) {
			String id = myRes.getString("id");
			String firstName = myRes.getString("first_name");
			String lastName = myRes.getString("last_name");
			String email = myRes.getString("email");
			
			Student tempStudent = new Student(firstName,lastName,id,email);
			
			studentList.add(tempStudent);
			
		}
		return studentList;
		}
		finally {
		//close the connection
			closeConnection(myCon,myStmt,myRes);
		}
	}
	
	public void addStudent(Student student) throws Exception {

		
		Connection myCon = null;
		PreparedStatement myStmt = null;
		
		try {
		//create a connection to the db
		myCon = dataSource.getConnection();
		//create an sql statement
		String query = "INSERT INTO student" 
				+ "(first_name,last_name,email)" 
				+ "values(?,?,?)";
		myStmt = myCon.prepareStatement(query);
		
		//setting the values for the place holders
		myStmt.setString(1, student.getFirstName());
		myStmt.setString(2, student.getLastName());
		myStmt.setString(3, student.getEmail());
		
		myStmt.execute();

		}
		finally {
		//close the connection
			closeConnection(myCon,myStmt,null);
		}
	}
	
	public Student getStudent(int studentId) throws Exception {
		
		//create a student object
		Student theStudent;
		
		//initialize the sql attributes
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRes = null;
		
		try {
			
		//create a connection	
		myCon = dataSource.getConnection();
		
		//prepare the statement
		String query = "select * from student where id=?";
		myStmt = myCon.prepareStatement(query);
		
		myStmt.setInt(1, studentId);
		
		//execute query
		myRes = myStmt.executeQuery();
		
		//get the student details and create a student object
		if(myRes.next()) {
			String firstName = myRes.getString("first_name");
			String lastName = myRes.getString("last_Name");
			String email = myRes.getString("email");
			String id = myRes.getString("id");
			
			theStudent = new Student(firstName,lastName,id,email);
		}
		else {
			throw new Exception("Student id not present in the database" + studentId);
		}
		return theStudent;
		}
		
		finally {
			closeConnection(myCon,myStmt,myRes);
		}
	}
	
	public void updateStudent(Student student) throws Exception {
		//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
		//initialize the sql attributes
		
		int id =Integer.parseInt(student.getId());
		Connection myCon = null;
		PreparedStatement myStmt = null;
				
		try {
			
			//create a connection	
			myCon = dataSource.getConnection();
			
			//prepare the statement
			String query = "update student set first_name=?,last_name=?,email=? where id=?";
			myStmt = myCon.prepareStatement(query);
			
			myStmt.setString(1,student.getFirstName());
			myStmt.setString(2,student.getLastName());
			myStmt.setString(3,student.getEmail());
			myStmt.setInt(4,id);;
			
			//execute query
			myStmt.execute();
		}
		finally {
			closeConnection(myCon,myStmt,null);
				}
	}
	
	public void deleteStudent(int studentId) throws Exception {
		//delete from table_name WHERE condition;
		//initialize the sql attributes
		Connection myCon = null;
		PreparedStatement myStmt = null;
				
		try {
			
			//create a connection	
			myCon = dataSource.getConnection();
			
			//prepare the statement
			String query = "delete from student where id=?";
			myStmt = myCon.prepareStatement(query);
			
			myStmt.setInt(1,studentId);;
			
			//execute query
			myStmt.execute();
		}
		finally {
			closeConnection(myCon,myStmt,null);
				}
		
	}

	private void closeConnection(Connection myCon, Statement myStmt, ResultSet myRes) throws SQLException {
		try {
		if(myStmt != null) {
			myStmt.close();
		}
		if(myRes != null) {
			myRes.close();
		}
		if(myCon != null) {
			myCon.close();//this will not necessarily close the db connection but will return the connection pool
		}	
		}
		catch(Exception exec) {
			exec.printStackTrace();
		}
	}
}
