package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    @DisplayName("Debe confirmar una reserva en estado PENDIENTE y cambiar estado de habitación")
    void debeConfirmarReservaPendiente() {
        // Arrange
        Cliente cliente = new Cliente("Carlos Gómez", "carlos@correo.com");

        // Instanciamos la subclase concreta
        Habitacion habitacion = new HabitacionEstandar("101", 2, 100.0, 1);

        RangoFechas periodo = new RangoFechas(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(4));
        Reserva reserva = new Reserva(cliente, habitacion, periodo);

        // Act
        reserva.confirmar();

        // Assert
        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());
    }

    @Test
    @DisplayName("Debe cancelar una reserva y liberar la habitación a estado DISPONIBLE")
    void debeCancelarReservaYLiberarHabitacion() {
        // Arrange
        Cliente cliente = new Cliente("Beatriz Morales", "beatriz@correo.com");
        Habitacion habitacion = new HabitacionEstandar("102", 2, 120.0, 1);
        RangoFechas periodo = new RangoFechas(
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(5));
        Reserva reserva = new Reserva(cliente, habitacion, periodo);
        reserva.confirmar(); // La confirmamos previamente para que la habitación esté OCUPADA

        // Act
        reserva.cancelar();

        // Assert
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado());
    }

    @Test
    @DisplayName("Debe calcular el costo total correctamente según el precio por noche y los días de estadía")
    void debeCalcularCostoTotalCorrectamenteAlCrearReserva() {
        // Arrange
        Cliente cliente = new Cliente("Lucía Méndez", "lucia@correo.com");
        Habitacion habitacion = new HabitacionEstandar("103", 2, 150.0, 1);
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusDays(3); // 3 días de estadía
        RangoFechas periodo = new RangoFechas(inicio, fin);

        // Act
        Reserva reserva = new Reserva(cliente, habitacion, periodo);

        // Assert
        // 150.0 * 3 días = 450.0
        assertEquals(450.0, reserva.getCostoTotal(), 0.001);
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
    }
}