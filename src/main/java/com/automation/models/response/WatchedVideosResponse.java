package com.automation.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response POJO for Watched Videos API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchedVideosResponse {

    private String statusCode;
    private String message;
    private WatchedVideosData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WatchedVideosData {
        private List<WatchedVideoItem> result;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WatchedVideoItem {
        private String videoId;
        private String _id;
        private String phoneNumber;
        private List<String> product;
        private String collection;
        private String market;
        private Integer priceText;
        private String driveLink;
        private Boolean isDeleted;
        private Seller seller;
        private String thubmbnailDriveLink;
        private String createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Seller {
        private String _id;
        private String name;
    }
}
