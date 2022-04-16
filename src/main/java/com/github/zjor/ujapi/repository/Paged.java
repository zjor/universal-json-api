package com.github.zjor.ujapi.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Pagination starts from 1,
 * if page == 0 it is treated as 1
 *
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paged<T> {

    private List<T> items;
    private PageInfo pagination;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        private int page;
        private int pageSize;

        @JsonProperty("hasPrev")
        @Accessors(fluent = true)
        private boolean hasPrev;

        @JsonProperty("hasNext")
        @Accessors(fluent = true)
        private boolean hasNext;

        private int totalPages;
        private int offset;
        private long total;

        public static PageInfo create(int page, int size, long total) {
            page = (page <= 0) ? 1 : page;
            var offset = (page - 1) * size;
            var totalPages = total / size + ((total % size == 0) ? 0 : 1);
            return PageInfo.builder()
                    .page(page)
                    .pageSize(size)
                    .hasPrev(page > 1)
                    .hasNext(offset + size < total)
                    .totalPages((int) totalPages)
                    .offset(offset)
                    .total(total)
                    .build();
        }
    }

}
