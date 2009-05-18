package gpp.bean;

public enum UsuariGrup {
	REGISTRAT(1), MODERADOR(2);

    private int id;

    private UsuariGrup(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UsuariGrup valueOf(int id) {
        for (UsuariGrup status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status id " + id);
    }
}
