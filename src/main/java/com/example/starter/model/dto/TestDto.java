package com.example.starter.model.dto;

public class TestDto {
    private String status;
    public TestDto(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
}