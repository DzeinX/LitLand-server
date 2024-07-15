package org.example.litland.shell;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarkShell {
    private String userId;
    private String bookId;
    private Integer mark;
}
