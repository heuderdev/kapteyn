package br.com.sicoob.cards.repositories;

import br.com.sicoob.cards.models.UsufructuariesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsufructuariesRepository extends JpaRepository<UsufructuariesModel,Long> {
    @Query("SELECT CASE WHEN COUNT(u.email) > 0 THEN true ELSE false END FROM usufructuaries u WHERE u.email= :email")
    boolean findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u.login) > 0 THEN true ELSE false END FROM usufructuaries u WHERE u.login= :login")
    boolean findByUsername(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u.id) > 0 THEN true ELSE false END FROM usufructuaries u WHERE u.id= :id")
    boolean checkIfIdExists(@Param("id") Long id);

    @Query(value = "SELECT U FROM usufructuaries U")
    List<UsufructuariesModel> GetAllUsers();

    @Query(value = "SELECT U FROM usufructuaries U WHERE U.login = :login")
    UsufructuariesModel findByLogin(String login);


}
