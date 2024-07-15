package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveMarkResponse {
    private String message;
    private String verdict;
    private Float bookRating;
    private Integer userMark;
    private Long amountVoices;
}
