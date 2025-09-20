package com.example.splitwise_lite.service;

import com.example.splitwise_lite.exception.GroupNotFoundException;
import com.example.splitwise_lite.exception.UserNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import com.example.splitwise_lite.model.Group;
import com.example.splitwise_lite.model.User; // Import the User model
import com.example.splitwise_lite.repository.GroupRepository;
import com.example.splitwise_lite.repository.UserRepository; // Import the UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired // Ask Spring for the UserRepository as well
    private UserRepository userRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    @Transactional
    public Group addUserToGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id:" +userId));

        group.getMembers().add(user);
        return groupRepository.save(group);
    }
}