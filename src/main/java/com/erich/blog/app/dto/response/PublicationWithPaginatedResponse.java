package com.erich.blog.app.dto.response;

import com.erich.blog.app.dto.PublicarDto;

import java.util.List;
import java.util.Map;

public record PublicationWithPaginatedResponse(List<PublicarDto> publications, Map<String, Object> pages) {
}
