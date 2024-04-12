package org.amalgam.server.dataAccessLayer;

import org.amalgam.utils.models.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAL {
    public List<Booking> fetchBookingByPaymentID(int paymentID) {
        List<Booking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();

            String query = "SELECT * FROM bookings WHERE payment_id = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, paymentID);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setRoomID(resultSet.getInt("roomID"));
                booking.setSessionID(resultSet.getInt("scheduleID"));
                booking.setUserID(resultSet.getInt("userID"));
                booking.setPaymentID(resultSet.getInt("paymentID"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle SQLException appropriately
            }
        }

        return bookings;
    }

    public boolean registerNewBooking(int userID, int sessionID, int roomID, int paymentID, String booking_date) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseUtil.getConnection();

            String query = "INSERT INTO bookings (user_id, session_id, room_id, payment_id, booking_date) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, sessionID);
            statement.setInt(3, roomID);
            statement.setInt(4, paymentID);
            statement.setString(5, booking_date);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
