package ma.xproce.myjobmatch.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.xproce.myjobmatch.dao.entities.Job;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private String category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")
    private String location;

    @JsonProperty("applicationDeadline")
    private Date applicationDeadline;

    @JsonProperty("postedAt")
    private Date postedAt;

    @JsonProperty("maxApplications")
    private int maxApplications;

    @JsonProperty("status")
    private String status;

    @JsonProperty("jobType")
    private String jobType;

    @JsonProperty("salaryRange")
    private String salaryRange;

    @JsonProperty("requiredEducation")
    private String requiredEducation;

    @JsonProperty("requiredExperience")
    private String requiredExperience;

    @JsonProperty("jobLevel")
    private String jobLevel;

    @JsonProperty("requiredSkills")
    private List<String> requiredSkills;

    @JsonProperty("rhName")
    private String rhName;
//    private Long id;
//    private String title;
//    private String category;
//    private String description;
//    private String location;
//    //private Date createdAt;
//    //private Date editedAt;
//    private Date applicationDeadline;
//    private Date postedAt;
//    private int maxApplications;
//    private String status;
//    private String jobType;
//    private String salaryRange;
//    private String requiredEducation;
//    private String requiredExperience;
//    private String jobLevel;
//    private List<String> requiredSkills;
//    private String rhName;

    public JobDto(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.category = job.getCategory();
        this.description = job.getDescription();
        this.location = job.getLocation();
        //this.createdAt = job.getCreatedAt();
        //this.editedAt = job.getEditedAt();
        this.applicationDeadline = job.getApplicationDeadline();
        this.postedAt = job.getPostedAt();
        this.maxApplications = job.getMaxApplications();
        this.status = job.getStatus();
        this.jobType = job.getJobType();
        this.salaryRange = job.getSalaryRange();
        this.requiredEducation = job.getRequiredEducation();
        this.requiredExperience = job.getRequiredExperience();
        this.jobLevel = job.getJobLevel();
        this.requiredSkills = job.getRequiredSkills();
        this.rhName = job.getRh() != null ? job.getRh().getFullName() : null; // Safely retrieve RH name
    }

}
