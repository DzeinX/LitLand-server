package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.shell.BookCartShell;

@Data
@AllArgsConstructor
public class BookCartResponse {
    private BookCartShell book;
    private String message;
    private String verdict;
}
