package com.managerPass.provider;

import com.managerPass.jpa.entity.Enum.EPriority;
import com.managerPass.jpa.entity.PriorityEntity;
import com.managerPass.provider.repository.PriorityRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriorityProvider {

    @Autowired
    private PriorityRepositoryTest priorityRepositoryTest;

    public PriorityEntity priorityGenerate(EPriority ePriority) {
        PriorityEntity priority = new PriorityEntity();
        priority.setName(ePriority);

       return priorityRepositoryTest.save(priority);
    }

}
