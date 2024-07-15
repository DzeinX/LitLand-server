package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMarkResponse {
    private Integer mark;
    private Long amountVoices;
    private String message;
    private String verdict;
}
