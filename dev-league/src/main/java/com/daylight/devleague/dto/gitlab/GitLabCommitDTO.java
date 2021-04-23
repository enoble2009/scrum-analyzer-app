package com.daylight.devleague.dto.gitlab;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitLabCommitDTO implements Serializable {

	private static final long serialVersionUID = -6681356272118316310L;
	
	private Long id;
	private String externalId;
	private String code;
	private String title;
	private String author;
	private LocalDateTime createdDate;
	private Long createdLines;
	private Long removedLines;
	private Long totalLines;

}
