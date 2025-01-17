package ma.xproce.myjobmatch.dto;

import java.util.List;

public class JobMatchResponse {

    private List<String> matchedJobs;

    public List<String> getMatchedJobs() {
        return matchedJobs;
    }

    public void setMatchedJobs(List<String> matchedJobs) {
        this.matchedJobs = matchedJobs;
    }
}
