package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.shell.BookShell;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedBooksResponse {
    private List<BookShell> books;
    private String message;
    private String verdict;
}
