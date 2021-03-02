package jcarlos.ocaloca;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego {

    private final Tablero tablero;
    private final ControladorJugadores cj;

    // Crea el juego a partir del tablero y la  lista de jugadores
    // Pone a los jugadores en la casilla de salida
    public Juego(Tablero tablero, ControladorJugadores controlador) {
        this.tablero = tablero;
        this.cj = controlador;
        // Coloca a los jugadores en la casilla de salida
        for (Jugador aux : cj.getTodosJugadores()) {
            this.tablero.getCasilla(1).ponerJugador(aux);
        }

    }

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        // Se crea el juego
        String[] nombres = {"J1", "J2", "J3"};
        ControladorJugadores cj = new ControladorJugadores(nombres);
        Tablero tablero = new Tablero();
        Juego juego = new Juego(tablero, cj);

        // Imprime el estado del tablero inicialmente
        Vista.mostrarTablero(juego.getTablero());

        boolean salir = true;
        String boton;

        ArrayList<String> jugadoresPozo = new ArrayList<>();

        do {
            for (Jugador aux : cj.getTodosJugadores()) {

                System.out.println("---------------------------------------------------");

                if (aux.getTurnosSinJugar() > 0) {
                    System.out.println("El jugador " + aux.getNombre() + " no juega está ronda");

                    aux.setTurnosSinJugar(aux.getTurnosSinJugar() - 1);

                } else {
                    System.out.println("Pulsa la tecla b para tirar el dado");
                    boton = teclado.nextLine();

                    do {

                        if (boton.equalsIgnoreCase("b")) {

                            tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                            aux.tirarDado();

                            aux.mover(aux.getTirada());

                            tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                            Vista.informarTirada(aux);
                            Vista.informarProgreso(aux);
                            Vista.mostrarTablero(tablero);

                            switch (aux.getCasillaActual()) {
                                case 5:
                                case 14:
                                case 23:
                                case 32:
                                case 41:
                                case 50:
                                case 59:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla Oca 4");
                                    System.out.println("Por tanto se mueve a la casilla " + (aux.getCasillaActual() + 4));
                                    System.out.println("Tiene otra tirada más");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(4);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    if (aux.getCasillaActual() == 59) {
                                        salir = false;

                                        aux.setTiraOtraVez(false);
                                    }

                                    aux.setTiraOtraVez(true);

                                    break;

                                case 9:
                                case 18:
                                case 27:
                                case 36:
                                case 45:
                                case 54:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla Oca 5");
                                    System.out.println("Por tanto se mueve a la casilla " + (aux.getCasillaActual() + 5));
                                    System.out.println("Tiene otra tirada más");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(5);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(true);

                                    break;

                                case 6:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla puente");
                                    System.out.println("Por tanto se mueve a la casilla " + (aux.getCasillaActual() + 13));
                                    System.out.println("Has perdido un turno");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(13);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTurnosSinJugar(1);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 12:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla puente");
                                    System.out.println("Por tanto se mueve a la casilla " + (aux.getCasillaActual() + 7));
                                    System.out.println("Has perdido un turno");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(7);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTurnosSinJugar(1);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 19:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla posada");
                                    System.out.println("Has perdido un turno");

                                    aux.setTurnosSinJugar(1);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 31:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla pozo");
                                    System.out.println("Hasta que no venga otro jugador no puedes jugar");

                                    aux.setTurnosSinJugar(100);

                                    jugadoresPozo.add(aux.getNombre());

                                    if (jugadoresPozo.size() == 2) {
                                        System.out.println("El jugador " + aux.getNombre() + " sale del pozo");
                                        jugadoresPozo.remove(0);

                                    }

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 42:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla laberinto");
                                    System.out.println("Por tanto retrocede a la casilla " + (aux.getCasillaActual() - 12));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(-12);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 26:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla dado 26");
                                    System.out.println("Por tanto avanza a la casilla " + (aux.getCasillaActual() + 26));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(26);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 53:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla dado 53");
                                    System.out.println("Por tanto avanza a la casilla " + (aux.getCasillaActual() - 53));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(53);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 56:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla carcel");
                                    System.out.println("Has perdido dos turnos");

                                    aux.setTurnosSinJugar(2);

                                    aux.setTiraOtraVez(false);

                                    break;

                                case 58:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla calavera");
                                    System.out.println("Por tanto retrocede a la casilla " + ((63 + 10) - 53));
                                    System.out.println("Has perdido un turno");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(-57);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                default:

                                    aux.setTiraOtraVez(false);

                                    break;
                            }
                        }

                    } while (aux.TiraOtraVez());
                }

                if (aux.ganaPartida()) {
                    salir = false;

                    System.out.println("El jugador " + aux.getNombre() + " ha ganado la partida");

                    break;
                }
            }

        } while (salir);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ControladorJugadores getCj() {
        return cj;
    }

}
