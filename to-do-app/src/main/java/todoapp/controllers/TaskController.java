package todoapp.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.models.dtos.RegisterUserDTO;
import todoapp.models.dtos.TaskDTO;
import todoapp.models.entities.Task;
import todoapp.models.entities.User;
import todoapp.services.TaskService;
import todoapp.services.UserService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String showTasks(HttpServletRequest request, Model model) {
        System.out.println("Show All Tasks Called!");
        try {
            String username = this.getUserNameFromCookie(request);
            if (!Objects.equals(username, "")) {
                System.out.println("****************************************");
                System.out.println("Username is not empty string! "+ username);
                System.out.println("****************************************");
                User user = this.userService.getUserByUsername(username);
                List<Task> taskList = this.taskService.getAllTasks(user.getId());

                taskList.forEach(t -> {
                    System.out.println("****************************************");
                    System.out.println("*** Title: " + t.getTitle() + "***");
                    System.out.println("*** Description: " + t.getDescription() + "***");
                    System.out.println("*** Status: " + t.getStatus() + "***");
                    System.out.println("*** Start Date: " + t.getStartDate() + "***");
                    System.out.println("*** End Date: " + t.getEndDate() + "***");
                    System.out.println("****************************************");

                });

                model.addAttribute("tasks", taskList);
            }else {
                return "redirect:/auth/login";
            }
        } catch (Exception e) {
            System.out.println("****************************************");
            System.out.println("***--------ERROR IN: Show All Tasks-----------***");
            System.out.println("****************************************");
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        }
        return "task/all";
    }

    @GetMapping("/add")
    public String showCreateTask() {
        return "task/create";
    }

    @PostMapping("/add")
    public String createTask(HttpServletRequest request, TaskDTO taskForm, Model model) {
        try {
            String username = this.getUserNameFromCookie(request);
            if (!username.equals("")) {
                this.taskService.create(taskForm,username);
            }
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "task/create";
        }
    }


    private String getUserNameFromCookie(HttpServletRequest request) {
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        return username;
    }

    //Model attributes
    @ModelAttribute(name = "taskForm")
    public TaskDTO initUserRegFormDto() {
        return new TaskDTO();
    }

}
