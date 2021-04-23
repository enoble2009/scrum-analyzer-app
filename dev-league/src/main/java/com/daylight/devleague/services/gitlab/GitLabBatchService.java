package com.daylight.devleague.services.gitlab;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.domain.gitlab.GitLabProject;
import com.daylight.devleague.dto.gitlab.GitLabBatchEntity;
import com.daylight.devleague.dto.gitlab.GitLabCommitDTO;
import com.daylight.devleague.services.gitlab.fetch.GitLabCommitsFetchService;
import com.daylight.devleague.services.gitlab.process.GitLabCommitsProcessService;
import com.daylight.devleague.services.gitlab.store.GitLabCommitsStoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabBatchService {

	private static final int MAX_COMMITS_TO_FETCH = 100;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Value("${gitlab.access.token}")
	private String apiToken;
	
	@Value("${gitlab.host.url}")
	private String hostUrl;

	@Value("${gitlab.branch}")
	private String branch;
	
	@Value("${gitlab.username}")
	private String username;

	@Value("${gitlab.password}")
	private String password;
	
	private final GitLabBatchLogService logService;
	
	private final GitLabCommitsFetchService fetchService;
	private final GitLabCommitsProcessService processService;
	private final GitLabCommitsStoreService storeService;

	private final GitLabProjectService projectService;
	
	public void load() throws Exception {
		GitLabBatchLog log = logService.initialize(GitLabBatchEntity.COMMITS);
		GitLabApi api = new GitLabApi(hostUrl, apiToken);
		CommitsApi commitsApi = api.getCommitsApi();
		
		Date startDate = log.getStartDate() == null? new Date(0l): dateFormat.parse(log.getStartDate().format(formatter));
		Date endDate = dateFormat.parse(log.getRunDate().format(formatter));

		loadProjects(log, commitsApi, startDate, endDate);
		
		api.close();

		logService.save(log.complete());
	}

	private void loadProjects(GitLabBatchLog log, CommitsApi commitsApi, Date startDate, Date endDate)
			throws GitLabApiException, Exception {
		List<Commit> commits;
		List<GitLabProject> projects = projectService.getProjects();
		
		for (GitLabProject project: projects) {
			int projectId = project.getExternalId().intValue();
			if (projectId == 5) continue;
			Pager<Commit> commitsPager = commitsApi.getCommits(projectId, branch, startDate, endDate, MAX_COMMITS_TO_FETCH);
			
			while (!(commits = fetchService.fetch(commitsApi, commitsPager, projectId, log)).isEmpty()) {
				List<GitLabCommitDTO> commitsInfo = processService.process(log, commits);
				log.newBatchStep(maxCommittedDate(commitsInfo));
				storeService.store(commitsInfo, project);
			}
		}
	}

	private LocalDateTime maxCommittedDate(List<GitLabCommitDTO> commitsInfo) {
		return commitsInfo.stream()
				.max((c1, c2) -> c2.getCreatedDate().compareTo(c1.getCreatedDate()))
				.get().getCreatedDate();
	}

}
