package com.vti.service;

import com.vti.DTO.GroupDTO;
import com.vti.entity.Account;
import com.vti.entity.Group;
import com.vti.filter.GroupFilter;
import com.vti.repository.AccountRepository;
import com.vti.repository.GroupRepository;
import com.vti.request.GroupRequest;
import com.vti.specification.GroupSpecificationBuilder;
import com.vti.validation.GroupValidate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class GroupService implements IGroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<GroupDTO> getAllGroups(GroupFilter filter, String search, Pageable pageable) {
        GroupSpecificationBuilder groupSpecificationBuilder = new GroupSpecificationBuilder(filter, search);

        Page<Group> groupPage = groupRepository.findAll(groupSpecificationBuilder.build(), pageable);

        Page<GroupDTO> groupDTOPage = groupPage.map(new Function<Group, GroupDTO>() {
            @Override
            public GroupDTO apply(Group group) {
                return modelMapper.map(group, GroupDTO.class);
            }
        });
        return groupDTOPage;
    }

    public ResponseEntity<?> getGroupById(Integer id) {
        Optional<Group> opGroup = groupRepository.findById(id);

        if (opGroup.isEmpty()) {
            return new ResponseEntity<>("Group not found with Id: " + id, HttpStatus.BAD_REQUEST);
        }

        Group group = opGroup.get();

        GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);

        return ResponseEntity.ok(groupDTO);
    }

    public ResponseEntity<?> createCroup(GroupRequest groupRequest) {
        Optional<Group> opGroup = groupRepository.findByGroupName(groupRequest.getGroupName());

        if (opGroup.isEmpty()) {
            Group newGroup = modelMapper.map(groupRequest, Group.class);

            Optional<Account> existingAccount = accountRepository.findById(groupRequest.getCreatorId());
            if (existingAccount.isEmpty()) {
                return new ResponseEntity<>("Account not found with id: " + existingAccount.get().getId(), HttpStatus.BAD_REQUEST);
            }
            //validate groupName
            if (!GroupValidate.validateGroupName(newGroup.getGroupName())) {
                return new ResponseEntity<>("Group name must be in range from 6 to 50 characters and not contain specific character", HttpStatus.BAD_REQUEST);
            }

            //validate Date
            if (!GroupValidate.validateDate(newGroup.getCreateDate())) {
                return new ResponseEntity<>("The createDate must be in the past and greater than the year 2000", HttpStatus.BAD_REQUEST);
            }

            groupRepository.save(newGroup);

            return ResponseEntity.ok("Create Group Successfully!");
        }
        return new ResponseEntity<>("Group existed with name: " + groupRequest.getGroupName(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteGroup(Integer id) {
        Optional<Group> opGroup = groupRepository.findById(id);

        if (opGroup.isEmpty()) {
            return new ResponseEntity<>("Group not found with id: " + id, HttpStatus.BAD_REQUEST);
        }

        Group deleteGroup = opGroup.get();

        groupRepository.deleteGroupById(deleteGroup.getId());

        return ResponseEntity.ok("Delete Successfully!");
    }

    public ResponseEntity<?> updateGroup(GroupRequest groupRequest) {
        Optional<Group> opGroupId = groupRepository.findById(groupRequest.getId());
        if (opGroupId.isEmpty()) {
            return new ResponseEntity<>("Group not found id: " + groupRequest.getId(), HttpStatus.BAD_REQUEST);
        }

        Optional<Group> opGroupName = groupRepository.findByGroupName(groupRequest.getGroupName());

        if (opGroupName.isPresent()) {
            return new ResponseEntity<>("Group name is existed!", HttpStatus.BAD_REQUEST);
        }

        Group newGroup = modelMapper.map(groupRequest, Group.class);

        //validate groupName
        if (!GroupValidate.validateGroupName(newGroup.getGroupName())) {
            return new ResponseEntity<>("Group name must be in range from 6 to 50 characters and not contain specific character", HttpStatus.BAD_REQUEST);
        }

        //validate Date
        if (!GroupValidate.validateDate(newGroup.getCreateDate())) {
            return new ResponseEntity<>("The createDate must be in the past and greater than the year 2000", HttpStatus.BAD_REQUEST);
        }

        groupRepository.save(newGroup);

        return ResponseEntity.ok("Update Successfully!");
    }
}
