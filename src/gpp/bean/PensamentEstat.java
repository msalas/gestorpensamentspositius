package gpp.bean;

public enum PensamentEstat {
	AWAITING_MODERATION(0), PUBLISHED(1), REJECTED(2), REJECTED_EDITABLE(3), UNDER_WORK(
            4);

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
