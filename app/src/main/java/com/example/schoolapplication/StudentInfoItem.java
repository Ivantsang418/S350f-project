package com.example.schoolapplication;

public class StudentInfoItem {
    String name;
    String studentId;
    String phone;
    String email;
    String username;
    public StudentInfoItem(String username, String name, String studentId, String phone, String email) {
        this.username = username;
        this.studentId = studentId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
