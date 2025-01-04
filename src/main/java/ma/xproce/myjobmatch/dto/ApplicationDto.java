package ma.xproce.myjobmatch.dto;

import ma.xproce.myjobmatch.dao.entities.Application;

import java.util.Date;

public class ApplicationDto {
    private Long candidateId;
    private Long jobId;

    private String status; //(Pending, Accepted, Rejected)
    private Date applicationDate;
    private Date createdAt;
    private Date editedAt;

    public ApplicationDto(Application application) {
        this.candidateId = application.getCandidate().getId();  // Set the candidate's ID
        this.jobId = application.getJob().getId();  // Set the job's ID
        this.status = application.getStatus();
        this.applicationDate = application.getApplicationDate();
        this.createdAt = application.getCreatedAt();
        this.editedAt = application.getEditedAt();
    }


    // Getters and setters

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

}
