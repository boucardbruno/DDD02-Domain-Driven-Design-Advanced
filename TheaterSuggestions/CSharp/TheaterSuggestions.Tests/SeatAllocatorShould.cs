using ExternalDependencies.AuditoriumLayoutRepository;
using ExternalDependencies.ReservationsProvider;
using NFluent;
using NUnit.Framework;

namespace SeatsSuggestions.Tests;

[TestFixture]
public class SeatAllocatorShould
{
    /*
     *  Business Rule - Only Suggest available seats
     */

    [Test]
    public void Suggest_one_seat_when_Auditorium_contains_one_available_seat_only()
    {
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
        const string showId = "1";
        const int partyRequested = 1;

        var auditoriumLayoutAdapter =
            new AuditoriumSeatingAdapter(new AuditoriumLayoutRepository(), new ReservationsProvider());
        // remove the below lines and happy coding.
        var auditoriumSeating = auditoriumLayoutAdapter.GetAuditoriumSeating(showId);
        Check.That(auditoriumSeating.Rows).HasSize(2);
    }

    [Test]
    public void Return_SuggestionNotAvailable_when_Auditorium_has_all_its_seats_already_reserved()
    {
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
        const string showId = "5";
        const int partyRequested = 1;

        var auditoriumLayoutAdapter =
            new AuditoriumSeatingAdapter(new AuditoriumLayoutRepository(), new ReservationsProvider());
        // remove the below lines and happy coding.
        var auditoriumSeating = auditoriumLayoutAdapter.GetAuditoriumSeating(showId);
        Check.That(auditoriumSeating.Rows).HasSize(2);
    }
}