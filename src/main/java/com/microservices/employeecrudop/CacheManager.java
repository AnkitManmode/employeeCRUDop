package com.microservices.employeecrudop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CacheManager {

    @Autowired
    EmployeeRepository employeeRepository;
    public static HashMap<Integer,Employee> Cache = new HashMap<>();

    @Scheduled(cron = "* * * * * *")
    public void loadCache()
    {
        System.out.println("Cache loading started");
       List<Employee>employeeList = employeeRepository.findAll();
       employeeList.forEach(employee ->Cache.put(employee.getEmpId(),employee));
        System.out.println("Cache loading ended");
    }
}
