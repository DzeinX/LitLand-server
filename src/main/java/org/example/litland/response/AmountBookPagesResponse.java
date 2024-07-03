package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmountBookPagesResponse {
    private Integer totalPages;
    private String message;
    private String verdict;
}
