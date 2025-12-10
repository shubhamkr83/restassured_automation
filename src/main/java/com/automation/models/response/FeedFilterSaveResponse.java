package com.automation.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response POJO for Feed Filter Save API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedFilterSaveResponse {

    private String statusCode;
    private String message;
    private FilterSaveData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilterSaveData {
        private List<String> suitable_for;
        private List<String> productTags;
        private List<String> city;
        private Integer price_min;
        private Integer price_max;
        private String lastSelectedFilter;
    }
}
