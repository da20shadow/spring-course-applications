package com.security.tasks.services;

import com.security.auth.user.models.entities.User;
import com.security.targets.constants.TargetMessages;
import com.security.targets.exceptions.TargetNotFoundException;
import com.security.targets.models.entities.Target;
import com.security.targets.repositories.TargetRepository;
import com.security.tasks.constants.TaskMessages;
import com.security.tasks.exceptions.DuplicateTaskTitleException;
import com.security.tasks.exceptions.TaskNotFoundException;
import com.security.tasks.models.dtos.AddTaskDTO;
import com.security.tasks.models.dtos.EditTaskDTO;
import com.security.tasks.models.dtos.TaskDTO;
import com.security.tasks.models.entities.Task;
import com.security.tasks.models.enums.TaskStatus;
import com.security.tasks.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TargetRepository targetRepository;
    private final ModelMapper modelMapper;

    public TaskDTO addTask(AddTaskDTO addTaskDTO, User user) {
        Task task = modelMapper.map(addTaskDTO, Task.class);
        task.setChecklist(new HashSet<>());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        if (addTaskDTO.getTargetId() != null) {
            Optional<Task> existTask = taskRepository.findByTitleAndTargetIdAndUserId(addTaskDTO.getTitle(),addTaskDTO.getTargetId(), user.getId());
            if (existTask.isPresent()) {
                throw new DuplicateTaskTitleException(TaskMessages.ErrorMessages.DUPLICATE_TITLE_ERROR);
            }

            Optional<Target> optionalTarget = targetRepository.findByIdAndUserId(addTaskDTO.getTargetId(), user.getId());
            if (optionalTarget.isPresent()) {
                task.setTarget(optionalTarget.get());
            }else {
                throw new TargetNotFoundException(TargetMessages.ErrorMessages.NOT_FOUND);
            }
        }

        task = taskRepository.save(task);
        return toTaskDTO(task);
    }

    public TaskDTO updateTask(Long taskId, EditTaskDTO editTaskDTO, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);

        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.NOT_FOUND);
        }
        Task task = optionalTask.get();
        if (editTaskDTO.getTitle() != null) {
            task.setTitle(editTaskDTO.getTitle());
        }
        if (editTaskDTO.getDescription() != null) {
            task.setDescription(editTaskDTO.getDescription());
        }
        if (editTaskDTO.getStatus() != null) {
            task.setStatus(editTaskDTO.getStatus());
        }
        if (editTaskDTO.getPriority() != null) {
            task.setPriority(editTaskDTO.getPriority());
        }
        if (editTaskDTO.isUrgent()) {
            task.setUrgent(editTaskDTO.isUrgent());
        }
        if (editTaskDTO.isImportant()) {
            task.setImportant(editTaskDTO.isImportant());
        }
        if (editTaskDTO.getStartDate() != null) {
            task.setStartDate(editTaskDTO.getStartDate());
        }
        if (editTaskDTO.getDueDate() != null) {
            task.setDueDate(editTaskDTO.getDueDate());
        }
        task = taskRepository.save(task);
        return toTaskDTO(task);
    }

    public void deleteTask(Long taskId, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.NOT_FOUND);
        }
        taskRepository.delete(optionalTask.get());
    }

    public Page<TaskDTO> getAllTasks(Long userId, Pageable pageable) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::toTaskDTO);
    }

    public Page<TaskDTO> getImportantTasks(Long userId, Pageable pageable) {
        return taskRepository.findByUserIdAndImportantTrue(userId, pageable)
                .map(this::toTaskDTO);
    }

    public Page<TaskDTO> getUrgentTasks(Long userId, Pageable pageable) {
        return taskRepository.findByUserIdAndUrgentTrue(userId, pageable).map(this::toTaskDTO);
    }

    public Page<TaskDTO> getTasksByTargetId(Long userId, Long targetId, Pageable pageable) {
        return taskRepository.findByUserIdAndTargetId(userId, targetId, pageable)
                .map(this::toTaskDTO);
    }

    public TaskDTO getTaskByIdAndUserId(Long taskId, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.NOT_FOUND);
        }
        return toTaskDTO(optionalTask.get());
    }

    private TaskDTO toTaskDTO(Task task) {
        TaskDTO.TaskDTOBuilder taskBuilder = TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().getStatusName())
                .priority(task.getPriority())
                .urgent(task.isUrgent())
                .important(task.isImportant())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt());

        if (task.getTarget() != null) {
            taskBuilder.targetId(task.getTarget().getId());
        }
        return taskBuilder.build();
    }
}

