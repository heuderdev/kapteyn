package br.com.sicoob.cards.service;

import br.com.sicoob.cards.dto.request.UserRequestDTO;
import br.com.sicoob.cards.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO findById(Long id);

    List<UserResponseDTO> findAll();

    UserResponseDTO register(UserRequestDTO userDTO);

    UserResponseDTO update(UserRequestDTO userDTO, Long id);

    String delete(Long id);
}
