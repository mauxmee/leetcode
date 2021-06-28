package canva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DesignServiceImpl implements DesignService {
    AtomicInteger m_id = new AtomicInteger(0);
    // map : context(userId) : map< designId, design>>
    // need to share the design, not copy
    //Map<AuthContext, Map<String, String>> m_cache = new HashMap<>();
    Map<AuthContext, Map<String, StringBuffer>> m_cache = new HashMap<>();
    // map< designId, List< context(userId)>
    // only add when there are shared design
    Map<String, List<AuthContext>> m_sharedMap = new HashMap<>();

    @Override
    public String createDesign(AuthContext ctx, String designContent) {
        if (ctx == null || designContent == null) return null;
        var userDesigns = m_cache.computeIfAbsent(ctx, (key) -> new HashMap<>());
        Integer designId = m_id.incrementAndGet();
        //userDesigns.put(String.valueOf(designId), designContent);
        userDesigns.put(String.valueOf(designId), new StringBuffer(designContent));
        return Integer.toString(designId);
    }

    public StringBuffer getDesignBuffer(AuthContext ctx, String designId) {
        var userDesigns = m_cache.get(ctx);
        if (userDesigns == null) {
            throw new RuntimeException("No such User " + ctx.getUserId());
        }
        return userDesigns.get(designId);
    }

    @Override
    // return is the content of the design
    public String getDesign(AuthContext ctx, String designId) {
        var db = getDesignBuffer(ctx, designId);
        return db == null ? null : db.toString();
    }

    /**
     * Returns a list of design ids that the given context has access to.
     */
    @Override
    public List<String> findDesigns(AuthContext ctx) {
        var userDesigns = m_cache.get(ctx);
        if (userDesigns == null) {
            throw new RuntimeException("No such User " + ctx.getUserId());
        }
        return userDesigns.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Gives a specific user access to the design.
     */
    @Override
    public void shareDesign(AuthContext ctx, String designId, String targetUserId) {
        try {
            StringBuffer design = getDesignBuffer(ctx, designId);
            if (design == null) {
                System.out.println("no such design " + designId + " for the user " + ctx.getUserId() + " ignore");
                return;
            }

            AuthContext targetUser = m_cache.keySet().stream()
                    .filter(k -> k.getUserId().equals(targetUserId))
                    .findFirst().orElse(null);
            if (targetUser == null) {
                System.out.println(targetUserId + " no target user. ignore");
                return;
            }
            // add the shared design to the target user
            m_cache.get(targetUser).put(designId, design);
            // add the shared info to the map
            var sharedList = m_sharedMap.computeIfAbsent(designId, (k) -> new ArrayList<>());
            sharedList.add(ctx);
            sharedList.add(targetUser);
            System.out.println(designId + " the design is shared.");
        } catch (RuntimeException e) {
            System.out.println(ctx.getUserId() + " no such user, ignore");
            return;
        }
    }

    @Override
    public void removeShareDesign(AuthContext ctx, String designId, String targetUserId) {

    }

    @Override
    public void updateDesign(AuthContext ctx, String designId, String newDesign) {
//        List<AuthContext> needToBeUpdated = m_sharedMap.get(designId);
//        if (needToBeUpdated == null) {
//            needToBeUpdated = Collections.singletonList(ctx);
//        }
//        for (AuthContext c : needToBeUpdated) {
//            var userDesigns = m_cache.get(c);
//            if (userDesigns == null) {
//                System.out.println(ctx.getUserId() + " no such user, ignore");
//            }
//            if (userDesigns.containsKey(designId)) {
//                userDesigns.put(designId, newDesign);
//            } else {
//                System.out.println(designId + " designId doesn't exist. ignore");
//            }
//        }
        var userDesigns = m_cache.get(ctx);
        if (userDesigns == null) {
            System.out.println(ctx.getUserId() + " no such user, ignore");
        }
        try {
            var db = getDesignBuffer(ctx, designId);
            if (db == null) {
                System.out.println(designId + " designId doesn't exist. ignore");
                return;
            }
            db.setLength(0);
            db.append(newDesign);
        } catch (Exception ignore) {
        }
    }

    @Override
    public Map<String, StringBuffer> getUserDesigns(AuthContext ctx) {
        return m_cache.get(ctx);
    }
}