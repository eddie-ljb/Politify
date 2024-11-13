$(document).ready(function () {
    const $parliamentSelect = $('#parliament');
    const $pollSelect = $('#pollId');

    // Funktion zum Abrufen der Umfrage-IDs
    function fetchPolls(parliamentId) {
        console.log("Abrufen der Umfrage-IDs für Parlament ID:", parliamentId);

        $.ajax({
            url: '/data/getUmfageIDsFuerParlament',
            type: 'GET',
            data: { parlament: parliamentId }, // Name muss 'parlament' sein
            success: function (pollIds) {
                console.log("Antwort erhalten:", pollIds);

                // Poll-Optionen zurücksetzen
                $pollSelect.empty();

                if (pollIds && pollIds.length > 0) {
                    // Sortieren der Poll-IDs in absteigender Reihenfolge
                    pollIds.sort((a, b) => b - a);

                    // Umfrage-Optionen einfügen
                    $.each(pollIds, function (index, id) {
                        $pollSelect.append(
                            $('<option></option>').val(id).text(`Umfrage ${id}`)
                        );
                    });
                } else {
                    $pollSelect.append(
                        $('<option></option>').val('').text('Keine Umfragen verfügbar')
                    );
                }
            },
            error: function (xhr, status, error) {
                console.error("Fehler beim Abrufen der Umfragen:", status, error);
                console.log("Fehler-Details:", xhr.responseText);
            }
        });
    }

    // Event-Listener für das Parlament-Auswahlfeld
    $parliamentSelect.change(function () {
        const selectedParliamentId = $parliamentSelect.val();
        console.log("Parlament geändert:", selectedParliamentId);
        fetchPolls(selectedParliamentId);
    });

    // Initiales Laden bei Seitenaufruf
    const initialParliamentId = $parliamentSelect.val();
    console.log("Initiales Laden für Parlament ID:", initialParliamentId);
    fetchPolls(initialParliamentId);
});
