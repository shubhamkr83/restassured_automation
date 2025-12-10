package com.automation.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request POJO for Catalog Tagging API.
 * Endpoint: PUT /v1/admin/catalog/{catalog_id}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogTaggingRequest {

    private String title;
    private Double priceText;
    private List<String> productTags;
}
