package veterinaria.domain.model;


public class User {
    private final String documentId;
    private String name;
    private int age;
    private Role role;
    private String username;
    private String password;

    public User(String documentId, String name, int age, Role role) {
        this.documentId = documentId;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    

	public User(String documentId, String name, int age, Role role, String username, String password) {
		super();
		this.documentId = documentId;
		this.name = name;
		this.age = age;
		this.role = role;
		this.username = username;
		this.password = password;
	}



	public String getDocumentId() {
        return documentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void assignCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasRole(Role expectedRole) {
        return this.role.equals(expectedRole);
    }
}
