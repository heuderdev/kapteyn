package br.com.sicoob.cards.repositories;

import br.com.sicoob.cards.models.UsufructuariesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UsufructuariesModel,Long> {
    @Query("SELECT CASE WHEN COUNT(u.email) > 0 THEN true ELSE false END FROM usufructuaries u WHERE u.email= :email")
    boolean findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u.username) > 0 THEN true ELSE false END FROM usufructuaries u WHERE u.username= :username")
    boolean findByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u.id) > 0 THEN true ELSE false END FROM usufructuaries u WHERE u.id= :id")
    boolean checkIfIdExists(@Param("id") Long id);

    @Query("SELECT id,username,email,permission,created_at,update_at FROM usufructuaries")
    List<UsufructuariesModel> GetAllUsers();

    @Query("SELECT * FROM usufructuaries WHERE id=:id")
    UsufructuariesModel queryBuscarUsuarioPorId(@Param("id") Long id);
}
