package com.userInfo;

public class UsersInfo {
    private final String userID;
    private final String firstName;
    private final String lastName;
    private String middleName;
    private String contactNumber;
    private String address;
    private String userName;
    private String password;
    private final String position;
    private byte[] image;



    public UsersInfo(String userID, String firstName, String lastName, String position) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }
    public UsersInfo(String userID, String firstName, String middleName, String lastName, String contactNumber, String address,
                     String userName, String password, String position, byte[] image) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.position = position;
        this.image = image;
    }
    public String getUserID() {
        return userID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPosition() {
        return position;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public String getAddress() {
        return address;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public byte[] getImage() {
        return image;
    }

}
