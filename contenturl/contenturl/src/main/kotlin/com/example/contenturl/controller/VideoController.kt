package com.example.contenturl.controller

import com.example.contenturl.service.RecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/videos")
class VideoController @Autowired constructor(
    private val recipeService: RecipeService
) {

    @GetMapping("/check-recipe/{platformContentId}")
    fun checkRecipe(@PathVariable platformContentId: String): String {
        return recipeService.isRecipe(platformContentId)
    }
}
