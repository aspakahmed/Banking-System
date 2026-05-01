import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardUI {

    JFrame frame;
    JTextArea output;
    JScrollPane scrollPane;

    public DashboardUI(int userId) {

        frame = new JFrame("Dashboard");
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(120, 20, 150, 30);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(120, 60, 150, 30);
        frame.add(withdrawBtn);

        JButton balanceBtn = new JButton("Check Balance");
        balanceBtn.setBounds(120, 100, 150, 30);
        frame.add(balanceBtn);

        JButton historyBtn = new JButton("Transaction History");
        historyBtn.setBounds(90, 140, 200, 30);
        frame.add(historyBtn);

        output = new JTextArea();
        output.setBounds(50, 180, 300, 40);
        frame.add(output);

        BankService service = new BankService();

        // 💰 Deposit
        depositBtn.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog("Enter amount:");
                double amt = Double.parseDouble(input);

                if (amt <= 0) {
                    output.setText("Enter valid amount!");
                    return;
                }

                service.deposit(userId, amt);
                output.setText("Deposited: ₹" + amt);

            } catch (Exception ex) {
                output.setText("Invalid input!");
            }
        });

        // 💸 Withdraw
        withdrawBtn.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog("Enter amount:");
                double amt = Double.parseDouble(input);

                if (amt <= 0) {
                    output.setText("Enter valid amount!");
                    return;
                }

                service.withdraw(userId, amt);
                output.setText("Withdraw: ₹" + amt);

            } catch (Exception ex) {
                output.setText("Error in withdraw!");
            }
        });

        // 📊 Balance
        balanceBtn.addActionListener(e -> {
            double bal = service.checkBalance(userId);
            output.setText("Current Balance: ₹" + bal);
        });

        // 📜 Transaction History (TABLE)
        historyBtn.addActionListener(e -> {

            List<String[]> data = service.transactionHistory(userId);

            String[] columns = {"Type", "Amount", "Date"};

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (String[] row : data) {
                model.addRow(row);
            }

            JTable table = new JTable(model);

            // Remove old table if exists
            if (scrollPane != null) {
                frame.remove(scrollPane);
            }

            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(20, 230, 350, 120);

            frame.add(scrollPane);
            frame.revalidate();
            frame.repaint();
        });

        frame.setVisible(true);
    }
}