package canva;

import java.util.List;
import java.util.Map;

public interface DesignService {
    /**
     * Creates a design and returns the design id.
     */
    String createDesign(AuthContext ctx, String designContent);

    /**
     * Returns the design content, if the user has access to the design.
     */
    String getDesign(AuthContext ctx, String designId);

    List<String> findDesigns(AuthContext ctx);

    Map<String, StringBuffer> getUserDesigns( AuthContext ctx);
    /**
     * Gives a specific user access to the design.
     */
    void shareDesign(AuthContext ctx, String designId, String targetUserId);

    void removeShareDesign(AuthContext ctx, String designId, String targetUserId);

    // update a specific design for a designId, the design maybe shared among users.
    void updateDesign(AuthContext ctx, String designId, String newDesign);
}