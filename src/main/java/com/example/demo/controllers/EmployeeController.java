package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.models.Passport;
import com.example.demo.models.Post;
import com.example.demo.models.Supervisor;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.PassportRepository;
import com.example.demo.repo.PostRepository;
import com.example.demo.repo.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;

    @GetMapping("/employee")
    public String employeeMain(Model model)
    {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee-main";
    }

    @GetMapping("/employee/add")
    public String employeeAdd(@ModelAttribute("employees") Employee employee, Model addr)
    {
        Iterable<Post> post = postRepository.findAll();
        addr.addAttribute("post",post);
        Iterable<Passport> passport = passportRepository.findAll();
        addr.addAttribute("passport", passport);
        return "employee-add";
    }

    @PostMapping("/employee/add")
    public String PostEmployeeAdd(@ModelAttribute("employees") Employee employee, @RequestParam String namepost,@RequestParam String number, Model addr)
    {
        Iterable<Post> post = postRepository.findAll();
        addr.addAttribute("post",post);
        Iterable<Passport> passport = passportRepository.findAll();
        addr.addAttribute("passport", passport);
        employee.setPost(postRepository.findByNamepost(namepost));
        employee.setPassport(passportRepository.findByNumber(number));

        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @PostMapping("/employee/filter")
    public String employeeResult(@RequestParam(defaultValue = "") String surname, Model model)
    {
        List<Employee> result = employeeRepository.findBySurnameEquals(surname);
        model.addAttribute("result", result);
        return "employee-main";
    }

    @GetMapping("/employee/{id}/edit")
    public  String EmployeeDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Employee> post = employeeRepository.findById(id);
        ArrayList<Employee> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("employee",res);
        if(!employeeRepository.existsById(id))
        {
            return  "redirect:/employee";
        }
        return "employee-main";
    }

    @PostMapping ("/employee/{id}/edit")
    public  String EmployeeUpdate(@PathVariable(value = "id") long id,
                                   @RequestParam(defaultValue = "")  String surname,
                                   @RequestParam(defaultValue = "0") float height,
                                   @RequestParam(defaultValue = "false")  boolean lovecookie,
                                   @RequestParam(defaultValue = "10") int favnumber,
                                   @RequestParam(defaultValue = "0")  double weight,
                                   Model model)
    {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setSurname(surname);
        employee.setHeight(height);
        employee.setLovecookie(lovecookie);
        employee.setFavnumber(favnumber);
        employee.setWeight(weight);
        employeeRepository.save(employee);
        return "redirect:/employee";
    }


    @GetMapping("/employee/{id}/remove")
    public  String EmployeeDelDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Employee> post = employeeRepository.findById(id);
        ArrayList<Employee> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("employee",res);
        if(!employeeRepository.existsById(id))
        {
            return  "redirect:/employee";
        }
        return EmployeeDelete(id,model);
    }
    @PostMapping("/employee/{id}/remove")
    public String EmployeeDelete(@PathVariable(value = "id") long id, Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/employee";
    }

    @GetMapping("/employee/supervisor/add")
    private String supervisorGetAdd(Model model){
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        Iterable<Supervisor> supervisors = supervisorRepository.findAll();
        model.addAttribute("supervisors", supervisors);
        return "employee-supervisor-add";
    }

    @PostMapping("/employee/supervisor/add")
    public String supervisorAdd(@RequestParam Long employee, @RequestParam Long supervisor, Model model)
    {
        Employee employee2 = employeeRepository.findById(employee).orElseThrow();
        Supervisor supervisor2 = supervisorRepository.findById(supervisor).orElseThrow();
        employee2.getSupervisors().add(supervisor2);
        //university2.getStudents().add(student2);
        employeeRepository.save(employee2);
        return "redirect:/employee";
    }

//    @GetMapping("/human/{id_human}/{id_univ}/remove")
//    public  String humanDelete(@PathVariable(value = "id_employee") long id_employee,@PathVariable(value = "id_univ") Long id_univ, Model model)
//    {
//        Employee employee = employeeRepository.findById(id_employee).orElseThrow();
//        University university2 = universityRepository.findById(id_univ).orElseThrow();
//        human.getUniversities().remove(university2);
//        humanRepository.save(human);
//        return "redirect:/human";
//    }
}
