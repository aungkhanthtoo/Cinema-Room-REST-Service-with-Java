class Account {

    private long balance = 0;

    public synchronized boolean withdraw(long amount) {
        final boolean canWithdraw = balance >= amount;
        if (canWithdraw) {
            balance -= amount;
        }
        return canWithdraw;
    }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}