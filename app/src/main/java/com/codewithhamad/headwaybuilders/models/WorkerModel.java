package com.codewithhamad.headwaybuilders.models;

public class WorkerModel {

    private int workerId;
    private int buildingId;
    private String workerName;
    private String job;
    private int salary;

    public WorkerModel(int workerId, int buildingId, String workerName, String job, int salary) {
        this.workerId = workerId;
        this.buildingId = buildingId;
        this.workerName = workerName;
        this.job = job;
        this.salary = salary;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
