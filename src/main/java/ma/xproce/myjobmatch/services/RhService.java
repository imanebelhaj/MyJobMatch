package ma.xproce.myjobmatch.services;


import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;

import ma.xproce.myjobmatch.dto.RhProfileDto;
import ma.xproce.myjobmatch.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RhService {

    @Autowired
    private RHRepository rhRepository;


//    @Transactional(readOnly = true)
//    public RH getProfileById(Long rhId) {
//        return rhRepository.findById(rhId)
//                .orElseThrow(() -> new RuntimeException("RH not found"));
//    }

    public RhProfileDto getProfileById(Long rhId) {
        RH rh = rhRepository.findById(rhId)
                .orElseThrow(() -> new RuntimeException("RH not found"));

        RhProfileDto rhProfileDto = new RhProfileDto();
        rhProfileDto.setCompanyName(rh.getCompanyName());
        rhProfileDto.setFullName(rh.getFullName());
        rhProfileDto.setLinkedinUrl(rh.getLinkedinUrl());
        rhProfileDto.setDepartment(rh.getDepartment());
        rhProfileDto.setPhone(rh.getPhone());
        rhProfileDto.setCompanyWebsite(rh.getCompanyWebsite());
        rhProfileDto.setProfilePictureUrl(rh.getProfilePictureUrl());
        rhProfileDto.setProfileComplete(rh.isProfileComplete());

        return rhProfileDto;
    }

    public void updateProfile(Long rhId, RhProfileDto rhProfileDto) {
        // Fetch the RH entity by ID
        RH rh = rhRepository.findById(rhId)
                .orElseThrow(() -> new ResourceNotFoundException("RH not found with ID: " + rhId));

        // Update the profile fields only if they are provided in the request body
        if (rhProfileDto.getCompanyName() != null) {
            rh.setCompanyName(rhProfileDto.getCompanyName());
        }
        if (rhProfileDto.getFullName() != null) {
            rh.setFullName(rhProfileDto.getFullName());
        }
        if (rhProfileDto.getLinkedinUrl() != null) {
            rh.setLinkedinUrl(rhProfileDto.getLinkedinUrl());
        }
        if (rhProfileDto.getDepartment() != null) {
            rh.setDepartment(rhProfileDto.getDepartment());
        }
        if (rhProfileDto.getPhone() != null) {
            rh.setPhone(rhProfileDto.getPhone());
        }
        if (rhProfileDto.getCompanyWebsite() != null) {
            rh.setCompanyWebsite(rhProfileDto.getCompanyWebsite());
        }
        if (rhProfileDto.getProfilePictureUrl() != null) {
            rh.setProfilePictureUrl(rhProfileDto.getProfilePictureUrl());
        }

        // Save the updated RH entity
        rhRepository.save(rh);
    }
}
