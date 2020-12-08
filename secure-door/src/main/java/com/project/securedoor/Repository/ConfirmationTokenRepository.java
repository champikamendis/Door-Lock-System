package com.project.securedoor.Repository;

import com.project.securedoor.Model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    Object findByConfirmationToken(String confirmationToken);
}
