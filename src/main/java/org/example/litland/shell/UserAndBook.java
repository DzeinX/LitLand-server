package org.example.litland.shell;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.model.Book;
import org.example.litland.model.User;
import org.example.litland.response.BookCartResponse;

@Data
@AllArgsConstructor
public class UserAndBook {
    private User user;
    private Book book;
    private BookCartResponse bookCartResponse;
}
