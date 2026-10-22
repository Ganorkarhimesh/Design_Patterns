package com.smarthospital.model;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String disease;

    public Patient(int id, String name, int age, String gender, String phone, String disease) {
        this.id=id; this.name=name; this.age=age; this.gender=gender; this.phone=phone; this.disease=disease;
    }
    public Patient(String name, int age, String gender, String phone, String disease) {
        this(0,name,age,gender,phone,disease);
    }
    public int getId(){return id;} public void setId(int v){id=v;}
    public String getName(){return name;} public int getAge(){return age;}
    public String getGender(){return gender;} public String getPhone(){return phone;}
    public String getDisease(){return disease;}
}
