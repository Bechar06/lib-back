package com.library.management.system.library_management_system.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * la classe {@code LMSException} est une forme de {@link Throwable} qui permet d'affiche un message en donnant un
 * libelle venant de la base de données
 */

@Getter
@Setter
public class LMSException extends Exception {

    private static final long serialVersionUID = 7718828512143293558L;

    /**
     * le paramétre du message de l'exception
     */
    private final Integer msgParam;

    /**
     * l'attribut qui a mené à la levée de l'exception
     */
    private final String fieldname;

    /**
     * le code d'erreur dans la base
     */
    private final String errorCode;

    /**
     * des information supplémentaires
     */
    private final HashMap data;

    /**
     * Construire une nouvelle exception avec le message de détail spécifié.
     * La cause n'est pas initialisée, le type de message est initialisé par <tt>ERROR</tt>
     *
     * @param message le message de détail. Le message détaillé est sauvegardé pour une récupération ultérieure par la
     * méthode {@link #getMessage()}.
     */
    public LMSException(String message) {
        super(message);
        this.msgParam = null;
        this.fieldname = null;
        this.errorCode = null;
        this.data = null;
    }

    /**
     * Construire une nouvelle exception avec le message de détail spécifié, et la cause.
     * Notez que le message de détail associé à {@code cause} <i> n'est pas</i> intégré automatiquement
     * dans le message de détail de cette exception.
     * La cause n'est pas initialisée, le type de message est initialisé par <tt>ERROR</tt>.
     *
     * @param message le message de détail. Le message détaillé est sauvegardé pour une récupération ultérieure par la
     * méthode {@link #getMessage()}.
     * @param cause la cause (qui est sauvegardée pour une récupération ultérieure par la méthode {@link #getCause()}).
     * (La valeur A <tt>null</tt> est autorisée et indique que la cause est inexistante ou inconnue.)
     */
    public LMSException(String message, Throwable cause) {
        super(message, cause);
        this.msgParam = null;
        this.fieldname = null;
        this.errorCode = null;
        this.data = null;
    }

    /**
     * Construire une nouvelle exception avec le message de détail spécifié, et la cause.
     * Notez que le message de détail associé à {@code cause} <i> n'est pas</i> intégré automatiquement
     * dans le message de détail de cette exception.
     * La cause n'est pas initialisée, le type de message est initialisé par <tt>ERROR</tt>.
     *
     * @param message le message de détail. Le message détaillé est sauvegardé pour une récupération ultérieure par la
     * méthode {@link #getMessage()}.
     * @param fieldname l'attribut qui a mené à la levée de l'exception.
     * @param cause la cause (qui est sauvegardée pour une récupération ultérieure par la méthode {@link #getCause()}).
     * (La valeur A <tt>null</tt> est autorisée et indique que la cause est inexistante ou inconnue.)
     */
    public LMSException(String message, String fieldname, Throwable cause) {
        super(message, cause);
        this.msgParam = null;
        this.fieldname = fieldname;
        this.errorCode = null;
        this.data = null;
    }

    /**
     * Construire une nouvelle exception avec le message de détail spécifié, et l'attribut.
     * Notez que le message de détail associé à la {@code cause} <i> n'est pas</i> intégré automatiquement
     * dans le message de détail de cette exception.
     * le type de message est initialisé par <tt>ERROR</tt>.
     *
     * @param message le message de détail. Le message détaillé est sauvegardé pour une récupération ultérieure par la
     * méthode {@link #getMessage()}.
     * @param fieldname l'attribut qui a mené à la levée de l'exception.
     */
    public LMSException(String message, String fieldname) {
        super(message);
        this.msgParam = null;
        this.fieldname = fieldname;
        this.errorCode = null;
        this.data = null;
    }

    /**
     * Construire une nouvelle exception avec le message de détail spécifié, l'attribut et le type de message.
     * Notez que le message de détail associé à {@code cause} <i> n'est pas</i> intégré automatiquement
     * dans le message de détail de cette exception.
     *
     * @param message le message de détail. Le message détaillé est sauvegardé pour une récupération ultérieure par la
     * méthode {@link #getMessage()}.
     * @param fieldname l'attribut qui a mené à la levée de l'exception.
     * @param messageType le type message de l'exception.
     */

    public LMSException(String message, String fieldname, Enum messageType) {
        super(message);
        this.msgParam = null;
        this.fieldname = fieldname;
        this.errorCode = null;
        this.data = null;
    }

    /**
     * Construire une nouvelle exception avec le message de détail spécifié et l'attribut.
     * Notez que le message de détail associé à {@code cause} <i> n'est pas</i> intégré automatiquement
     * dans le message de détail de cette exception.
     *
     * @param message le message de détail. Le message détaillé est sauvegardé pour une récupération ultérieure par la
     * méthode {@link #getMessage()}.
     * @param fieldname l'attribut qui a mené à la levée de l'exception.
     * @param messageType le type message de l'exception.
     * @param msgParam le paramétre du message de l'exception
     */
    public LMSException(String message, String fieldname, Enum messageType, Integer msgParam) {
        super(message);
        this.fieldname = fieldname;
        this.msgParam = msgParam;
        this.errorCode = null;
        this.data = null;
    }


    public LMSException(String message, String codeMsg, String fieldname, Integer msgParam, Map data) {
        super(message);
        this.fieldname = fieldname;
        this.errorCode = codeMsg;
        this.msgParam = msgParam;
        this.data = (HashMap) data;
    }
}
