package canva;

import java.util.Objects;

public class AuthContext {
    public final String userId;

    public AuthContext(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthContext)) return false;
        AuthContext that = (AuthContext) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}