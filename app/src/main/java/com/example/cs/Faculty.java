package com.example.cs;

public class Faculty {
    private  String Teacher_Name,Teacher_Scale,Teacher_Email,Teacher_Education;
    private int Profile;
    Faculty(){}

    Faculty( int Profile,String Teacher_Name, String Teacher_Scale, String Teacher_Email, String Teacher_Education){
        this.Profile=Profile;
        this.Teacher_Name =Teacher_Name;
        this.Teacher_Scale=Teacher_Scale;
        this.Teacher_Email=Teacher_Email;
        this.Teacher_Education=Teacher_Education;
    }

    public int getProfile() {
        return Profile;
    }

    public void setProfile(int profile) {
        this.Profile=profile;
    }

    public String getTeacher(){
        return Teacher_Name;
    }
    public void setTeacher(String teacher){
        this.Teacher_Name =teacher;
    }

    public String getScale(){
        return Teacher_Scale;
    }
    public void setScale(String scale){
        this.Teacher_Scale=scale;
    }

    public String getEmail(){
        return Teacher_Email;
    }
    public void setEmail(String email){
        this.Teacher_Email=email;
    }

    public String getEducation(){
        return Teacher_Education;
    }
    public void setEducation(String education){
        this.Teacher_Education =education;
    }
}
