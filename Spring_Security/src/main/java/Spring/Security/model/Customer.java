package Spring.Security.model;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
}