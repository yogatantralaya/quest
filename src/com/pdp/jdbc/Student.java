package com.pdp.jdbc;

public class Student {

	String firstName;
	String lastName;
	String id;
	String email;
	
	public Student(String firstName, String lastName, String id, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
	}

	public Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", id=" + id + ", email=" + email
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getId()=" + getId()
				+ ", getEmail()=" + getEmail() + "]";
	}	
	
}
