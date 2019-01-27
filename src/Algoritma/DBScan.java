package Algoritma;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DBScan{
	private ArrayList<int[]> veriSeti;
	private MesafeFonksiyonu mesafe;
	
	public DBScan(ArrayList<int[]> veriSeti, MesafeFonksiyonu mesafe) {
		this.mesafe = mesafe;
		this.veriSeti = veriSeti;
	}
	
	public DBScanSonuc calistir(double epsilon, int minPoints) {
		int veriBoyutu = veriSeti.size();
		int[] kumeler = new int[veriBoyutu];
		Queue<Integer> kumeCekirdegi = new LinkedList<Integer>();
		Queue<Integer> kumeAdayi = new LinkedList<>();
		int kumeSayisi = 0;
		boolean kumeMi;
		
		for(int i = 0; i < veriBoyutu; i++) {
			if(kumeler[i] != 0) continue;
			kumeCekirdegi.add(i);
			kumeMi = false;
			do {
				for(int j = 0; j < veriBoyutu; j++) {
					if((kumeler[j] == 0  || (kumeMi && kumeler[j] == kumeSayisi) ) && mesafe.hesapla(veriSeti.get(kumeCekirdegi.peek()), veriSeti.get(j))<=epsilon) {
						kumeAdayi.add(j);
					}
				}
				if(kumeAdayi.size() >= minPoints) {
					if(!kumeMi) {
						kumeMi = true;
						kumeSayisi++;
					}
					while(kumeAdayi.size() > 0) {
						if(kumeler[kumeAdayi.peek()]!=kumeSayisi) {
							kumeler[kumeAdayi.peek()] = kumeSayisi;
							kumeCekirdegi.add(kumeAdayi.poll());
						}else {
							kumeAdayi.poll();
						}
					}
				}
				kumeAdayi.clear();
				kumeCekirdegi.poll();
			}while(kumeCekirdegi.size() > 0);
			
		}
		
		int[] kumeYogunlugu = new int[kumeSayisi + 1];
		for(int i = 0; i < veriBoyutu; i++) {
			kumeYogunlugu[kumeler[i]]++;
		}
		
		return new DBScanSonuc(kumeler, kumeYogunlugu);
		
	}
}
