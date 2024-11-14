// controller/BMICalculatorController.java
package controller;

import model.User;
import java.util.ArrayList;
import java.util.List;

public class BMICalculatorController {
    private List<User> users;

    public BMICalculatorController() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void updateUser(int index, User updatedUser) {
        users.set(index, updatedUser);
    }

    public void deleteUser(int index) {
        users.remove(index);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public int getUserCount() {
        return users.size();
    }
}
