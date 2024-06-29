package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveFileResponse {
    private String message;
    private String verdict;
}
