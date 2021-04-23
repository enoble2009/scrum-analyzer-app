package com.daylight.devleague.services.gitlab.fetch;

import java.util.ArrayList;
import java.util.List;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Commit;
import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabCommitsFetchService {
	
	public List<Commit> fetch(CommitsApi api, Pager<Commit> commits, int projectId, GitLabBatchLog log) throws Exception {
		List<Commit> res = new ArrayList<>();
		if (!commits.hasNext()) {
			return res; 
		}

		List<Commit> basicCommits = commits.next();
		
		for (Commit commit: basicCommits) {
			res.add(api.getCommit(projectId, commit.getId()));
		}
		
		return res;
	}

}
