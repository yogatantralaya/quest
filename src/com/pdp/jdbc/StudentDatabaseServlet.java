package com.pdp.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentDatabaseServlet
 */
@WebServlet("/StudentDatabaseServlet")
public class StudentDatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	StudentDbUtil studentDbUtil;
	
	//Tomcat will inject the connection pool object to the dataSource Variable
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	//overriding the init() method to create an instance of the student db
	@Override
	public void init() throws ServletException {
		studentDbUtil = new StudentDbUtil(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//get the command parameter from the request
			String com = request.getParameter("command");
			//if the parameter return null set it to list in order to avoid null pointer exception
			if(com == null) {
				com ="LIST";
			}
			//based on the value of the command parameter call the respective function
			switch(com) {
			case "LIST":{
				listStudents(request,response);
				break;
			}
			case "ADD":{
				addStudents(request,response);
				break;
			}
			case "LOAD":{
				loadStudents(request,response);
			}
			case "UPDATE":{
				updateStudents(request,response);
			}
			case "DELETE":{
				deleteStudents(request,response);
			}
			default:{
				listStudents(request,response);
			}	
			}
		}
		catch(Exception exec) {
			throw new ServletException(exec); 
		}
					
	}
	
	

	private void deleteStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//get the id parameter from the request and convert it to Integer
		int studentId = Integer.parseInt(request.getParameter("id"));
		//pass the id to StudentDbUtil to delete the user in the db
		studentDbUtil.deleteStudent(studentId);
		listStudents(request,response);
		
	}

	private void updateStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//get all the other attributes
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String id = request.getParameter("studentId");
		
		//create a new student object
		Student student = new Student(firstName,lastName,id,email);
		
		//pass the student object to the StudnetDbUtil to update the details on student db
		studentDbUtil.updateStudent(student);
		
		//to go back to the main page
		listStudents(request,response);
		
		
	}

	private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//get the id parameter from the request and convert it to Integer
		int studentId = Integer.parseInt(request.getParameter("id"));
		//get the user which matches this id
		Student theStudent = studentDbUtil.getStudent(studentId);
		//set the user to as a request attribute
		request.setAttribute("THE_STUDENT",theStudent);
		//pass the attribute to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//get the form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a student object
		Student student = new Student(firstName,lastName,email);
		
		//call the addStudent method in the StudnetDbUtil class to add the student info to the student db
		studentDbUtil.addStudent(student);
		
		//to go back to the main page
		listStudents(request,response);
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//get the student list
		List<Student> studentlist = studentDbUtil.getStudents();
		//set the student list to a request Attribute
		request.setAttribute("Student_List",studentlist);
		//pass on the request to the JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view_student_list.jsp");
		dispatcher.forward(request,response);
	}


}

