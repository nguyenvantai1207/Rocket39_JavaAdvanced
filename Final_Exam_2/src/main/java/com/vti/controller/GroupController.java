package com.vti.controller;

import com.vti.DTO.GroupDTO;
import com.vti.filter.GroupFilter;
import com.vti.request.GroupRequest;
import com.vti.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("account/group")
    public Page<GroupDTO> findAllGroups(@RequestBody GroupFilter filter,@RequestParam String search, Pageable pageable) {
        return groupService.getAllGroups(filter, search, pageable);
    }

    @GetMapping("account/group/{id}")
    public ResponseEntity<?> findGroupById(@PathVariable Integer id) {
        return groupService.getGroupById(id);
    }

    @PostMapping("account/group")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        return groupService.createCroup(groupRequest);
    }

    @PutMapping("account/group")
    public ResponseEntity<?> updateGroup(@RequestBody GroupRequest groupRequest) {
        return groupService.updateGroup(groupRequest);
    }

    @DeleteMapping("account/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer id) {
        return groupService.deleteGroup(id);
    }
}
