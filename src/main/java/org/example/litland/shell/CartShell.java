package org.example.litland.shell;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.model.CartStatus;

@Data
@AllArgsConstructor
public class CartShell {
    private String hash;
    private String name;
    private String authors;
    private Integer amount;
    private Float price;
    private String coverName;
    private CartStatus status;
}
