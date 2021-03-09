package jcarlos.ocaloca;

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

        // Este bucle do while controla que el juego no pare hasta que algún jugador llegue a la casilla
        // 63
        do {
            // El for gestiona que jugador le toca jugar la ronda
            for (Jugador aux : cj.getTodosJugadores()) {

                System.out.println("---------------------------------------------------");

                // Si el jugador tiene turnos sin jugar entra en la primera condición del if
                // donde se indica el nombre del jugador que no juega esa ronda y luego
                // al total de turnosSinJugar se le restará uno 
                if (aux.getTurnosSinJugar() > 0) {
                    System.out.println("El jugador " + aux.getNombre() + " no juega está ronda");

                    aux.setTurnosSinJugar(aux.getTurnosSinJugar() - 1);

                    // Si el jugador ha caido en la casilla 31 (casilla pozo) tendrá que sacar un 6 en el dado para poder salir del pozo
                    if (aux.getCasillaActual() == 31) {
                        aux.tirarDado();

                        Vista.informarTirada(aux);

                        // Si consigue sacar un 6 muestra un mensaje de que el jugador ha salido del pozo
                        if (aux.getTirada() == 6) {
                            System.out.println("El jugador " + aux.getNombre() + " ha salido del pozo");

                            aux.setTurnosSinJugar(0); // Los turnos se ponen a 0 para que pueda tirar en la siguiente ronda

                            // Quitamos al jugador de la casilla pozo y se moverá dependiendo de lo que saque en la tirada
                            tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                            aux.mover(aux.getTirada());

                            tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                            Vista.informarProgreso(aux);
                            Vista.mostrarTablero(tablero);

                        } else {
                            // Si el jugador no ha sacado un 6 se mostrará este mensaje
                            System.out.println("El jugador " + aux.getNombre() + " no ha salido del pozo");
                        }
                    }

                    // Si el jugador no tiene turnos sin jugar entra en el else
                } else {
                    // El jugador debe pulsar la tecla b para lanzar el dado
                    System.out.println("Pulsa la tecla b para tirar el dado");
                    boton = teclado.nextLine();

                    // Este buble do while se repetirá mientras el jugador tenga otra tirada más
                    do {

                        if (boton.equalsIgnoreCase("b")) {

                            // Primero quitamos el jugador de la casilla 1 
                            tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                            // El jugador tira el dado
                            aux.tirarDado();

                            // Se moverá dependiendo del valor que ha sacado en el dado
                            aux.mover(aux.getTirada());

                            // Y le colocamos en la casilla que le corresponde
                            tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                            Vista.informarTirada(aux);
                            Vista.informarProgreso(aux);
                            Vista.mostrarTablero(tablero);

                            // En el switch dado en la casilla que se encuentre el jugador entrará en el
                            // case que le corresponde
                            switch (aux.getCasillaActual()) {
                                // Corresponde con las casillas Oca 4
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

                                    // Si el jugador cae en la casilla 59 se mueve a la casilla 63, por tanto,
                                    // dicho jugador ha ganado la partida
                                    if (aux.getCasillaActual() == 59) {
                                        salir = false;

                                        aux.setTiraOtraVez(false);
                                    } else {
                                        // Tiene otra tirada más
                                        aux.setTiraOtraVez(true);
                                    }

                                    break;

                                // Corresponde con las casillas Oca 5
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

                                // Corresponde con la casills Puente
                                case 6:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla puente");
                                    System.out.println("Por tanto se mueve a la casilla " + (aux.getCasillaActual() + 13));
                                    System.out.println("Has perdido un turno");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(13);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTurnosSinJugar(1); // El jugador pierde un turno

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Puente
                                case 12:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla puente");
                                    System.out.println("Por tanto se mueve a la casilla " + (aux.getCasillaActual() + 7));
                                    System.out.println("Has perdido un turno");

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(7);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTurnosSinJugar(1); // El jugador pierde un turno

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Posada
                                case 19:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla posada");
                                    System.out.println("Has perdido un turno");

                                    aux.setTurnosSinJugar(1); // El jugador pierde un turno

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Pozo
                                case 31:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla pozo");
                                    System.out.println("Hasta que no saques un 6 no puedes jugar");

                                    aux.setTurnosSinJugar(100);

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Laberinto
                                case 42:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla laberinto");
                                    System.out.println("Por tanto retrocede a la casilla " + (aux.getCasillaActual() - 12));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(-12); // El jugador retrocede a la casilla 30

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Dado 26
                                case 26:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla dado 26");
                                    System.out.println("Por tanto avanza a la casilla " + (aux.getCasillaActual() + 26));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(26);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Dado 53
                                case 53:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla dado 53");
                                    System.out.println("Por tanto avanza a la casilla " + ((63 + 10) - 53));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(53);

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Carcel
                                case 56:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla carcel");
                                    System.out.println("Has perdido dos turnos");

                                    aux.setTurnosSinJugar(2); // Pierde 2 turnos

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con la casilla Calavera
                                case 58:

                                    System.out.println("El jugador " + aux.getNombre() + " ha llegado a la casilla calavera");
                                    System.out.println("Por tanto retrocede a la casilla " + (aux.getCasillaActual() - 57));

                                    tablero.getCasilla(aux.getCasillaActual()).quitarJugador(aux);

                                    aux.mover(-57); // El jugador vuelve a la casilla 1

                                    tablero.getCasilla(aux.getCasillaActual()).ponerJugador(aux);

                                    Vista.mostrarTablero(tablero);

                                    aux.setTiraOtraVez(false);

                                    break;

                                // Corresponde con las casillas normales
                                default:

                                    aux.setTiraOtraVez(false);

                                    break;
                            }
                        }

                    } while (aux.TiraOtraVez());
                }

                // Cuando un jugador llega a la casilla 63 entra en este if y la partida termina
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
