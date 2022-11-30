package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String blogMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "post-main";
    }

    //    @GetMapping("/employee/add")
//
//    @PostMapping("/employee/add")
//    public String employeePostAdd(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult)
//    {
//        if (bindingResult.hasErrors())
//            return "employee-main";
//        employeeRepository.save(employee);
//        return "redirect:/employee";
//    }

   @GetMapping("/post/add")
    public String postAdd(@ModelAttribute("posts") Post post)
    {
        return "post-add";
    }

    @PostMapping("/post/add")
    public String employeePostAdd(@ModelAttribute("posts") @Valid Post post, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return "post-add";
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/post/filter")
    public String postFilter(Model model)
    {
        return "post-filter";
    }

    @PostMapping("/post/filter/result")
    public String postResult(@RequestParam String namepost, Model model)
    {
        Post result = postRepository.findByNamepost(namepost);
        model.addAttribute("result", result);
        return "post-filter";
    }

    @GetMapping("/post/{id}")
    public  String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        if(!postRepository.existsById(id))
        {
            return  "redirect:/post";
        }
        return "post-details";
    }
    @GetMapping("/post/{id}/edit")
    public  String postEdit(@PathVariable(value = "id") long id, Model model)
    {
        Post res = postRepository.findById(id).orElseThrow();
        model.addAttribute("post",res);
        return "post-edit";
    }

    @PostMapping("/post/{id}/edit")
    public  String PostUpdate(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult,
                              @PathVariable(value = "id") long id)
    {
        if (bindingResult.hasErrors())
            return "post-edit";
        postRepository.save(post);
        return "redirect:/";
    }

//    @GetMapping("/post/{id}/edit")
//    public  String blogEdit(@PathVariable(value = "id") long id, Model model)
//    {
//        if(!postRepository.existsById(id))
//        {
//            return  "redirect:/post";
//        }
//        Optional<Post> post = postRepository.findById(id);
//        ArrayList<Post> res = new ArrayList<>();
//        post.ifPresent(res::add);
//        model.addAttribute("post",res);
//        return "post-edit";
//    }
//
//
//    @PostMapping ("/post/{id}/edit")
//    public  String blogPostUpdate(@PathVariable(value = "id") long id,
//                                  @RequestParam(defaultValue = "0")  double maxsalary,
//                                  @RequestParam(defaultValue = "false") boolean paid,
//                                  @RequestParam(defaultValue = "non")  String namepost,
//                                  @RequestParam(defaultValue = "0")  float salary,
//                                  @RequestParam(defaultValue = "0")  int countemploey,
//                                  Model model)
//    {
//        Post post = postRepository.findById(id).orElseThrow();
//        post.setMaxsalary(maxsalary);
//        post.setPaid(paid);
//        post.setNamepost(namepost);
//        post.setSalary(salary);
//        post.setCountemploey(countemploey);
//        postRepository.save(post);
//        return "redirect:/";
//    }


    @PostMapping("/post/{id}/remove")
    public String blogBlogDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }

}
