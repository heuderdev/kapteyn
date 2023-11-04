package br.com.sicoob.cards.service;

import br.com.sicoob.cards.dto.request.UserRequestDTO;
import br.com.sicoob.cards.dto.response.UserResponseDTO;
import br.com.sicoob.cards.models.UserModel;
import br.com.sicoob.cards.repositories.IUserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {
    private IUserRepository repository;

    @Override
    public UserResponseDTO findById(Long id) {
        UserModel user = returnUser(id);
        return new UserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return null;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userDTO) {
        return null;
    }


    @Override
    public UserResponseDTO update(UserRequestDTO userDTO, Long id) {
        UserModel user = returnUser(id);
        return new UserResponseDTO(user);
    }

    @Override
    public String delete(Long id) {
        return null;
    }

    private UserModel returnUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("user not exist."));
    }
}
