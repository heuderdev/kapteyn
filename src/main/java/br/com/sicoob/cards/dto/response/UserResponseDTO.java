package br.com.sicoob.cards.dto.response;

import br.com.sicoob.cards.models.UserModel;

public class UserResponseDTO {
    private Long id;

    private String username;

    private String email;

    private String password;


    private Integer permission;

    public UserResponseDTO(UserModel userModel) {
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.username = userModel.getUsername();
        this.permission = userModel.getPermission();
    }
}
