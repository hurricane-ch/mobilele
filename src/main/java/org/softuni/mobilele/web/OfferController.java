package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.enums.EngineEnum;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.service.BrandService;
import org.softuni.mobilele.service.OfferService;
import org.softuni.mobilele.service.exception.ObjectNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/offer")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    @ModelAttribute("engines")
    public EngineEnum[] engines() {
        return EngineEnum.values();
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("createOfferDTO")) {
            model.addAttribute("createOfferDTO", CreateOfferDTO.empty());
        }

        model.addAttribute("brands", brandService.getAllBrands());

        return "offer-add";
    }

    @PostMapping("/add")
    public String add(
            @Valid CreateOfferDTO createOfferDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt,
            @AuthenticationPrincipal UserDetails seller) {

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("createOfferDTO", createOfferDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.createOfferDTO", bindingResult);
            return "redirect:/offer/add";
        }


        UUID newOfferUUID = offerService.createOffer(createOfferDTO, seller);

        return "redirect:/offer/" + newOfferUUID;
    }

    @GetMapping("/{uuid}")
    public String details(@PathVariable("uuid") UUID uuid, Model model,
                          @AuthenticationPrincipal UserDetails viewer) {

        OfferDetailDTO offerDetailDTO = offerService
                .getOfferDetail(uuid, viewer)
                .orElseThrow(() -> new ObjectNotFoundException("Offer with uuid " + uuid + " not found!"));

        model.addAttribute("offer", offerDetailDTO);

        return "details";
    }

    @PreAuthorize("@offerService.isOwner(#uuid, #principal.username)")
    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid,
                         @AuthenticationPrincipal UserDetails principal) {

        offerService.deleteOffer(uuid);

        return "redirect:/offers/all";
    }
}