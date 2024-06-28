package org.example.litland.handler.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveBookError {
    private String message;
    private int errorCode;
}
