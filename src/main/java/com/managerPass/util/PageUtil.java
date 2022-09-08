package com.managerPass.util;

import com.managerPass.jpa.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageUtil {

    public List<TaskEntity> convertPageToList(Page<TaskEntity> page) {
        return page.getContent();
    }
}
