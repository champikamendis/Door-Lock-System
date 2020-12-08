package com.project.securedoor.Repository;

import com.project.securedoor.Model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
