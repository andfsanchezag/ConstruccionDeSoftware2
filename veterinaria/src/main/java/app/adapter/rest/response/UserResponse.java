package app.adapter.rest.response;

public class UserResponse {
    private long id;
    private String name;
    private long document;
    private int age;
    private String role;
    private String userName;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getDocument() { return document; }
    public void setDocument(long document) { this.document = document; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
