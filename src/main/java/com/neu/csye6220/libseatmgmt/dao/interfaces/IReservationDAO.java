package com.neu.csye6220.libseatmgmt.dao.interfaces;

import com.neu.csye6220.libseatmgmt.model.Reservation;

import java.util.List;

public interface IReservationDAO {
    void createReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    boolean isSeatAvailable(Long seatId, String startTime, String endTime);
    List<Reservation> getReservationsBySeatId(Long seatId);
    void deleteReservationBySeatId(Long seatId);
    List<Reservation> getReservationsByUserId(Long userId);
    void deleteReservationByUserId(Long userId);
    Reservation getReservationById(Long id);
    void deleteReservation(Long id);
    void deleteAllReservations();

}
