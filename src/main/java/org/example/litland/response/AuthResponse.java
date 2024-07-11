package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.shell.BookCartShell;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private List<BookCartShell> cart;
    private String verdict;
    private String message;
}
