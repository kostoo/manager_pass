package com.managerPass.jpa.repository_service;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.PriorityEntity;
import com.managerPass.jpa.repository.PriorityEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PriorityRepositoryService {

    private final PriorityEntityRepository priorityEntityRepository;

    public PriorityEntity getPriorityByName(EPriority name) {
        return priorityEntityRepository.findByName(name).orElseThrow(() ->
             new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not found priority name %s ", name))
        );
    }
}
