package com.daylight.devleague.services.gitlab.process;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.gitlab4j.api.models.Commit;
import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.dto.gitlab.GitLabCommitDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabCommitsProcessService {
	
	Pattern codePattern = Pattern.compile(".*((PG|PS)-\\d*).*");
	
	public List<GitLabCommitDTO> process(GitLabBatchLog log, List<Commit> commits) {
		List<GitLabCommitDTO> list = (List<GitLabCommitDTO>) commits.stream()
				.map(commit -> {
					Matcher matcher = codePattern.matcher(commit.getTitle());
					String code = matcher.matches()? matcher.group(1): null;

					return GitLabCommitDTO.builder()
						.externalId(commit.getId())
						.code(code)
						.title(commit.getTitle())
						.author(commit.getAuthorEmail())
						.createdLines(commit.getStats().getAdditions().longValue())
						.removedLines(commit.getStats().getDeletions().longValue())
						.totalLines(commit.getStats().getTotal().longValue())
						.createdDate(convertToLocalDateTimeViaMilisecond(commit.getCommittedDate()))
						.build();
				})
				.collect(Collectors.toList());
		
		return list;
	}

	public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
	    return Instant.ofEpochMilli(dateToConvert.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}

}
