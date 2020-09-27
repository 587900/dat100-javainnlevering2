package no.hvl.dat100.lab6.matriser;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Matriser {

	// a)
	public static void skrivUt(int[][] matrise) {
		
		//System.out.println(tilStreng(matrise));	// :^)
		
		for (int[] tabell : matrise) {
			for (int tall : tabell) {
				System.out.print(tall + " ");
			}
			System.out.println();
		}
		
		//i same format som Tabeller.java
//		for (int[] tabell : matrise) {
//			
//			if (tabell.length == 0) {
//				System.out.println("[]");
//				return;
//			}
//			
//			StringBuilder sb = new StringBuilder();
//			
//			sb.append("[");
//			for (int i : tabell) sb.append(i).append(",");
//			sb.setLength(sb.length()-1);
//			sb.append("]");
//			
//			System.out.println(sb.toString());
//			
//		}
		
	}

	// b)
	public static String tilStreng(int[][] matrise) {
		
		StringBuilder sb = new StringBuilder();
		
		for (int[] tabell : matrise) {
			for (int tall : tabell) {
				sb.append(tall).append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
		
	}

	// c)
	public static int[][] skaler(int tall, int[][] matrise) {
		
		int[][] ret = new int[matrise.length][];
		
		for (int i = 0; i < matrise.length; ++i) {
			int[] tabell = matrise[i];
			int[] scaled = new int[tabell.length];
			for (int j = 0; j < tabell.length; ++j) scaled[j] = tabell[j]*tall;
			ret[i] = scaled;
		}
		
		return ret;
	
	}

	// d)
	public static boolean erLik(int[][] a, int[][] b) {
		
		if (a.length != b.length) return false;
		
		for (int i = 0; i < a.length; ++i) {
			
			int[] t1 = a[i];
			int[] t2 = b[i];
			
			if (t1.length != t2.length) return false;
			
			for (int j = 0; j < t1.length; ++j) {
				if (t1[j] != t2[j]) return false;
			}
			
		}

		return true;
	}
	
	// e)
	public static int[][] speile(int[][] matrise) { return speile(matrise, new LeftFlippingMirror()); }	//Bytt ut med andre mirrors nedanfor for å endre måte matrise blir spegla på
	public static int[][] speile(int[][] matrise, Mirror mirror) {
		
		if (matrise.length == 0) return new int[0][0];
		for (int i = 1; i < matrise.length; ++i) { if (matrise[i].length != matrise[i-1].length) throw new IllegalArgumentException("Matrix columns must have consistent lengths!"); }	
		
		
		int[][] ret = new int[matrise.length][matrise[0].length];
		Point limit = new Point(matrise[0].length-1, matrise.length-1);
		
		int tblLen = matrise[0].length;
		for (int y = 0; y < matrise.length; ++y) {
			for (int x = 0; x < tblLen; ++x) {
				Point destination = mirror.mirror(new Point(x,y), limit);
				ret[destination.y][destination.x] = matrise[y][x];
				//alternativt, sjå vekk i frå Mirror og Point, og bruk
				//ret[x][y] = matrise[y][x];
			}
		}

		return ret;
	
	}
	
	abstract static class Mirror { abstract Point mirror(Point p, Point limit); }
	static class HorizontalMirror extends Mirror { Point mirror(Point p, Point limit) { return new Point(limit.x - p.x, p.y); }	}
	static class VerticalMirror extends Mirror { Point mirror(Point p, Point limit) { return new Point(p.x, limit.y - p.y); }	}
	static class DiagonalMirror extends Mirror { Point mirror(Point p, Point limit) { return new Point(limit.x - p.x, limit.y - p.y); }	}
	static class LeftFlippingMirror extends Mirror { 
		Point mirror(Point p, Point limit) { 
			if (limit.x != limit.y) throw new IllegalArgumentException("LeftFlippingMirror can only flip squared matrices!");	//Burde hatt desse i constructor, men burde heller då lage ny class, blir rotete med andre mirrors om ikkje 
			return new Point(p.y, p.x);
		}
	}
	static class RightFlippingMirror extends Mirror { 
		Point mirror(Point p, Point limit) { 
			if (limit.x != limit.y) throw new IllegalArgumentException("RightFlippingMirror can only flip squared matrices!"); 
			return new Point(limit.y - p.y, limit.x - p.x);
		} 
	}
	
	
	//Berre for "gøy"	-> ex: new LeftFlippingMirror() = new MultiMirror(new RightFlippingMirror(), new DiagonalMirror()) 
	//					-> ex: new DiagonalMirror() = new MultiMirror(new HorizontalMirror(), new VerticalMirror())
	//					-> ex: new LeftFlippingMirror() = new MultiMirror(new RightFlippingMirror(), new HorizontalMirror(), new VerticalMirror()) 
	static class MultiMirror extends Mirror {
		List<Mirror> mirrors;
		public MultiMirror(Mirror... mirrorz) {
			mirrors = Arrays.asList(mirrorz);
		}
		Point mirror(Point p, Point limit) {
			Point ret = p;
			for (Mirror mirror : mirrors) ret = mirror.mirror(ret, limit);
			return ret;
		}
	}

	// f)
	//ok, denne var litt tricky
	//skal fungere i følge testar eg har gjort og sjekka med https://www.symbolab.com/solver/matrix-multiply-calculator
	//inspiration 1 (https://www.mathsisfun.com/algebra/matrix-multiplying.html) - Måtte lære meg korleis å multiplisere matriser :) (igjen)
	//inspiration 2 (https://www.javatpoint.com/java-program-to-multiply-two-matrices) - Hjalp litt, men var hovudsakleg for same-dimensional matrices
	//i etterkant fann eg denne - (https://www.programiz.com/java-programming/examples/multiply-matrix-function) - skulle ønske eg såg den tidlegare
	public static int[][] multipliser(int[][] a, int[][] b) {
		
		if (a.length == 0 || a[0].length == 0 || b.length == 0 || b[0].length == 0) throw new IllegalArgumentException("Matrices cannot be 0-dimensional!");
		if (a[0].length != b.length) throw new IllegalArgumentException("The number of columns in matrix#1 must match the number of rows in matrix#2!");
		
		int depth = a.length;
		int breadth = b[0].length;
		int vectorLength = b.length;
		
		int[][] ret = new int[depth][breadth];
		
		for (int y = 0; y < depth; ++y) {
			for (int x = 0; x < breadth; ++x) {
				for (int vectorPos = 0; vectorPos < vectorLength; ++vectorPos) {
					ret[y][x] += a[y][vectorPos]*b[vectorPos][x];
				}
			}
		}
		
		return ret;
		
	}
	
	//til testing av multipliser(matrise) -> har sjekka alle svara nedanfor, og dei stemmer med kalkulatoren nemnt ovanfor
	public static void main(String[] args) {
		
		int[][] a = {{1,1,1},{2,2,2}};
		int[][] b = {{1,1},{2,2},{3,3}};
		int[][] c = {{1,1,1},{2,2,2},{3,3,3}};
		
		int[][] t1 = { {1,2,3}, {4,5,6} };
		int[][] t2 = { {10,11}, {12,13}, {14,15} };
		
		int[][] x1 = { {3,4,2} };
		int[][] x2 = { {13,9,7,15},{8,7,4,6},{6,4,0,3} };
		
		int[][] toSpeil = { {1,2,3}, {4,5,6}, {7,8,9} };

		System.out.println("MIRRORING");
		//skrivUt(x2); System.out.println();
		//skrivUt(speile(x2, new DiagonalMirror())); System.out.println();
		skrivUt(toSpeil); System.out.println();
		skrivUt(speile(toSpeil)); System.out.println();
		
		int[][] y1 = speile(toSpeil, new MultiMirror(new RightFlippingMirror(), new HorizontalMirror(), new VerticalMirror()));
		int[][] y2 = speile(toSpeil, new LeftFlippingMirror());
		System.out.println(erLik(y1,y2)); 
		
		System.out.println();
		
		System.out.println("MATRIX MULTIPLICATION");
		skrivUt(multipliser(a, b)); System.out.println();
		skrivUt(multipliser(b, a)); System.out.println();
		skrivUt(multipliser(c, c)); System.out.println();
		skrivUt(multipliser(t1, t2)); System.out.println();
		skrivUt(multipliser(t2, t1)); System.out.println();
		skrivUt(multipliser(x1,x2)); System.out.println();
		
	}
}
