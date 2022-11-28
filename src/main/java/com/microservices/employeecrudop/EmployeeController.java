package com.microservices.employeecrudop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/getmsg")
    public String getHelloWorld() {

        return "hello world";
    }

    public HashMap<Integer, Employee> employeelist = new HashMap<>();

    @PostMapping("/createEmp")
    public Employee createEmployee(@RequestBody Employee employee) {
        employeelist.put(employee.getEmpId(), employee);
        return employeelist.get(employee.getEmpId());
    }

    @PostMapping("/createEmpDB")
    public Employee createEmployee1(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/findAllEmployee")
    public List<Employee> findallEmployee() {
        //cache call --> return list from cache
        // return employeeRepository.findAll();
        return CacheManager.Cache.values().stream().collect(Collectors.toList());
    }

    @GetMapping("/findEmpById/{empId}")
    public String findEmpById(@PathVariable int empId) {
        // return employee from cache // do not make a DB call instead just fetch employee from cache and return it
        //  Optional<Employee> employee = employeeRepository.findById(empId);
        //  if (employee.isPresent()) {
        //     return employee.toString();
        //  } else {
        //      return "No employee found for given EmpId";
        //  }
        //}

        // By using cache
        Employee employee = CacheManager.Cache.get(empId);
        if (employee != null) {
            return employee.toString();
        } else {
            return "No employee found for given employee Id";
        }
    }

    @GetMapping("/FindAllEmployee")
    public ResponseEntity<List<Employee>> findallEmployees() {
        return new ResponseEntity<List<Employee>>(employeeRepository.findAll(), HttpStatus.CREATED);
    }


    //    @GetMapping("/showGreet/{name}")
//    public static String showGreet(@PathVariable String name) {
//        Calendar c = Calendar.getInstance();
//        int timeOfDay = c.get(Calendar.getInstance());
//
//        if (timeOfDay >= 7 && timeOfDay <= 12) {
//            return " Good morning";
//        } else if (timeOfDay >= 12 && timeOfDay <= 5) {
//            return " Good Afternoon";
//        } else if (timeOfDay >= 5 && timeOfDay <= 7) {
//            return "Good evening";
//        } else {
//            return "Good gh";
//        }
//    }
    @PutMapping("/updateEmployee/{empId}")
    public String updateEmployee(@PathVariable int empId, @RequestBody Employee employee) {
        //employee1 is from DataBase--> Existing Information
        //employee is from user --> this we want to update
        // transformation

        Optional<Employee> employee1 = employeeRepository.findById(empId);
        if (employee1.isPresent()) {
            Employee updateEmp = employee1.get();
            updateEmp.setEmpName(employee.getEmpName());
            updateEmp.setEmpAddress(employee.getEmpAddress());
            return employeeRepository.save(updateEmp).toString();
        } else {
            return "Employee is not present for the given employee ID";
        }
    }


    // Another Way to Update

//    Optional<Employee> employee2 = employeeRepository.findById(empId);
//    if(employee2.isPresent())
//    {
//        return employeeRepository.save(employee).toString();
//    }else {
//        return "Employee is not present for the given employee ID";s
//    }

    @DeleteMapping("/deleteEmp/{empId}")
    public String deleteEmployee(@PathVariable int empId) {
        Optional<Employee> employee = employeeRepository.findById(empId);
        if (employee.isPresent()) {
            employeeRepository.deleteById(empId);
            return "Employee id is deleted Successfully";
        } else {
            return "Given employee id is not present";
        }
    }

    @GetMapping("/empIdList")
    public String empIdList() {
        List<Integer> employeeIdList = CacheManager.Cache.keySet().stream().collect(Collectors.toList());

        return CacheManager.Cache.values().stream().collect(Collectors.toList()).toString();
    }
}
