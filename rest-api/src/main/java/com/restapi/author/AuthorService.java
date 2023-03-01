package com.restapi.author;

import com.restapi.author.model.dto.AuthorDTO;
import com.restapi.author.model.entity.AuthorEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    public AuthorDTO getAuthorByEmail(String email) {
        //TODO implement it
        AuthorDTO author = new AuthorDTO();
        author.setEmail(email);
        return author;
    }
}
