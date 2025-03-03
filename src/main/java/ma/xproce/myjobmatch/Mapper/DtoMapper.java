package ma.xproce.myjobmatch.Mapper;

import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dto.CandidateProfileDto;
import ma.xproce.myjobmatch.dto.EducationDto;
import ma.xproce.myjobmatch.dto.ExperienceDto;

public class DtoMapper {

    public static CandidateProfileDto mapToCandidateProfileDto(Candidate candidate) {
        if (candidate == null) {
            return null;
        }
        CandidateProfileDto dto = new CandidateProfileDto();
        dto.setFullName(candidate.getFullName());
        dto.setPhone(candidate.getPhone());
        dto.setEmail(candidate.getEmail());
        dto.setUsername(candidate.getUsername());
        dto.setLinkedinUrl(candidate.getLinkedinUrl());
        dto.setCategory(candidate.getCategory());
        dto.setSummary(candidate.getSummary());
        dto.setSkills(candidate.getSkills());
        dto.setLanguages(candidate.getLanguages());
        dto.setProfileComplete(candidate.isProfileComplete());
        dto.setProfilePicture(candidate.getProfilePicture());
        dto.setResumePdf(candidate.getResumePdf());

        // Convert educations
        if (candidate.getEducations() != null) {
            dto.setEducations(candidate.getEducations().stream().map(EducationDto::new).toList());
        }

        // Convert experiences
        if (candidate.getExperiences() != null) {
            dto.setExperiences(candidate.getExperiences().stream().map(ExperienceDto::new).toList());
        }

        return dto;
    }
}
