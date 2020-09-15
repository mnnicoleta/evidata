package evidata.core.model;

/**
 * Created by Nicolle on iun. in 2018
 */
public enum Status {

    NEW("Nou"),
    IN_PROGRESS("In progres"),
    INACTIVE("Inactiv"),
    PENDING("In asteptare"),
    REJECTED("Respins"),
    RETURNED("Returnat"),
    DONE("Terminat"),
    FINALIZED("Finalizat");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
