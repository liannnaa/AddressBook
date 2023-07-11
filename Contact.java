import java.io.Serializable;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int count = 0;
    private int id;
    private String name;
    private String email;
    private String phone;
    private String notes;

    public Contact(String name, String email, String phone, String notes) {
        this.id = ++count;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.notes = notes;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Contact.count = count;
    }

    public static void resetCount() {
        count = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return id + "\t| " + name + "\t| " + email + "\t| " + phone + "\t| " + notes;
    }
}
