package ma.xproce.myjobmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.xproce.myjobmatch.dao.entities.Education;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDto {
    private String school;
    private String degree;
    private String field;
    private Date startDate;
    private Date endDate;



    public EducationDto(String school, String degree, String field, Date startDate, Date endDate) {
        this.school = school;
        this.degree = degree;
        this.field = field;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Default constructor
    public EducationDto() {
    }
    public EducationDto(Education education) {
        if (education != null) {
            this.school = education.getSchool();
            this.degree = education.getDegree();
            this.field = education.getField();
            this.startDate = education.getStartDate();
            this.endDate = education.getEndDate();
        }
    }


    // Getters and Setters
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
