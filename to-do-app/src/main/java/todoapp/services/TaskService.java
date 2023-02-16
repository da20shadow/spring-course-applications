package todoapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todoapp.constants.Messages;
import todoapp.exceptions.ResourceNotFoundException;
import todoapp.models.dtos.TaskDTO;
import todoapp.models.entities.Task;
import todoapp.models.entities.User;
import todoapp.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<Task> getAllTasks(Long userId) {

        return this.taskRepository.findAllByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.NO_TASKS_YET));
    }

    public void create(TaskDTO taskForm, String username) {
        User user = this.userService.getUserByUsername(username);
        if (user == null){
            throw new ResourceNotFoundException(Messages.USER_NOT_FOUND);
        }
        taskForm.setUserId(user.getId());
        System.out.println("************** TASK TO INSERT ***************");
        System.out.println("Title: " + taskForm.getTitle());
        System.out.println("Status: " + taskForm.getStatus());
        System.out.println("Description: " + taskForm.getDescription());
        System.out.println("endDate: " + taskForm.getEndDate());
        System.out.println("endDate: " + taskForm.getUserId());
        System.out.println("*********************************************");
        Task task = this.modelMapper.map(taskForm,Task.class);

        System.out.println("************** MAPPED TASK ***************");
        System.out.println("Title: " + task.getTitle());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Description: " + task.getDescription());
        System.out.println("endDate: " + task.getEndDate());
        System.out.println("*********************************************");

        this.taskRepository.saveAndFlush(task);
    }
}
