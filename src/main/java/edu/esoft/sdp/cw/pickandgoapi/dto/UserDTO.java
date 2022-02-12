package edu.esoft.sdp.cw.pickandgoapi.dto;

import edu.esoft.sdp.cw.pickandgoapi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private String userProfileUrl;
    private String status;

    public UserDTO(Long id, String username, String email, Set<Role> roles, String userProfileUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userProfileUrl = userProfileUrl;
    }
}
