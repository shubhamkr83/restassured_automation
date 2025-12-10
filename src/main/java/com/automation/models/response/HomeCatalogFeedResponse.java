package com.automation.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response POJO for Home Catalog Feed API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeCatalogFeedResponse {

    private String statusCode;
    private String message;
    private List<CatalogFeedItem> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CatalogFeedItem {
        private String _id;
        private String title;
        private String description;
        private String image;
        private Integer price;
        private String url;
    }
}
