package com.company.library.service.impl;

import com.company.library.dto.AuthorDTO;
import com.company.library.exception.ResourceNotFoundException;
import com.company.library.mapper.AuthorMapper;
import com.company.library.model.Author;
import com.company.library.repository.AuthorRepository;
import com.company.library.service.inter.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(AuthorDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.INSTANCE.toEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return AuthorMapper.INSTANCE.toDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setBirthDate(authorDTO.getBirthDate());
        author.setNationality(authorDTO.getNationality());
        author.setBiography(authorDTO.getBiography());
        author.setWebsite(authorDTO.getWebsite());

        Author updatedAuthor = authorRepository.save(author);
        return AuthorMapper.INSTANCE.toDTO(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) throws ResourceNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }
}
