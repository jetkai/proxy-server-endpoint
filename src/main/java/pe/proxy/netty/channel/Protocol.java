package pe.proxy.netty.channel;

/**
 * Protocol
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
public enum Protocol {

    RAW_SOCKET(14),
    HTTP(-1), //TODO
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
