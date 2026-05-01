import java.sql.*;
import java.util.*;
public class BankService {
    TransactionDAO tdao = new TransactionDAO();

    public void deposit(int userId, double amount) {
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE users SET balance = balance + ? WHERE id=?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();
            tdao.addTransaction(userId, "DEPOSIT", amount);

            addTransaction(userId, "Deposit", amount);

            System.out.println("Amount Deposited!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void withdraw(int userId, double amount) {
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement check = con.prepareStatement(
                    "SELECT balance FROM users WHERE id=?"
            );
            check.setInt(1, userId);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getDouble("balance") >= amount) {

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE users SET balance = balance - ? WHERE id=?"
                );
                ps.setDouble(1, amount);
                ps.setInt(2, userId);
                ps.executeUpdate();
                tdao.addTransaction(userId, "WITHDRAW", amount);

                addTransaction(userId, "Withdraw", amount);

                System.out.println("Amount Withdrawn!");
            } else {
                System.out.println("Insufficient Balance!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double checkBalance(int userId) {
        double balance = 0;

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT balance FROM users WHERE id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }



    public List<String[]> transactionHistory(int userId) {

        List<String[]> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT type, amount, date FROM transactions WHERE user_id=?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = {
                        rs.getString("type"),
                        String.valueOf(rs.getDouble("amount")),
                        rs.getString("date")
                };
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private void addTransaction(int userId, String type, double amount) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO transactions(user_id,type,amount) VALUES(?,?,?)"
            );
            ps.setInt(1, userId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}