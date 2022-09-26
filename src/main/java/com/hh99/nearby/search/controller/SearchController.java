package com.hh99.nearby.search.controller;

import com.hh99.nearby.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public ResponseEntity<?> search(@RequestParam String keyword,
                                    @RequestParam int pageNum){
        return searchService.search(keyword,pageNum);
    }
}
