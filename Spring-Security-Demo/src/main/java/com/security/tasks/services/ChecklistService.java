package com.security.tasks.services;

import com.security.auth.user.models.entities.User;
import com.security.shared.models.dtos.SuccessResponseDTO;
import com.security.tasks.constants.TaskMessages;
import com.security.tasks.exceptions.DuplicateTaskTitleException;
import com.security.tasks.exceptions.TaskNotFoundException;
import com.security.tasks.models.dtos.*;
import com.security.tasks.models.entities.ChecklistItem;
import com.security.tasks.models.entities.Task;
import com.security.tasks.repositories.ChecklistRepository;
import com.security.tasks.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistItemRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public AddChecklistItemSuccessResponseDTO add(Long taskId, User user, AddChecklistItemDTO checklistItem) {
        Optional<ChecklistItem> checklistItemDB =
                checklistItemRepository.findByTitleAndTaskIdAndUserId(checklistItem.getTitle(),taskId,user.getId());
        if (checklistItemDB.isPresent()) {
            throw new DuplicateTaskTitleException(TaskMessages.ErrorMessages.DUPLICATE_CHECKLIST_ITEM_TITLE_ERROR);
        }

        Optional<Task> taskFromDB = taskRepository.findByIdAndUserId(taskId,user.getId());
        if (taskFromDB.isEmpty()) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.NOT_FOUND);
        }

        Task task = taskFromDB.get();
        ChecklistItem item = ChecklistItem.builder()
                .title(checklistItem.getTitle())
                .completed(false)
                .user(user)
                .task(task)
                .build();
        ChecklistItemDTO addedItem = modelMapper.map(checklistItemRepository.save(item),ChecklistItemDTO.class);
        return new AddChecklistItemSuccessResponseDTO(TaskMessages.SuccessMessages.ADD_CHECKLIST_ITEM_SUCCESS,addedItem);
    }

    public List<CheckTaskDTO> getAllByTaskId(Long taskId, Long userId) {
        List<ChecklistItem> checklistItems = checklistItemRepository.findAllByTaskIdAndUserId(taskId, userId);
        return checklistItems.stream()
                .map(checklistItem -> modelMapper.map(checklistItem, CheckTaskDTO.class))
                .collect(Collectors.toList());
    }

    public SuccessResponseDTO deleteItem(Long itemId,Long userId) {
        Optional<ChecklistItem> item = checklistItemRepository.findByIdAndUserId(itemId, userId);
        if (item.isEmpty()) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.CHECKLIST_ITEM_NOT_FOUND);
        }
        checklistItemRepository.delete(item.get());
        return new SuccessResponseDTO(TaskMessages.SuccessMessages.DELETE_CHECKLIST_ITEM_SUCCESS);
    }

    public EditChecklistItemSuccessResponseDTO editItem(Long itemId, Long userId, EditChecklistItemDTO item) {
        Optional<ChecklistItem> itemFromDB = checklistItemRepository.findByIdAndUserId(itemId, userId);
        if (itemFromDB.isEmpty()) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.CHECKLIST_ITEM_NOT_FOUND);
        }

        boolean hasChanges = false;
        ChecklistItem itemToUpdate = itemFromDB.get();

        if (item.getTitle() != null) {
            itemToUpdate.setTitle(item.getTitle());
            hasChanges = true;
        }

        boolean isCompleted = Objects.requireNonNullElse(item.isCompleted(), itemToUpdate.isCompleted());

        if (isCompleted != itemToUpdate.isCompleted()) {
            itemToUpdate.setCompleted(isCompleted);
            hasChanges = true;
        }

        if (!hasChanges) {
            throw new TaskNotFoundException(TaskMessages.ErrorMessages.NO_CHANGE);
        }

        ChecklistItemDTO updatedItem = modelMapper.map(checklistItemRepository.save(itemToUpdate),ChecklistItemDTO.class);
        return new EditChecklistItemSuccessResponseDTO(TaskMessages.SuccessMessages.UPDATE_CHECKLIST_ITEM_SUCCESS,updatedItem);
    }

}