package gym.management.Sessions;

public enum SessionType {
    Pilates(60, 30),        // המחיר לשיעור פילאטיס ומכסה של 20 משתתפים
    MachinePilates(80, 10), // המחיר לשיעור מכונה פילאטיס ומכסה של 15 משתתפים
    ThaiBoxing(100, 20),    // המחיר לשיעור טאי-בוקסינג ומכסה של 25 משתתפים
    Ninja(150, 5);         // המחיר לשיעור נינג'ה ומכסה של 10 משתתפים

    private final int price;  // שדה שמייצג את המחיר של השיעור
    private final int maxParticipants;  // שדה שמייצג את מכסת המשתתפים המקסימלית בשיעור

    // קונסטרוקטור שיקבל גם את המחיר וגם את המכסה
    SessionType(int price, int maxParticipants) {
        this.price = price;
        this.maxParticipants = maxParticipants;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }
}