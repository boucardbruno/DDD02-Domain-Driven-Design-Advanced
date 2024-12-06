package com.octo.technology.SeatsSuggestionsAcceptanceTests;

import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumLayoutRepository;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservationsProvider;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class SeatsAllocatorTest {
    /*
     *  Business Rule - Only Suggest available seats
     */
    @Test
    public void should_suggest_one_seat_when_Auditorium_contains_one_available_seat_only() throws IOException {
        // Example 1 - Happy path
        //
        // * Party 1
        //
        // * Ford Auditorium-1
        //
        //       1   2   3   4   5   6   7   8   9  10
        //  A : (2) (2)  1  (1) (1) (1) (1) (1) (2) (2)
        //  B : (2) (2) (1) (1) (1) (1) (1) (1) (2) (2)
        //
        // => A3
        final String showId = "1";
        final int partyRequested = 1;

        AuditoriumSeatingAdapter auditoriumLayoutAdapter =
                new AuditoriumSeatingAdapter(new AuditoriumLayoutRepository(), new ReservationsProvider());
        // remove the below lines and happy coding.
        AuditoriumSeating auditoriumSeating = auditoriumLayoutAdapter.getAuditoriumSeating(showId);
        assertThat(auditoriumSeating.rows()).hasSize(2);
    }

    @Test
    public void should_return_SeatsNotAvailable_when_Auditorium_has_all_its_seats_already_reserved() throws IOException {
        // Example 2 - Unhappy path
        //
        // * Party 1
        //
        // * Madison Auditorium-5
        //      1   2   3   4   5   6   7   8   9  10
        // A : (2) (2) (1) (1) (1) (1) (1) (1) (2) (2)
        // B : (2) (2) (1) (1) (1) (1) (1) (1) (2) (2)
        //
        // => SuggestionNotAvailable
        AuditoriumSeatingAdapter auditoriumLayoutAdapter =
                new AuditoriumSeatingAdapter(new AuditoriumLayoutRepository(), new ReservationsProvider());
        // remove the below lines and happy coding.
        final String showId = "5";
        final int partyRequested = 1;
        AuditoriumSeating auditoriumSeating = auditoriumLayoutAdapter.getAuditoriumSeating(showId);
        assertThat(auditoriumSeating.rows()).hasSize(2);
    }
}
