package DosyaDuzenleyici;

import java.io.PrintWriter;

import Algoritma.DBScanSonuc;

public final class SonucYazdir {
	
	public static void yazdir(DBScanSonuc sonuc) {
		try {
			PrintWriter writer = new PrintWriter("sonuc.txt");
			
			int veriBoyutu = sonuc.veriSayisi();
			
			for (int i = 0; i < veriBoyutu; i++) {
				writer.write("Kayit " + i + ":\t\t" + "Kume " + sonuc.getKumeler(i) + "\n");
			}
			writer.write("\n");
			for(int i = 1; i < sonuc.kumeSayisi(); i++) {
				writer.write("Kume " + i + ":\t\t" + sonuc.getKumeYogunlugu(i) + " kayit\n");
			}
			writer.write("Kumeye atanamayan (sapan deger): " + sonuc.getKumeYogunlugu(0) + " kayit" );

			writer.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
