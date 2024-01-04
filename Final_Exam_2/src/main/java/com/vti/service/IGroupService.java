package com.vti.service;

import com.vti.DTO.GroupDTO;
import com.vti.filter.GroupFilter;
import com.vti.request.GroupRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IGroupService {
    Page<GroupDTO> getAllGroups(GroupFilter filter, String search, Pageable pageable);

    ResponseEntity<?> getGroupById(Integer id);

    ResponseEntity<?> createCroup(GroupRequest groupRequest);

    ResponseEntity<?> deleteGroup(Integer id);

    ResponseEntity<?> updateGroup(GroupRequest groupRequest);
}
