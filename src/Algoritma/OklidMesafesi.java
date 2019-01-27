package Algoritma;


public class OklidMesafesi implements MesafeFonksiyonu{
	@Override
	public double hesapla(int[] v1, int[] v2) {
		double toplam = 0.0;
		for(int i = 0; i < v1.length; i++) {
			toplam += Math.pow((v1[i] - v2[i]), 2);
		}
		return Math.sqrt(toplam);
	}
}
