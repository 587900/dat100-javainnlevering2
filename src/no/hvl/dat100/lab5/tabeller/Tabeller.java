package no.hvl.dat100.lab5.tabeller;

import java.util.Arrays;
import java.util.Date;

public class Tabeller {
	
	//til testing
	public static void main(String[] args) {
	
		int[] tabell = {1,5,7,9,12,15,78};
		
		skrivUt(settSammen(tabell, tabell));
		
	}

	// a)
	public static void skrivUt(int[] tabell) {

		//System.out.println(tilStreng(tabell));	// :^)
		
		if (tabell.length == 0) {
			System.out.println("[]");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		for (int i : tabell) sb.append(i).append(",");
		sb.setLength(sb.length()-1);
		sb.append("]");
		
		System.out.println(sb.toString());

	}

	// b)
	public static String tilStreng(int[] tabell) {
		if (tabell.length == 0) return "[]";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		for (int i : tabell) sb.append(i).append(",");
		sb.setLength(sb.length()-1);
		sb.append("]");
		
		return sb.toString();
		
	}

	// c)
	public static int summer(int[] tabell) {
		
		//vanleg
//		int sum = 0;
//		for (int i = 0; i < tabell.length; ++i) sum += tabell[i];
//		return sum;
		
		//utvidet
		int sum = 0;
		for (int i : tabell) sum += i;
		return sum;
		
		//while
//		int sum = 0;
//		int i = 0;
//		while(i < tabell.length) sum += tabell[i++];
//		return sum;
	}

	// d)
	public static boolean finnesTall(int[] tabell, int tall) {
		
		for (int i : tabell) {
			if (i == tall) return true;
		}
		return false;
		
	}

	// e)
	public static int posisjonTall(int[] tabell, int tall) {
		
		for (int i = 0; i < tabell.length; ++i) {
			if (tabell[i] == tall) return i;
		}
		return -1;

	}

	// f)
	public static int[] reverser(int[] tabell) {
		
		int[] ret = new int[tabell.length];
		
		for (int i = 0, retLast = ret.length-1; i < tabell.length; ++i) {
			ret[retLast-i] = tabell[i];
		}
		
		return ret;
		
	}

	// g)
	public static boolean erSortert(int[] tabell) {
		
		for (int i = 1; i < tabell.length; ++i) {
			if (tabell[i] < tabell[i-1]) return false;
		}
		return true;

	}

	// h)
	public static int[] settSammen(int[] tabell1, int[] tabell2) {
		
		int[] ret = new int[tabell1.length + tabell2.length];
		
		int index = 0;
		for (int i : tabell1) ret[index++] = i;
		for (int i : tabell2) ret[index++] = i;

		return ret;
		
	}
}
