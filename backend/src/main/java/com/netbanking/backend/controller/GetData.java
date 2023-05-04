package com.netbanking.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.dto.DTOConverter;
import com.netbanking.backend.dto.UserDTO;
import com.netbanking.backend.model.TaskRecord;
import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.security.PasswordEncoder;
import com.netbanking.backend.security.UserDetailsImpl;
import com.netbanking.backend.service.TaskService;
import com.netbanking.backend.service.UserService;

import ch.qos.logback.classic.Logger;

@RestController
public class GetData {
    
    @Autowired
    private TaskService taskService;

    private Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(GetData.class);

    @GetMapping("/api/admin/getTasks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<TaskRecord> getPendingTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        // UserRecord user = userDetails.getUser();

        List<TaskRecord> list = taskService.getAllPendingTask();
        logger.error("list: " + list);
        return list;
    }
}
