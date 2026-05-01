import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDAO dao = new UserDAO();
        BankService service = new BankService();

        while (true) {
            System.out.println("\n1.Register\n2.Login\n3.Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Password: ");
                String pass = sc.nextLine();

                dao.register(new User(name, email, pass));

            } else if (choice == 2) {
                sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Password: ");
                String pass = sc.nextLine();

                int userId = dao.login(email, pass);

                if (userId != -1) {
                    System.out.println("Login Successful!");

                    while (true) {
                        System.out.println("\n1.Deposit\n2.Withdraw\n3.Balance\n4.History\n5.Logout");
                        int opt = sc.nextInt();

                        if (opt == 1) {
                            System.out.print("Amount: ");
                            service.deposit(userId, sc.nextDouble());

                        } else if (opt == 2) {
                            System.out.print("Amount: ");
                            service.withdraw(userId, sc.nextDouble());

                        } else if (opt == 3) {
                            service.checkBalance(userId);

                        } else if (opt == 4) {
                            service.transactionHistory(userId);

                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid Login!");
                }

            } else {
                break;
            }
        }
    }
}
