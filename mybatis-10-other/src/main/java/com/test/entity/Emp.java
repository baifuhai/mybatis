package com.test.entity;

public class Emp {
	
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	private Integer deptId;
	private EmpStatus status;

	public Emp(String lastName, String email, String gender, Integer deptId) {
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.deptId = deptId;
	}

	public Emp(String lastName, String email, String gender, Integer deptId, EmpStatus status) {
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.deptId = deptId;
		this.status = status;
	}

	public Emp() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public EmpStatus getStatus() {
		return status;
	}

	public void setStatus(EmpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Emp{" +
				"id=" + id +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", gender='" + gender + '\'' +
				", deptId=" + deptId +
				", status=" + status +
				'}';
	}

}
