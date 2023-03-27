package com.security.tasks.services;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.dtos.SuccessResponseDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Optional<Task> existTask = taskRepository.findByTitleAndTargetIdAndUserId(addTaskDTO.getTitle(), addTaskDTO.getTargetId(), user.getId());
            if (existTask.isPresent()) {
                throw new DuplicateTaskTitleException(TaskMessages.ErrorMessages.DUPLICATE_TITLE_ERROR);
            }

            Optional<Target> optionalTarget = targetRepository.findByIdAndUserId(addTaskDTO.getTargetId(), user.getId());
            if (optionalTarget.isPresent()) {
                task.setTarget(optionalTarget.get());
            } else {
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

        if (Objects.equals(editTaskDTO.isImportant(), Boolean.TRUE)) {
            task.setImportant(true);
        } else if (Objects.equals(editTaskDTO.isImportant(), Boolean.FALSE)) {
            task.setImportant(false);
        }

        if (Objects.equals(editTaskDTO.isUrgent(), Boolean.TRUE)) {
            task.setUrgent(true);
        } else if (Objects.equals(editTaskDTO.isUrgent(), Boolean.FALSE)) {
            task.setUrgent(false);
        }

        if (editTaskDTO.getStartDate() != null) {
            if (editTaskDTO.getStartDate().equals("clear")) {
                task.setStartDate(null);
            } else {
                LocalDateTime startDate = LocalDateTime.parse(editTaskDTO.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                task.setStartDate(startDate);
            }
        }

        if (editTaskDTO.getDueDate() != null) {
            if (editTaskDTO.getDueDate().equals("clear")) {
                task.setDueDate(null);
            } else {
                LocalDateTime dueDate = LocalDateTime.parse(editTaskDTO.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                task.setDueDate(dueDate);
            }
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

    public List<TaskDTO> getTasksWithEndDateToday(Long userId) {
        LocalDate today = LocalDate.now();
        List<Task> todayTasks = taskRepository.findAllByDueDateAndUserId(today,userId);
        return todayTasks.stream().map(t -> modelMapper.map(t,TaskDTO.class)).collect(Collectors.toList());
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

