package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBookResponse {
    private String message;
    private String verdict;
    private String hash;
}
