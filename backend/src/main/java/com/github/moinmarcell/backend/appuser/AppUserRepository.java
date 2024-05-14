package com.github.moinmarcell.backend.appuser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AppUserRepository extends MongoRepository<AppUser, String> {

    Optional<AppUser> findByGoogleId(String googleId);

    boolean existsByGoogleId(String googleId);
}
