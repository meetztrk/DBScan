package Algoritma;

public class DBScanSonuc {
    private int[] kumeler;
    private int[] kumeYogunlugu;

    public DBScanSonuc(int[] kumeler, int[] kumeYogunlugu){
        this.kumeler = kumeler;
        this.kumeYogunlugu = kumeYogunlugu;
    }

    public int getKumeler(int index) {
        return kumeler[index];
    }

    public int getKumeYogunlugu(int index){
        return kumeYogunlugu[index];
    }

    public int kumeSayisi(){
        return kumeYogunlugu.length;
    }
    
    public int veriSayisi() {
    	return kumeler.length;
    }
}
