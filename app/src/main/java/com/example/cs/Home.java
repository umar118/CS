package com.example.cs;

public class Home {

    private  String Profile,RollNo,Name,Caste,Department,Batch,Semester,Year;


    public Home(){}

    public Home(String Profile,String RollNo, String Name, String Caste, String Department, String Batch, String Semester, String Year){

        this.Profile =Profile;
        this.RollNo =RollNo;
        this.Name =Name;
        this.Caste=Caste;
        this.Department =Department;
        this.Batch=Batch;
        this.Semester=Semester;
        this.Year =Year;
    }

   public String getProfile(){
        return Profile;
    }
    public void setProfile(String profile){
        this.Profile=profile;
    }

    public String getRollNo()
    {
        return RollNo;
    }
    public void setRollNo(String rollNo){
        this.RollNo=rollNo;
    }

    public String getName(){
        return Name;
    }
    public void setName(String name){
        this.Name=name;
    }

    public String getCaste() {
        return Caste;
    }
    public void setCaste(String caste){
        this.Caste=caste;
    }

    public String getDepartment(){
        return Department;
    }
    public void setDepartment(String department){
        this.Department=department;
    }

    public String getBatch(){
        return Batch;
    }
    public void setBatch(String batch){
        this.Batch=batch;
    }

    public String getSemester(){
        return Semester;
    }
    public void setSemester(String semester){
        this.Semester=semester;
    }

    public String getYear(){
        return Year;
    }
    public void setYear(String year){
        this.Year=year;
    }
}
