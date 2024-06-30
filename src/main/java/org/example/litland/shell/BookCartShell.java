package org.example.litland.shell;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCartShell {
    private String hash;
    private String name;
    private String authors;
    private Integer amount;
    private Float price;
}
