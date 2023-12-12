package net.openwebinars.springboot.restjwt.security;

import io.lettuce.core.api.sync.RedisCommands;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.restjwt.BlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RedisBlacklist {

    @Autowired
    private TokenBlacklist repo;


    public void addToBlacklist(BlackList blackList) {
        repo.save(blackList);
    }


    public boolean isBlacklisted(String token) {
        return repo.existsById(token);
    }

}
