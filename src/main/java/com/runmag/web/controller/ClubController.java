package com.runmag.web.controller;

import com.runmag.web.dto.ClubDto;
import com.runmag.web.models.Club;
import com.runmag.web.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {
    private ClubService clubService;
    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model){
        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/{id}")
    public String clubDetail(@PathVariable("id") long id, Model model){
        ClubDto clubDto = clubService.findClubById(id);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model){
        Club club = new Club();
        model.addAttribute("club",club);
        return "clubs-create";
    }

    @GetMapping("/clubs/{id}/delete")
    public String deleteClub(@PathVariable("id") Long id){
        clubService.delete(id);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model){
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{id}/edit")
    public String editClubsForm(@PathVariable("id") Long id, Model model){
        ClubDto club = clubService.findClubById(id);
        model.addAttribute("club", club);
        return "clubs-edit";
    }
    @PostMapping(("/clubs/{id}/edit"))
    public String updateClub(@PathVariable("id") Long id, @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("club", club);
            return "clubs-edit";
        }
        club.setId(id);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }
}
