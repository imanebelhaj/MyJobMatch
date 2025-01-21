package ma.xproce.myjobmatch.services;

import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.Education;
import ma.xproce.myjobmatch.dao.entities.Experience;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dto.CandidateProfileDto;
import ma.xproce.myjobmatch.dto.EducationDto;
import ma.xproce.myjobmatch.dto.ExperienceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;



    @Transactional(readOnly = true)
    public CandidateProfileDto getProfileById(Long candidateId) {
        // Fetch the Candidate entity by ID
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

        // Create a new CandidateProfileDto object
        CandidateProfileDto profileDto = new CandidateProfileDto();

        // Set basic information from Candidate entity
        profileDto.setFullName(candidate.getFullName());
        profileDto.setPhone(candidate.getPhone());
        profileDto.setLinkedinUrl(candidate.getLinkedinUrl());
        profileDto.setCategory(candidate.getCategory());
        profileDto.setSummary(candidate.getSummary());
        profileDto.setProfileComplete(candidate.isProfileComplete());

        // Set additional fields from the Candidate entity
        profileDto.setSkills(candidate.getSkills());
        profileDto.setLanguages(candidate.getLanguages());
        profileDto.setProfilePicture(candidate.getProfilePicture());
        profileDto.setResumePdf(candidate.getResumePdf());

        // Set Education fields (if present)
        if (candidate.getEducations() != null && !candidate.getEducations().isEmpty()) {
            // Iterate over all education entries
            for (Education education : candidate.getEducations()) {
                // Assuming you have a method to convert Education to EducationDto
                profileDto.getEducations().add(new EducationDto(
                        education.getSchool(),
                        education.getDegree(),
                        education.getField(),
                        education.getStartDate(),
                        education.getEndDate()
                ));
            }
        }
        // Set Experience fields (if present)
        if (candidate.getExperiences() != null && !candidate.getExperiences().isEmpty()) {
            // Iterate over all experience entries
            for (Experience experience : candidate.getExperiences()) {
                // Assuming you have a method to convert Experience to ExperienceDto
                profileDto.getExperiences().add(new ExperienceDto(
                        experience.getTitle(),
                        experience.getCompany(),
                        experience.getStartDate(),
                        experience.getEndDate(),
                        experience.getDescription(),
                        experience.getType()
                ));
            }
        }
        return profileDto;
    }



    @Transactional
    public Candidate updateProfile(Long candidateId, CandidateProfileDto profileDto) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

        // Update basic details
        candidate.setFullName(profileDto.getFullName());
        candidate.setPhone(profileDto.getPhone());
        candidate.setLinkedinUrl(profileDto.getLinkedinUrl());
        candidate.setCategory(profileDto.getCategory());
        candidate.setSummary(profileDto.getSummary());
        candidate.setProfileComplete(profileDto.isProfileComplete());

        // Update skills and languages (could be updated directly as they are simple lists)
        candidate.setSkills(profileDto.getSkills());
        candidate.setLanguages(profileDto.getLanguages());

        // Update profile picture and resume PDF if provided
        if (profileDto.getProfilePicture() != null) {
            candidate.setProfilePicture(profileDto.getProfilePicture());
        }
        if (profileDto.getResumePdf() != null) {
            candidate.setResumePdf(profileDto.getResumePdf());
        }

        // Update education entries
        if (profileDto.getEducations() != null) {
            // Assuming that you want to replace the current education list
            candidate.getEducations().clear(); // Clear the existing ones
            for (EducationDto educationDto : profileDto.getEducations()) {
                Education education = new Education();
                education.setSchool(educationDto.getSchool());
                education.setDegree(educationDto.getDegree());
                education.setField(educationDto.getField());
                education.setStartDate(educationDto.getStartDate());
                education.setEndDate(educationDto.getEndDate());
                candidate.getEducations().add(education);
            }
        }

        // Update experience entries
        if (profileDto.getExperiences() != null) {
            // Assuming that you want to replace the current experience list
            candidate.getExperiences().clear(); // Clear the existing ones
            for (ExperienceDto experienceDto : profileDto.getExperiences()) {
                Experience experience = new Experience();
                experience.setTitle(experienceDto.getTitle());
                experience.setCompany(experienceDto.getCompany());
                experience.setStartDate(experienceDto.getStartDate());
                experience.setEndDate(experienceDto.getEndDate());
                experience.setDescription(experienceDto.getDescription());
                experience.setType(experienceDto.getType());
                candidate.getExperiences().add(experience);
            }
        }

        updateResumeForm(candidate);
        return candidateRepository.save(candidate);
    }

    @Transactional
    public Candidate completeProfile(Long candidateId, CandidateProfileDto profileDto) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

        // Update basic details
        candidate.setFullName(profileDto.getFullName());
        candidate.setPhone(profileDto.getPhone());
        candidate.setLinkedinUrl(profileDto.getLinkedinUrl());
        candidate.setCategory(profileDto.getCategory());
        candidate.setSummary(profileDto.getSummary());
        candidate.setProfileComplete(true); // Mark profile as complete
        candidate.setSkills(profileDto.getSkills());
        candidate.setLanguages(profileDto.getLanguages());


        // Update profile picture and resume PDF if provided
        if (profileDto.getProfilePicture() != null) {
            candidate.setProfilePicture(profileDto.getProfilePicture());
        }
        if (profileDto.getResumePdf() != null) {
            candidate.setResumePdf(profileDto.getResumePdf());
        }

        // Update education entries (similar to updateProfile)
        if (profileDto.getEducations() != null) {
            candidate.getEducations().clear(); // Clear existing educations
            for (EducationDto educationDto : profileDto.getEducations()) {
                Education education = new Education();
                education.setSchool(educationDto.getSchool());
                education.setDegree(educationDto.getDegree());
                education.setField(educationDto.getField());
                education.setStartDate(educationDto.getStartDate());
                education.setEndDate(educationDto.getEndDate());
                candidate.getEducations().add(education);
            }
        }

        // Update experience entries (similar to updateProfile)
        if (profileDto.getExperiences() != null) {
            candidate.getExperiences().clear(); // Clear existing experiences
            for (ExperienceDto experienceDto : profileDto.getExperiences()) {
                Experience experience = new Experience();
                experience.setTitle(experienceDto.getTitle());
                experience.setCompany(experienceDto.getCompany());
                experience.setStartDate(experienceDto.getStartDate());
                experience.setEndDate(experienceDto.getEndDate());
                experience.setDescription(experienceDto.getDescription());
                experience.setType(experienceDto.getType());
                candidate.getExperiences().add(experience);
            }
        }

        updateResumeForm(candidate);
        // ResumeForm will be updated automatically due to @PreUpdate
        return candidateRepository.save(candidate);
    }


    private void updateResumeForm(Candidate candidate) {
        StringBuilder resumeBuilder = new StringBuilder();
        resumeBuilder.append("Category: ").append(candidate.getCategory()).append("\n");
        resumeBuilder.append("Summary: ").append(candidate.getSummary()).append("\n");

        if (candidate.getSkills() != null && !candidate.getSkills().isEmpty()) {
            resumeBuilder.append("Skills: ").append(String.join(", ", candidate.getSkills())).append("\n");
        }

        if (candidate.getLanguages() != null && !candidate.getLanguages().isEmpty()) {
            resumeBuilder.append("Languages: ").append(String.join(", ", candidate.getLanguages())).append("\n");
        }

        if (candidate.getExperiences() != null && !candidate.getExperiences().isEmpty()) {
            resumeBuilder.append("Experience:\n");
            for (Experience exp : candidate.getExperiences()) {
                // Format Experience details
                resumeBuilder.append("Title: ").append(exp.getTitle()).append("\n")
                        .append("Company: ").append(exp.getCompany()).append("\n")
                        .append("Description: ").append(exp.getDescription()).append("\n")
                        .append("Type: ").append(exp.getType()).append("\n\n");
            }
        }

        if (candidate.getEducations() != null && !candidate.getEducations().isEmpty()) {
            resumeBuilder.append("Education:\n");
            for (Education edu : candidate.getEducations()) {
                // Format Education details
                resumeBuilder.append("School: ").append(edu.getSchool()).append("\n")
                        .append("Degree: ").append(edu.getDegree()).append("\n")
                        .append("Field: ").append(edu.getField()).append("\n");
            }
        }

        // Set the generated resumeForm
        candidate.setResumeForm(resumeBuilder.toString());
    }


    @Transactional
    public void deleteAccount(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

        candidateRepository.delete(candidate);
    }







}
