document.addEventListener("DOMContentLoaded", function () {
    const pollSelect = document.getElementById("pollId");
    const showResultsBtn = document.getElementById("showResultsBtn"); // Button zum Anzeigen der Ergebnisse
    const surveyDetailsContainer = document.getElementById("surveyDetails");
    const resultsTableBody = document.getElementById("resultsBody");
    const resultsTable = document.getElementById("resultsTable");

    // Funktion zum Abrufen der Umfrage-Details
    async function fetchSurveyDetails(umfrageId) {
        try {
            // Abrufen der Umfrage-Details über eine AJAX-Anfrage
            const response = await fetch(`/data/getUmfrageDetailsFuerUmfrageID?umfrage=${umfrageId}`);

            if (!response.ok) {
                throw new Error('Fehler beim Abrufen der Umfrage-Details');
            }

            const details = await response.json();

            // Darstellung der Umfrage-Details im HTML
            document.getElementById("parlamentName").textContent = details.Parlament || 'Nicht verfügbar';
            document.getElementById("surveyDate").textContent = details.Date || 'Nicht verfügbar';
            document.getElementById("sampleSize").textContent = details['Befragte Personen'] || 'Nicht verfügbar';
            document.getElementById("surveyInstitute").textContent = details.Institut || 'Nicht verfügbar';
            document.getElementById("surveyTasker").textContent = details.Auftraggeber || 'Nicht verfügbar';
            document.getElementById("surveyMethod").textContent = details.Methode || 'Nicht verfügbar';

            // Wenn das Institut ein Logo hat, es hier anzeigen
            const instituteLogoUrl = getInstituteLogo(details.Institut);  // Funktion zum Abrufen des Logos des Instituts
            const logoElement = document.getElementById("surveyInstituteLogo");
            if (instituteLogoUrl) {
                logoElement.src = instituteLogoUrl;
                logoElement.style.display = "block";  // Logo anzeigen
            } else {
                logoElement.style.display = "none";  // Logo ausblenden, falls kein Logo vorhanden
            }

            // Zusätzliche Stilverbesserungen für Umfrage-Details
            surveyDetailsContainer.style.backgroundColor = "#f8f9fa"; // Hintergrundfarbe
            surveyDetailsContainer.style.borderRadius = "8px"; // Abgerundete Ecken
            surveyDetailsContainer.style.padding = "20px"; // Innenabstand

        } catch (error) {
            console.error("Fehler beim Abrufen der Umfrage-Details:", error);
            surveyDetailsContainer.innerHTML = "<p>Fehler beim Laden der Umfrage-Details.</p>";
        }
    }

    // Funktion zum Abrufen des Logos des Instituts
    function getInstituteLogo(institutName) {
        const logos = {
            "Institut XY": "path_to_logo_institut_xy.png",
            "Institut ABC": "path_to_logo_institut_abc.png",
            // Weitere Institut-Logo-Zuweisungen
        };

        return logos[institutName] || null; // Gibt null zurück, wenn kein Logo gefunden wurde
    }

    // Funktion zum Abrufen der Umfrageergebnisse
    async function fetchResultsForPoll(umfrageId) {
        try {
            // Abrufen der Ergebnisse über eine AJAX-Anfrage
            const response = await fetch(`/data/getUmfageErgebnisseFuerUmfrageID?umfrage=${umfrageId}`);

            if (!response.ok) {
                throw new Error('Fehler beim Abrufen der Umfrageergebnisse');
            }

            const results = await response.json();

            // Ergebnisse nach Prozentwerten in absteigender Reihenfolge sortieren
            const sortedResults = Object.entries(results)
                .sort((a, b) => b[1] - a[1]);  // Sortiert nach dem Prozentwert (absteigend)

            // Tabelle zurücksetzen
            resultsTableBody.innerHTML = '';

            // Sortierte Ergebnisse dynamisch in die Tabelle einfügen
            sortedResults.forEach(([partei, prozent]) => {
                const row = document.createElement('tr');

                const parteiCell = document.createElement('td');
                parteiCell.textContent = partei;

                const prozentCell = document.createElement('td');
                prozentCell.textContent = prozent + "%";

                row.appendChild(parteiCell);
                row.appendChild(prozentCell);
                resultsTableBody.appendChild(row);
            });

            // Tabelle anzeigen
            resultsTable.style.display = 'table';

        } catch (error) {
            console.error("Fehler beim Abrufen der Umfrageergebnisse:", error);
        }
    }

    // Event-Listener für den Button "Ergebnisse anzeigen"
    showResultsBtn.addEventListener("click", function () {
        const selectedPollId = pollSelect.value;
        if (selectedPollId) {
            // Ergebnisse und Umfrage-Details abrufen
            fetchResultsForPoll(selectedPollId);
            fetchSurveyDetails(selectedPollId); // Umfrage-Details werden ebenfalls abgerufen
        } else {
            alert("Bitte wählen Sie eine Umfrage aus.");
        }
    });

});
