package com.mesut.controllers;

import com.mesut.pojo.DatabaseUser;
import com.mesut.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DatabaseApi {
    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/api/databases")
    public ResponseEntity<?> getDatabases() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        List<DatabaseUser> ldb = this.databaseService.getDatabaseByUsername(username);
        return ResponseEntity.ok(ldb);

    }

    @PostMapping("/api/databases")
    public ResponseEntity<?> createDatabases(@RequestBody DatabaseUser dbu) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        };

        if(!this.databaseService.isEmptyDatabase(dbu.getUsername(), dbu.getName())){
            return ResponseEntity.ok("username đã có database này");
        }

        DatabaseUser db = this.databaseService.createDatabase(dbu);

        return ResponseEntity.ok(db);
    }

    @DeleteMapping("/api/databases/{name}")
    public ResponseEntity<?> deleteDatabases(@PathVariable(value = "name") String name) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
            }

            this.databaseService.deleteDatabaseByUsernameAndName(authentication.getName(),name);

            return ResponseEntity.ok("deleted");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
