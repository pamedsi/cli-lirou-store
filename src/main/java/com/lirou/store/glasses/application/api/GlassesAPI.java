package com.lirou.store.glasses.application.api;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lirou.store.shared.validation.identifierValidator.ValidIdentifier;
import com.lirou.store.models.Message;

@RestController
@RequestMapping("/api/glasses")
public interface GlassesAPI {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<GlassesDTO>> getGlasses(@PageableDefault(size = 24, direction = Sort.Direction.ASC, sort = { "title" }) Pageable pageable);

    @GetMapping("/{identifier}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GlassesDTO> findSingleGlasses(@PathVariable("identifier") @ValidIdentifier String identifier);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Message> postGlasses(@RequestBody @Valid GlassesRequestDTO glassesDTO);

    @PutMapping("/{identifier}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> putGlasses(@RequestBody @Valid GlassesRequestDTO glassesDTO, @PathVariable("identifier") String glassesIdentifier);

    @DeleteMapping("/{identifier}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> deleteGlasses(@PathVariable("identifier") String glassesIdentifier);

    @PatchMapping("/{identifier}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> changeAvailability(@PathVariable("identifier") String glassesIdentifier, @RequestBody @Valid GlassesAvailability availability);
}
