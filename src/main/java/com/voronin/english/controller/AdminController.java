package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 08.11.2018.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/add-card", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveWord(CardFilled cardFilled, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.wordService.prepareAndSave(cardFilled, photo);
    }
}
