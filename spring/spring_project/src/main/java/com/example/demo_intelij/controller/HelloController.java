package com.example.demo_intelij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_intelij.testdb.Sample;

@RestController
public class HelloController {

    @Autowired
    private SampleRepository sampleRepository;

    @RequestMapping("/hello")
    @CrossOrigin("http://localhost:3001")
    public String hello(){
        return "hello world on docker!!!!!";
    }

    @RequestMapping("/testdb")
    @CrossOrigin("http://localhost:3001")
    public String getData(){
        return sampleRepository.findAll().get(0).getColumn1();
    }
}

interface SampleRepository extends JpaRepository<Sample, Long>{
}
