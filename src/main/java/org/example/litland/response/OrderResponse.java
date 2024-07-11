package org.example.litland.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.shell.CartShell;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Date dateGetting;
    private List<CartShell> carts;
    private String verdict;
    private String message;
}
