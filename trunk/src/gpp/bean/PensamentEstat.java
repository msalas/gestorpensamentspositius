package gpp.bean;

public enum PensamentEstat {
	POSITIU(1), DUBTOS(2), NEGATIU(3);

    private int id;

    private PensamentEstat(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PensamentEstat valueOf(int id) {
        for (PensamentEstat status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status id " + id);
    }
}
