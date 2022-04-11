package logica;

import java.util.ArrayList;
import java.util.Random;

public class Ahorcado {
	private String[] palabrasEspañol = { "ola", "mar", "casa", "auto", "perro", "gato", "oso", "botella", "eclipse",
			"barco", "bote", "taller", "trabajo", "torta", "pupitre", "arboleda", "cartuchera", "cielo", "computadora",
			"programacion", "rio", "puerta", "anillo", "edificio", "pelota", "mate", "poste", "mesa", "moto", "teclado"};
	private String[] palabrasIngles = { "abruptly", "absurd", "abyss", "avenue", "lucky", "length", "matrix", "night",
			"pixel", "programming", "waltz", "airplane", "acommodation","money", "eyes", "car", "bike", "tree", "day",
			"party", "job", "guitar", "lights", "building", "street", "love", "school", "college", "family", "book"};
	private String palabra;
	private char[] palabraSecreta;
	private ArrayList<String> letrasAdivinadas;
	private ArrayList<String> letrasIngresadas;
	private int puntaje;
	private int intentos;
	private String dificultad;

	public Ahorcado() {
		this.palabra = elegirPalabra();
		this.palabraSecreta = convertirPalabraAGuiones(this.palabra);
		this.letrasAdivinadas = new ArrayList<String>();
		this.letrasIngresadas = new ArrayList<String>();
		this.intentos = 6;
		this.puntaje = 0;
		this.dificultad = "Normal";
	}

	/* Para test! */
	public Ahorcado(String palabra) {
		this.palabra = palabra;
		this.palabraSecreta = convertirPalabraAGuiones(palabra);
		this.letrasAdivinadas = new ArrayList<String>();
		this.letrasIngresadas = new ArrayList<String>();
		this.intentos = 6;
		this.puntaje = 0;
		this.dificultad = "Normal";
	}

	public String obtenerPalabraSecreta() {
		StringBuilder palabra = new StringBuilder();
		for (int i = 0; i < this.palabraSecreta.length; i++) {
			palabra.append(palabraSecreta[i]);
		}
		return palabra.toString();
	}
	
	public void jugar(String letra) {
		if (letra.length() > 0) {
			char letraChar = letra.charAt(0);
			jugar(letraChar);
		}
	}

	public void jugar(char letra) {	
		adivinarLetra(letra);
		adivinarPalabra();	
	}

	public String obtenerStringLetrasIngresadas() {
		StringBuilder letrasIngresadas = new StringBuilder();
		for(String letra : this.letrasIngresadas) {
			letrasIngresadas.append(letra).append(" ");	
		}
		return letrasIngresadas.toString();
	}

	public void cambiarPalabra() {
		String viejaPalabra = this.palabra;
		String nuevaPalabra = elegirPalabra();

		while (nuevaPalabra == viejaPalabra)
			nuevaPalabra = elegirPalabra();

		this.palabra = nuevaPalabra;
		this.palabraSecreta = convertirPalabraAGuiones(nuevaPalabra);
	}

	public void cambiarDificultad(String dificultad) {
		if (dificultad.equals("Fácil")) {
			this.dificultad = dificultad;
			this.intentos = 8;
		}
		if (dificultad.equals("Difícil")) {
			this.dificultad = dificultad;
			this.intentos = 4;
		}
	}

	public void reiniciarJuego() {
		cambiarPalabra();
		restablecerPuntaje();
		restablecerIntentos();
		reiniciarLetrasIngresadas();
	}

	public boolean perdioJuego() {
		return this.intentos == 0;
	}

	public boolean ganoJuego() {
		if (this.puntaje == 0) {
			return false;
		}
		return this.puntaje % 20 == 0;
	}
	
	public ArrayList<String> getLetrasIngresadas() {
		return letrasIngresadas;
	}

	public ArrayList<String> getLetrasAdivinadas() {
		return letrasAdivinadas;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIdiomaIngles() {
		this.palabrasEspañol = this.palabrasIngles;
		cambiarPalabra();
	}

	private String elegirPalabra() {
		Random random = new Random();
		int elem = random.nextInt(this.palabrasEspañol.length);
		return this.palabrasEspañol[elem];
	}
	
	private void adivinarLetra(char letra) {
		letra = Character.toLowerCase(letra);
		if (adivinoLetra(letra)) {
			cambiarEstadoPalabra(letra);
			sumarPuntaje(letra);
			agregarLetraAdivinada(letra);
		} else {
			quitarIntentos();
		}
		agregarLetraIngresada(letra);
	}

	private void adivinarPalabra() {
		if (adivinoPalabra()) {
			cambiarPalabra();
			restablecerIntentos();
			reiniciarLetrasAdivinadas();
			reiniciarLetrasIngresadas();
		}
	}

	private void restablecerIntentos() {
		if (this.dificultad.equals("Fácil")) {
			this.intentos = 8;
		}
		if (this.dificultad.equals("Difícil")) {
			this.intentos = 4;
		}
		if (this.dificultad.equals("Normal")) {
			this.intentos = 6;
		}
	}

	private char[] convertirPalabraAGuiones(String palabra) {
		this.palabraSecreta = new char[palabra.length()];

		for (int i = 0; i < palabra.length(); i++) {
			this.palabraSecreta[i] = '-';
		}
		return this.palabraSecreta;
	}

	private void cambiarEstadoPalabra(char letra) {
		for (int i = 0; i < this.palabraSecreta.length; i++) {
			if (letra == this.palabra.charAt(i)) {
				this.palabraSecreta[i] = letra;
			}
		}
	}
	
	private void agregarLetraIngresada(char letra) {
		if(!esLetraIngresada(letra)) {
			this.letrasIngresadas.add(""+letra);
		}
	}
	
	private void reiniciarLetrasIngresadas() {
		this.letrasIngresadas.clear();	
	}
	
	private void sumarPuntaje(char letra) {
		if (!esLetraAdivinada(letra)) {
			this.puntaje++;
		}
	}

	private void agregarLetraAdivinada(char letra) {
		if (!esLetraAdivinada(letra)) {
			this.letrasAdivinadas.add("" + letra);
		}
	}

	private void quitarIntentos() {
		if (this.intentos > 0) {
			this.intentos--;
		}
	}
	
	private void restablecerPuntaje() {
		this.puntaje = 0;
	}
	
	private void reiniciarLetrasAdivinadas() {
		this.letrasAdivinadas.clear();
	}
	
	private boolean esLetraIngresada(char letra) {
		return this.letrasIngresadas.contains("" + letra);
	}
	
	private boolean adivinoLetra(char letra) {
		return this.palabra.contains("" + letra);
	}

	private boolean esLetraAdivinada(char letra) {
		return this.letrasAdivinadas.contains("" + letra);
	}

	private boolean adivinoPalabra() {
		return this.obtenerPalabraSecreta().equals(this.palabra);
	}
}