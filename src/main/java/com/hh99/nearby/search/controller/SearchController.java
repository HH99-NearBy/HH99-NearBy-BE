package com.hh99.nearby.search.controller;

import com.hh99.nearby.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search") //검색기능
    public ResponseEntity<?> search(@RequestParam String keyword,
                                    @RequestParam int pageNum){
        return searchService.search(keyword,pageNum);
    }

    @GetMapping("/api/relation") //연관검색어
    public ResponseEntity<?> relationWord(@RequestParam String word){
        return searchService.relationWord(word);
    }
}
