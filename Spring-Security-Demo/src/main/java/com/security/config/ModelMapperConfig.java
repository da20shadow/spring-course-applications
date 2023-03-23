package com.security.config;

import com.security.goals.models.dtos.AddGoalDTO;
import com.security.goals.models.entities.Goal;
import com.security.ideas.models.dtos.IdeaDTO;
import com.security.ideas.models.entities.Idea;
import com.security.ideas.models.entities.Tag;
import com.security.tasks.models.dtos.AddTaskDTO;
import com.security.tasks.models.entities.Task;
import com.security.utils.converter.StringToLocalDateConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final StringToLocalDateConverter stringToLocalDateConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addIdeaMappings(modelMapper);
//        addGoalMappings(modelMapper);
//        addTaskMappings(modelMapper);
        //TODO: add other mappings if needed

        Converter<String, LocalDateTime> stringToLocalDateTime = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String source) {
                if (source == null) {
                    return null;
                }
                return LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
            }
        };
        Converter<LocalDateTime, String> localDateTimeToString = new AbstractConverter<LocalDateTime, String>() {
            @Override
            protected String convert(LocalDateTime source) {
                if (source == null) {
                    return null;
                }
                return source.format(DateTimeFormatter.ISO_DATE_TIME);
            }
        };
        modelMapper.addConverter(stringToLocalDateTime);
        modelMapper.addConverter(localDateTimeToString);
        return modelMapper;
    }

    private void addIdeaMappings(ModelMapper modelMapper) {
        TypeMap<Idea, IdeaDTO> typeMap = modelMapper.createTypeMap(Idea.class, IdeaDTO.class);
        Converter<Set<Tag>, Set<String>> tagConverter = context -> {
            Set<Tag> source = context.getSource();
            Set<String> target = new HashSet<>();
            for (Tag tag : source) {
                target.add(tag.getName());
            }
            return target;
        };
        typeMap.addMappings(mapper -> mapper.using(tagConverter).map(Idea::getTags, IdeaDTO::setTags));
        // add other mappings specific to Idea and IdeaDTO classes
    }

    private void addGoalMappings(ModelMapper modelMapper) {
        TypeMap<AddGoalDTO, Goal> typeMap =
                modelMapper.createTypeMap(AddGoalDTO.class, Goal.class)
                        .addMappings(mapper -> mapper.using(stringToLocalDateConverter)
                                .map(AddGoalDTO::getDeadline, Goal::setDeadline));

        typeMap.addMappings(mapper -> mapper.skip(Goal::setId));
        // add other mappings specific to AddGoalDTO and Goal classes
    }

    private void addTaskMappings(ModelMapper modelMapper) {
        TypeMap<AddTaskDTO, Task> typeMap =
                modelMapper.createTypeMap(AddTaskDTO.class, Task.class)
                        .addMappings(mapper -> mapper.using(stringToLocalDateConverter)
                                .map(AddTaskDTO::getStartDate, Task::setStartDate))
                        .addMappings(mapper -> mapper.using(stringToLocalDateConverter)
                                .map(AddTaskDTO::getDueDate, Task::setDueDate));

        typeMap.addMappings(mapper -> mapper.skip(Task::setId));
        // add other mappings specific to AddTaskDTO and Task classes
    }

    //TODO: add other mapping methods if needed

}

