import javax.swing.*;
import java.awt.event.*;

public class LoginUI {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Bank Login");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 80, 25);
        frame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(120, 30, 150, 25);
        frame.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 70, 150, 25);
        frame.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 110, 100, 30);
        frame.add(loginBtn);

        JLabel result = new JLabel("");
        result.setBounds(50, 150, 250, 25);
        frame.add(result);

        UserDAO dao = new UserDAO();

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText();
                String pass = new String(passField.getPassword());

                int userId = dao.login(email, pass);

                if (userId != -1) {
                    result.setText("Login Successful!");
                    new DashboardUI(userId);
                } else {
                    result.setText("Invalid Login!");
                }
            }
        });

        frame.setVisible(true);
    }
}