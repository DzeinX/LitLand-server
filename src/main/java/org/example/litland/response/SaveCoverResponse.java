package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveCoverResponse {
    private String message;
    private String verdict;
}
