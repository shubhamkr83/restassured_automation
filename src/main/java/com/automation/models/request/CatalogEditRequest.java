package com.automation.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request POJO for Catalog Edit API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogEditRequest {

    private String title;

    @JsonProperty("priceText")
    private Double priceText;
}
