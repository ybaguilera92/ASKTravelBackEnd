package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
     Optional<RefreshToken> findByToken(String token);
     RefreshToken createRefreshToken(String userId);
     RefreshToken verifyExpiration(RefreshToken token);
    int deleteByUserId(String userId);
}
