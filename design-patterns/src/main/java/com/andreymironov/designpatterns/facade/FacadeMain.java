package com.andreymironov.designpatterns.facade;

public class FacadeMain {
    public static class BankAccountService {
        public void addMoney(String account, double amount) {
            System.out.println("Subtracting money from account...");
        }

        public void subtractMoney(String account, double amount) {
            System.out.println("Subtracting money from account...");
        }
    }

    public static class TransactionService {
        public void start() {
            System.out.println("Starting transaction...");
        }

        public void end() {
            System.out.println("Ending transaction...");
        }
    }

    public static class BankAccountsExchangeFacade {
        private final BankAccountService bankAccountService;
        private final TransactionService transactionService;

        public BankAccountsExchangeFacade(BankAccountService bankAccountService,
                                          TransactionService transactionService) {
            this.bankAccountService = bankAccountService;
            this.transactionService = transactionService;
        }

        public void exchangeMoney(String sourceAccount, String targetAccount, double amount) {
            System.out.println("Exchanging money between accounts...");
            transactionService.start();
            bankAccountService.subtractMoney(sourceAccount, amount);
            bankAccountService.addMoney(targetAccount, amount);
            transactionService.end();
        }
    }

    public static void main(String[] args) {
        BankAccountsExchangeFacade facade = new BankAccountsExchangeFacade(new BankAccountService(),
                new TransactionService());
        facade.exchangeMoney("user1", "user2", 1_000_000);
    }
}
