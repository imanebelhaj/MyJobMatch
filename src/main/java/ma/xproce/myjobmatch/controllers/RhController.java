package ma.xproce.myjobmatch.controllers;


import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.dto.RhProfileDto;
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
    //add job post
    //edit job post
    //publish the job post -> change state from created(can still edit or delete it) to publihed/posted(cant edit it + can still delete)
    //job post automatically change state to finished or idk -> when max candidate or deadline are reached (cant edit or delete)
    //view profile rh
    //profile form after register and first login : for comapny names etc
    //dashboard for each job and list of candidate appkied to it

    @Autowired
    private RHRepository rhRepository;
    @PutMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(@RequestBody RhProfileDto rhProfileDTO, Authentication authentication) {
//         RH rh = (RH) authentication.getPrincipal();
//        if (rh == null) {
//            String username = authentication.getName();  // Fallback to using username from Authentication
//            rh = rhRepository.findByUsername(username)
//                    .orElseThrow(() -> new RuntimeException("RH not found"));
//        }
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



}
