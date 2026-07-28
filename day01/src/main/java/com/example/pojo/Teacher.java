package com.example.pojo;

public class Teacher {

    private Integer id;
    private String name;
    private String addr;
    private Integer age;
    private String job;
    private Integer sal;

    public Teacher() {
    }

    public Teacher(Integer id, String name, String addr, Integer age, String job, Integer sal) {
        this.id = id;
        this.name = name;
        this.addr = addr;
        this.age = age;
        this.job = job;
        this.sal = sal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getSal() {
        return sal;
    }

    public void setSal(Integer sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                ", sal=" + sal +
                '}';
    }
}
