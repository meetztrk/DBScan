package DosyaDuzenleyici;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import javax.swing.JOptionPane;

public final class DosyaOku {
	public static LinkedList<int[]> verileriAl(String dosyaYolu, String ayirici) {
		LinkedList<int[]> list = new LinkedList<int[]>();
		try {
			BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaYolu));
			okuyucu.readLine(); //Veri tanimlama satiri.
			String satir = okuyucu.readLine();
			String[] ayrilmisVeriler;
			int[] veri;
			while(satir != null) {
				ayrilmisVeriler = satir.split(ayirici);
				veri = new int[ayrilmisVeriler.length];
				for(int i = 0; i < ayrilmisVeriler.length; i++) {
					veri[i] = Integer.parseInt(ayrilmisVeriler[i]);
				}
				list.add(veri);
				satir = okuyucu.readLine();
			}
			okuyucu.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Uyumsuz dosya turu veya erisim izni yok.");
		}
		
		return list;
	}
	
	public static String[] veriIsimleriniAl(String dosyaYolu, String ayirici) {
		try {
			BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaYolu));
			String[] ayrilmisVeriler = okuyucu.readLine().split(ayirici);
			okuyucu.close();
			return ayrilmisVeriler;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
