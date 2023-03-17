package com.productivity.adminpanel.member;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/management/api/v1/members")
public class MemberManagementController {
    @GetMapping
    @PreAuthorize("hasAuthority('member:read')")
    public ResponseEntity<?> getAllMembers() {
        Map<String, String> members = new HashMap<>();
        members.put("John", "John Profile Details");
        members.put("Mike", "Mike Profile Details");
        members.put("Anna", "Anna Profile Details");
        return ResponseEntity.ok(members);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('member:write')")
    public ResponseEntity<?> registerNewMember(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully created new member!");
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMember(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully created new member!");
        return ResponseEntity.status(201).body(response);
    }
}
