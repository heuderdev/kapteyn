package br.com.sicoob.cards.repositories;

import br.com.sicoob.cards.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,Long> {
    @Query("SELECT CASE WHEN COUNT(u.email) > 0 THEN true ELSE false END FROM users u WHERE u.email= :email")
    boolean findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u.username) > 0 THEN true ELSE false END FROM users u WHERE u.username= :username")
    boolean findByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u.id) > 0 THEN true ELSE false END FROM users u WHERE u.id= :id")
    boolean checkIfIdExists(@Param("id") Long id);
}
