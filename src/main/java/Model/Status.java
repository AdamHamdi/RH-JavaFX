package Model;

public enum Status {
    ACTIF("Actif"),
    EN_CONGE("En Congé"),
    INACTIF("Inactif");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status fromString(String text) {
        for (Status s : Status.values()) {
            if (s.getStatus().equalsIgnoreCase(text.trim())) {
                return s;
            }
        }
        throw new IllegalArgumentException("Statut invalide. Choisissez parmi 'Actif', 'En Congé', ou 'Inactif'.");
    }
}






