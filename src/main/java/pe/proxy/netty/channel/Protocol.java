package pe.proxy.netty.channel;

/**
 * Protocol
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
public enum Protocol {

    GAME_SERVER(14),
    UNDEFINED(0);

    private final java.lang.Integer id;

    Protocol(int id) {
        this.id = id;
    }

    public static Protocol getById(int id) {
        for(Protocol e : values()) {
            if(e.id.equals(id)) {
                return e;
            }
        }
        return UNDEFINED;
    }

}
