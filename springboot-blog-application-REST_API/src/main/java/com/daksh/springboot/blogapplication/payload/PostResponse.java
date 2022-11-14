package com.daksh.springboot.blogapplication.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private List<PostDTO> content;
	private int pageNo;
	private int pageSize;
	private long totalElement;
	private int totalPage;
	private boolean last;
}
