//package ma.xproce.myjobmatch.dto;
//
//import ma.xproce.myjobmatch.dao.entities.Job;
//import ma.xproce.myjobmatch.dto.JobDto;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JobMapper {
//
//    // Convert Job entity to JobDto
//    public static JobDto fromJobToJobDto(Job job) {
//        if (job == null) {
//            return null;
//        }
//
//        JobDto jobDto = new JobDto();
//        jobDto.setId(job.getId());
//        jobDto.setTitle(job.getTitle());
//        jobDto.setCategory(job.getCategory());
//        jobDto.setDescription(job.getDescription());
//        jobDto.setLocation(job.getLocation());
//        jobDto.setApplicationDeadline(job.getApplicationDeadline());
//        jobDto.setPostedAt(job.getPostedAt());
//        jobDto.setMaxApplications(job.getMaxApplications());
//        jobDto.setStatus(job.getStatus());
//        jobDto.setJobType(job.getJobType());
//        jobDto.setSalaryRange(job.getSalaryRange());
//        jobDto.setRequiredEducation(job.getRequiredEducation());
//        jobDto.setRequiredExperience(job.getRequiredExperience());
//        jobDto.setJobLevel(job.getJobLevel());
//        jobDto.setRequiredSkills(job.getRequiredSkills());
//        jobDto.setRhName(job.getRh() != null ? job.getRh().getFullName() : null);
//        jobDto.setCompany(job.getRh() != null ? job.getRh().getCompanyName() : null);
//        return jobDto;
//    }
//
//    // Convert JobDto to Job entity
//    public static Job fromJobDtoToJob(JobDto jobDto) {
//        if (jobDto == null) {
//            return null;
//        }
//
//        Job job = new Job();
//        job.setId(jobDto.getId());
//        job.setTitle(jobDto.getTitle());
//        job.setCategory(jobDto.getCategory());
//        job.setDescription(jobDto.getDescription());
//        job.setLocation(jobDto.getLocation());
//        job.setApplicationDeadline(jobDto.getApplicationDeadline());
//        job.setPostedAt(jobDto.getPostedAt());
//        job.setMaxApplications(jobDto.getMaxApplications());
//        job.setStatus(jobDto.getStatus());
//        job.setJobType(jobDto.getJobType());
//        job.setSalaryRange(jobDto.getSalaryRange());
//        job.setRequiredEducation(jobDto.getRequiredEducation());
//        job.setRequiredExperience(jobDto.getRequiredExperience());
//        job.setJobLevel(jobDto.getJobLevel());
//        job.setRequiredSkills(jobDto.getRequiredSkills());
//        // You may want to map RH (responsible HR) as well if needed, depending on your application logic
//        // job.setRh(...); // Add mapping logic for RH
//        return job;
//    }
//
//    // Convert a list of Job entities to a list of JobDtos
//    public static List<JobDto> fromJobListToJobDtoList(List<Job> jobs) {
//        return jobs.stream()
//                .map(JobMapper::fromJobToJobDto)
//                .collect(Collectors.toList());
//    }
//
//    // Convert a list of JobDtos to a list of Job entities
//    public static List<Job> fromJobDtoListToJobList(List<JobDto> jobDtos) {
//        return jobDtos.stream()
//                .map(JobMapper::fromJobDtoToJob)
//                .collect(Collectors.toList());
//    }
//}
//
