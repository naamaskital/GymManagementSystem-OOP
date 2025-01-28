package gym.customers;

import gym.management.Secretary;

// A class representing the balance in a user's account
public class Balance {
    // Field to store the current balance
    protected int balance;
    // Constructor - Initializes the balance with an initial value
    public Balance(int balance) {
        this.balance = balance;
    }
    // Method to get the current balance
    protected int getBalance() {
        return balance;
    }
    // Method to subtract a certain amount from the balance
    // The protected access modifier allows access only within the package or from subclasses
    protected void subBalance(int amount) {
        balance -= amount;
    }
    // Method to add a certain amount to the balance
    // The protected access modifier allows access only within the package or from subclasses
    protected void addBalance(int amount) {
        balance += amount;
    }
    public static class SecretaryActions {
        private Secretary secretary;

        // Constructor to initialize the secretary
        public SecretaryActions(Secretary secretary) {
            this.secretary = secretary;
        }
        public void subBalance(Balance balance,int amount) {
            balance.subBalance(amount);
        }
        public void addBalance(Balance balance,int amount) {
            balance.addBalance(amount);
        }
    }
}

