package com.security.ideas.services;

import com.security.auth.user.models.entities.User;
import com.security.goals.constants.GoalMessages;
import com.security.goals.exceptions.GoalNotFoundException;
import com.security.goals.models.entities.Goal;
import com.security.goals.repositories.GoalRepository;
import com.security.ideas.constants.IdeaMessages;
import com.security.ideas.exceptions.*;
import com.security.ideas.models.dtos.AddIdeaDTO;
import com.security.ideas.models.dtos.EditIdeaDTO;
import com.security.ideas.models.dtos.IdeaDTO;
import com.security.ideas.models.entities.Idea;
import com.security.ideas.models.entities.Tag;
import com.security.ideas.repositories.IdeaRepository;
import com.security.ideas.repositories.TagRepository;
import com.security.shared.models.dtos.SuccessResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IdeaService {

    private final IdeaRepository ideaRepository;
    private final GoalRepository goalRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public IdeaDTO add(User user, AddIdeaDTO addIdeaDTO) {

        // Check if an idea with the same title already exists
        Optional<Idea> optionalIdea = ideaRepository.findByTitle(addIdeaDTO.getTitle());
        if (optionalIdea.isPresent()) {
            throw new DuplicateIdeaException(IdeaMessages.IdeaErrorMessages.DUPLICATE_TITLE_ERROR);
        }

        // Create a new idea and set the properties
        Idea idea = new Idea();
        idea.setTitle(addIdeaDTO.getTitle());
        idea.setDescription(addIdeaDTO.getDescription());

        idea.setCreatedAt(LocalDateTime.now());
        idea.setUser(user);
        System.out.println("GOAL ID: " + addIdeaDTO.getGoalId());
        if (addIdeaDTO.getGoalId() != null) {
            Goal goal = goalRepository.findByIdAndUserId(addIdeaDTO.getGoalId(), user.getId())
                    .orElseThrow(() -> new GoalNotFoundException(GoalMessages.ErrorGoalMessages.NOT_FOUND));

            System.out.println("----------- GOAL FROM DB ---------");
            System.out.println(goal.toString());
            System.out.println("GOAL ID FROM DB: " + goal.getId());
            idea.setGoal(goal);

        }

        // Save the new idea to the database
        Idea savedIdea = ideaRepository.save(idea);

        if (addIdeaDTO.getTags() != null) {
            Set<Tag> newTags = new HashSet<>();
            for (String newTagName : addIdeaDTO.getTags()) {
                // Check if there is an existing tag with the same name
                Tag existingTag = tagRepository.findByName(newTagName);
                if (existingTag != null) {
                    newTags.add(existingTag);
                } else {
                    // If there is no existing tag, create a new one
                    Tag newTag = new Tag(newTagName);
                    newTags.add(newTag);
                }
            }
            savedIdea.setTags(newTags);
            savedIdea = ideaRepository.save(idea);
        }
        // Convert the idea entity to a DTO and return it
        return modelMapper.map(savedIdea, IdeaDTO.class);
    }

    public IdeaDTO update(Long id, Long userId, EditIdeaDTO editIdeaDTO) {
        // Retrieve the existing Idea entity from the database
        Idea idea = ideaRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IdeaNotFoundException(IdeaMessages.IdeaErrorMessages.IDEA_NOT_FOUND_ERROR));

        boolean isThereChange = false;

        // Update the title, if provided in the DTO
        if (editIdeaDTO.getTitle() != null && !editIdeaDTO.getTitle().equals(idea.getTitle())) {
            Optional<Idea> existingIdeaWithTitle = ideaRepository.findByTitle(editIdeaDTO.getTitle());
            if (existingIdeaWithTitle.isPresent()) {
                throw new DuplicateIdeaException(IdeaMessages.IdeaErrorMessages.DUPLICATE_TITLE_ERROR);
            }
            idea.setTitle(editIdeaDTO.getTitle());
            isThereChange = true;
        }

        // Update the description, if provided in the DTO
        if (editIdeaDTO.getDescription() != null) {
            idea.setDescription(editIdeaDTO.getDescription());
            isThereChange = true;
        }

        // Update the tags, if provided in the DTO
        if (editIdeaDTO.getTags() != null && !editIdeaDTO.getTags().isEmpty()) {
            Set<String> newTagNames = editIdeaDTO.getTags();
            Set<Tag> newTags = new HashSet<>();
            for (String newTagName : newTagNames) {
                // Check if there is an existing tag with the same name
                Tag existingTag = tagRepository.findByName(newTagName);
                if (existingTag != null) {
                    newTags.add(existingTag);
                } else {
                    // If there is no existing tag, create a new one
                    Tag newTag = new Tag(newTagName);
                    newTags.add(newTag);
                }
            }
            idea.setTags(newTags);
            isThereChange = true;
        }

        if (!isThereChange) {
            throw new UpdateIdeaException(IdeaMessages.IdeaErrorMessages.NOTHING_TO_UPDATE_ERROR);
        }

        // Save the updated Idea entity to the database
        Idea updatedIdea = ideaRepository.save(idea);

        // Convert the updated Idea entity to a DTO and return it
        return modelMapper.map(updatedIdea, IdeaDTO.class);
    }

    public SuccessResponseDTO deleteIdea(Long id, User user) {

        // Retrieve the existing Idea entity from the database
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new IdeaNotFoundException(IdeaMessages.IdeaErrorMessages.IDEA_NOT_FOUND_ERROR));

        //Check if the user is owner of the idea
        if (!idea.getUser().getEmail().equals(user.getEmail())) {
            throw new IdeaNotFoundException(IdeaMessages.IdeaErrorMessages.IDEA_NOT_FOUND_ERROR);
        }

        ideaRepository.delete(idea);
        return new SuccessResponseDTO(IdeaMessages.IdeaSuccessMessages.DELETE_SUCCESS);
    }

    public IdeaDTO getIdeaById(Long id, Long userId) {

        // Retrieve the existing Idea entity from the database
        Idea idea = ideaRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IdeaNotFoundException(IdeaMessages.IdeaErrorMessages.IDEA_NOT_FOUND_ERROR));

        return modelMapper.map(idea, IdeaDTO.class);
    }

    public Page<IdeaDTO> findByUserId(Long userId, Pageable pageable) {

        // Retrieve the existing user Ideas from the database
        Page<Idea> ideasPage = ideaRepository.findAllByUserId(userId, pageable);

        if (ideasPage == null) {
            throw new NoIdeasYetException(IdeaMessages.IdeaErrorMessages.NO_IDEAS_YET_ERROR);
        }

        return ideasPage.map(i -> modelMapper.map(i, IdeaDTO.class));
    }

    public Page<IdeaDTO> getIdeasByTag(Long userId, String tagName, Pageable pageable) {
        Optional<Tag> optionalTag = Optional.ofNullable(tagRepository.findByName(tagName));
        if (optionalTag.isEmpty()) {
            throw new IdeaTagNotFoundException(IdeaMessages.IdeaErrorMessages.NO_IDEAS_BY_TAG);
        }
        Page<Idea> ideasPage = ideaRepository.findByUserIdAndTags_Id(userId, optionalTag.get().getId(), pageable);
        return ideasPage.map(idea -> modelMapper.map(idea, IdeaDTO.class));
    }

    public Set<String> getUserIdeaTags(Long userId) {
        List<Idea> ideas = ideaRepository.findAllByUserId(userId);

        Set<String> tags = new HashSet<>();
        for (Idea idea : ideas) {
            for (Tag tag : idea.getTags()) {
                tags.add(tag.getName());
            }
        }

        return tags;
    }

    public Set<String> getGoalIdeasTags(Long userId,Long goalId) {
        List<Idea> ideas = ideaRepository.findAllByUserIdAndGoalId(userId,goalId);

        Set<String> tags = new HashSet<>();
        for (Idea idea : ideas) {
            for (Tag tag : idea.getTags()) {
                tags.add(tag.getName());
            }
        }

        return tags;
    }

    public Page<IdeaDTO> getGoalIdeas(Long userId, Long goalId, Pageable pageable) {
        Page<Idea> ideasPage = ideaRepository.findByUserIdAndGoalId(userId, goalId, pageable);
        return ideasPage.map(idea -> {
            IdeaDTO ideaDTO = modelMapper.map(idea, IdeaDTO.class);
            ideaDTO.setGoalId(idea.getGoal().getId());
            return ideaDTO;
        });
    }

    public Page<IdeaDTO> getGoalIdeasByTag(Long userId, Long goalId, String tagName, Pageable pageable) {
        Optional<Tag> optionalTag = Optional.ofNullable(tagRepository.findByName(tagName));
        if (optionalTag.isEmpty()) {
            throw new IdeaTagNotFoundException(IdeaMessages.IdeaErrorMessages.NO_IDEAS_BY_TAG);
        }
        Page<Idea> ideasPage =
                ideaRepository.findByUserIdAndGoalIdAndTags_Id(
                        userId,
                        goalId,
                        optionalTag.get().getId(),
                        pageable
                );
        return ideasPage.map(idea -> modelMapper.map(idea, IdeaDTO.class));
    }
}
