package com.erich.blog.app.dto.response;

import com.erich.blog.app.dto.ComentarioDto;

import java.util.Map;
import java.util.Set;

public record CommentsWithPaginatedResponse(Set<ComentarioDto> comments, Map<String,Object> pages) {
}
