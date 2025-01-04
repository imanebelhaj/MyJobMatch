package ma.xproce.myjobmatch.controllers;


import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.dto.RhProfileDto;
import ma.xproce.myjobmatch.services.RhService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rh")
@PreAuthorize("hasAuthority('RH')")
public class RhController {



    @Autowired
    private RHRepository rhRepository;
    @Autowired
    private RhService rhService;
    @PutMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(@RequestBody RhProfileDto rhProfileDTO, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();

        rh.setCompanyName(rhProfileDTO.getCompanyName());
        rh.setFullName(rhProfileDTO.getFullName());
        rh.setLinkedinUrl(rhProfileDTO.getLinkedinUrl());
        rh.setDepartment(rhProfileDTO.getDepartment());
        rh.setPhone(rhProfileDTO.getPhone());
        rh.setCompanyWebsite(rhProfileDTO.getCompanyWebsite());
        rh.setProfilePictureUrl(rhProfileDTO.getProfilePictureUrl());
        rh.setProfileComplete(true);
        rhRepository.save(rh);
        return ResponseEntity.ok("Profile updated successfully");
    }


    @RequestMapping("/view-profile")
    public ResponseEntity<RhProfileDto> viewProfile(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long rhId = customUserDetails.getRh().getId();

        RhProfileDto rhProfileDto = rhService.getProfileById(rhId);

        return ResponseEntity.ok(rhProfileDto);
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody RhProfileDto rhProfileDto, Authentication authentication) {
        // Get the authenticated RH ID from CustomUserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long rhId = customUserDetails.getRh().getId();

        // Delegate the update logic to the RhService
        rhService.updateProfile(rhId, rhProfileDto);

        return ResponseEntity.ok("Profile edited successfully");
    }


    // Delete Account Method
    @RequestMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();

        // Delete the authenticated user's account
        rhRepository.delete(rh);

        return ResponseEntity.ok("Account deleted successfully");
    }



}
