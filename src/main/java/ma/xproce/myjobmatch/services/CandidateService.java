package ma.xproce.myjobmatch.services;

import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.Resume;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.ResumeRepository;
import ma.xproce.myjobmatch.dto.CandidateProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private ResumeRepository resumeRepository;

    @Transactional(readOnly = true)
    public CandidateProfileDto getProfileById(Long candidateId) {
        // Fetch the Candidate entity by ID
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

        // Convert the Candidate entity to a CandidateProfileDto (you can customize this conversion)
        CandidateProfileDto profileDto = new CandidateProfileDto();
        profileDto.setFullName(candidate.getFullName());
        profileDto.setPhone(candidate.getPhone());
        profileDto.setLinkedinUrl(candidate.getLinkedinUrl());
        profileDto.setWebsite(candidate.getWebsite());
        profileDto.setLocation(candidate.getLocation());
        profileDto.setCategory(candidate.getCategory());
        profileDto.setJobType(candidate.getJobType());
        profileDto.setResumeUrl(candidate.getResumeUrl());
        profileDto.setCoverLetterUrl(candidate.getCoverLetterUrl());
        profileDto.setStatus(candidate.getStatus());
        profileDto.setProfilePictureUrl(candidate.getProfilePictureUrl());
        profileDto.setProfileComplete(candidate.isProfileComplete());

        // Populate resume-related fields if resume is available
        if (candidate.getResume() != null) {
            profileDto.setProfessionalSummary(candidate.getResume().getProfessionalSummary());
            profileDto.setPortfolioUrl(candidate.getResume().getPortfolioUrl());
            profileDto.setEducation(candidate.getResume().getEducation());
            profileDto.setExperience(candidate.getResume().getExperience());
            profileDto.setSoftSkills(candidate.getResume().getSoftSkills());
            profileDto.setHardSkills(candidate.getResume().getHardSkills());
            profileDto.setLanguages(candidate.getResume().getLanguages());
            profileDto.setCertifications(candidate.getResume().getCertifications());
            profileDto.setProjects(candidate.getResume().getProjects());
        }

        return profileDto;
    }
    public Candidate getProfileById2(Long candidateId) {
        // Assuming you are fetching the candidate from the repository by ID
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }



    @Transactional
    public Candidate updateProfile(Long candidateId, CandidateProfileDto profileDto) {
        // Fetch the Candidate entity by ID
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

        // Update the basic details of the Candidate
        candidate.setFullName(profileDto.getFullName());
        candidate.setPhone(profileDto.getPhone());
        candidate.setLinkedinUrl(profileDto.getLinkedinUrl());
        candidate.setWebsite(profileDto.getWebsite());
        candidate.setLocation(profileDto.getLocation());
        candidate.setCategory(profileDto.getCategory());
        candidate.setJobType(profileDto.getJobType());
        candidate.setResumeUrl(profileDto.getResumeUrl());
        candidate.setCoverLetterUrl(profileDto.getCoverLetterUrl());
        candidate.setStatus(profileDto.getStatus());
        candidate.setProfilePictureUrl(profileDto.getProfilePictureUrl());
        candidate.setProfileComplete(profileDto.isProfileComplete());

        // If the candidate has a Resume, update its details as well
        if (candidate.getResume() != null) {
            Resume resume = candidate.getResume();
            resume.setProfessionalSummary(profileDto.getProfessionalSummary());
            resume.setPortfolioUrl(profileDto.getPortfolioUrl());
            resume.setEducation(profileDto.getEducation());
            resume.setExperience(profileDto.getExperience());
            resume.setSoftSkills(profileDto.getSoftSkills());
            resume.setHardSkills(profileDto.getHardSkills());
            resume.setLanguages(profileDto.getLanguages());
            resume.setCertifications(profileDto.getCertifications());
            resume.setProjects(profileDto.getProjects());

            // Save the updated Resume entity
            resumeRepository.save(resume);
        } else {
            // If no Resume exists, create a new one and link it to the Candidate
            Resume newResume = new Resume();
            newResume.setProfessionalSummary(profileDto.getProfessionalSummary());
            newResume.setPortfolioUrl(profileDto.getPortfolioUrl());
            newResume.setEducation(profileDto.getEducation());
            newResume.setExperience(profileDto.getExperience());
            newResume.setSoftSkills(profileDto.getSoftSkills());
            newResume.setHardSkills(profileDto.getHardSkills());
            newResume.setLanguages(profileDto.getLanguages());
            newResume.setCertifications(profileDto.getCertifications());
            newResume.setProjects(profileDto.getProjects());

            // Link the new Resume to the Candidate
            candidate.setResume(newResume);

            // Save both the Candidate and the new Resume entity
            resumeRepository.save(newResume);
        }

        // Save and return the updated Candidate
        return candidateRepository.save(candidate);
    }


}
