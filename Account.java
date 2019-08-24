public class Account {
    private String UserName;
    private Password Key;
    private int[] ccdone;
    
    public Account(String un,char[] p,int length) {
        setUserName(un);
        setkey(new Password(p));
        ccdone=new int[length];
    }
    public Account(String un,int[][]p,int length) {
        setUserName(un);
        setkey(new Password(p));
        ccdone=new int[length];
    }

    public void setCcDone(int[] done) {
       ccdone=done;
    }
    public void ChapterDone(int courseind) {
        ccdone[courseind]++;
    }
    public int[] getCcDone() {
        return ccdone;
    }
    public void setUserName(String un) {
        this.UserName=un;
    }
    public void setkey(Password k) {
        this.Key=k;
    }
    public Password getPassword() {
        return Key;
    }
    public String getUserName() {
        return UserName;
    }
}