package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.shell.BookShell;

@Data
@AllArgsConstructor
public class BookResponse {
    private BookShell book;
    private String message;
    private String verdict;
}
