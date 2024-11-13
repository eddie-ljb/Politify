document.addEventListener("DOMContentLoaded", function () {
    const pollSelect = document.getElementById("pollId");
    const showResultsBtn = document.getElementById("showResultsBtn");
    const resultsTableBody = document.getElementById("resultsTableBody");
    const resultsTable = document.getElementById("resultsTable");
    const ctx = document.getElementById('pollResultsChart').getContext('2d');
    let pollResultsChart; // Variable für das Diagramm

    // Die Farben für die Parteien (nur für das Diagramm relevant)
    const partyColors = {
        "CDU/CSU": "black",
        "CDU": "black",
        "CSU": "black",
        "SPD": "red",
        "Grüne": "green",
        "AfD": "blue",
        "FDP": "yellow",
        "BSW": "purple",
        "Linke": "rgb(255,20,147)",
        "Freie Wähler": "orange",
        "Sonstiges": "gray"
    };

    // Die Logos für die Parteien
    const partyLogos = {
        "CDU/CSU": "parteien/cducsu.jpg", // Beispielpfad zu den Logos
        "CDU": "parteien/cducsu.jpg",
        "CSU": "parteien/cducsu.jpg",
        "SPD": "parteien/spd.jpg",
        "Grüne": "parteien/grüne.jpg",
        "AfD": "parteien/afd.jpg",
        "FDP": "parteien/fdp.jpg",
        "BSW": "parteien/bsw.jpg",
        "Linke": "parteien/dielinke.jpg",
        "Freie Wähler": "parteien/fw.jpg",
        "Sonstiges": "parteien/sonstiges.jpg"
    };

    // Funktion zum Abrufen der Umfrageergebnisse
    async function fetchResultsForPoll(umfrageId, parlament) {
        try {
            // Abrufen der Ergebnisse über eine AJAX-Anfrage
            const response = await fetch(`/data/getUmfageErgebnisseFuerUmfrageID?umfrage=${umfrageId}`);

            if (!response.ok) {
                throw new Error('Fehler beim Abrufen der Umfrageergebnisse');
            }

            const results = await response.json();

            // Ergebnisse nach Prozentwerten in absteigender Reihenfolge sortieren (außer "Sonstiges")
            const sortedResults = Object.entries(results)
                .filter(entry => entry[0] !== "Sonstiges") // Sonstiges wird separat behandelt
                .sort((a, b) => b[1] - a[1]);  // Sortiert nach dem Prozentwert (absteigend)

            // Sonstiges immer ganz rechts hinzufügen
            const other = Object.entries(results).find(entry => entry[0] === "Sonstiges");
            if (other) {
                sortedResults.push(other); // Sonstiges an das Ende der Liste anfügen
            }

            // Die Namen der Parteien und deren Prozentsätze extrahieren
            const parties = sortedResults.map(entry => entry[0]);
            const percentages = sortedResults.map(entry => entry[1]);

            // Die Tabelle zurücksetzen
            resultsTableBody.innerHTML = '';

            // Erstelle die erste Zeile (Parteinamen und Logos)
            const nameRow = document.createElement('tr');
            parties.forEach(partei => {
                const nameCell = document.createElement('td');
                try {
                    const logoImage = document.createElement('img');
                    logoImage.src = partyLogos[partei] || "parteien/default.png"; // Standard-Logo, wenn keines definiert ist
                    logoImage.alt = partei + " Logo";
                    logoImage.style.width = "30px"; // Größe des Logos
                    nameCell.appendChild(logoImage);
                } catch (error) {
                    console.error(`Fehler beim Laden des Logos für ${partei}: `, error);
                }
                nameCell.appendChild(document.createTextNode(' ' + partei)); // Parteiname hinter dem Logo
                nameRow.appendChild(nameCell);
            });
            resultsTableBody.appendChild(nameRow);

            // Erstelle die zweite Zeile (Prozentzahlen)
            const percentageRow = document.createElement('tr');
            percentages.forEach(prozent => {
                const prozentCell = document.createElement('td');
                prozentCell.textContent = prozent + "%";
                percentageRow.appendChild(prozentCell);
            });
            resultsTableBody.appendChild(percentageRow);

            // Tabelle anzeigen
            resultsTable.style.display = 'table';

            // Abrufen der Sperrklausel für das Parlament
            const sperrklauselResponse = await fetch(`/data/getSperrKlausurFuerUmfrage?umfrage=${umfrageId}`);
            const sperrklausel = await sperrklauselResponse.json();

            // Erstellen des Diagramms
            const colors = parties.map(partei => partyColors[partei] || 'gray'); // Wenn keine Farbe zugeordnet, grau

            if (pollResultsChart) {
                pollResultsChart.destroy();
            }

            // Berechnung des maximalen Werts für die Y-Achse
            const maxPercentage = Math.max(...percentages);
            const maxYValue = Math.min(50, maxPercentage + 5); // Maximalwert: 50 oder der höchste Wert + 5%

            // Erstellen des Diagramms
            pollResultsChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: parties, // Parteien
                    datasets: [{
                        label: 'Stimmenanteil (%)',
                        data: percentages, // Prozentwerte
                        backgroundColor: colors, // Parteienfarben
                        borderColor: colors.map(color => color === 'gray' ? 'darkgray' : color),
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Partei'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            max: maxYValue, // Dynamische Maximalhöhe
                            title: {
                                display: true,
                                text: 'Prozent (%)'
                            }
                        }
                    },
                    plugins: {
                        afterDatasetsDraw: function (chart) {
                            const ctx = chart.ctx;
                            const dataset = chart.data.datasets[0];
                            dataset.data.forEach((data, index) => {
                                const x = chart.getDatasetMeta(0).data[index].x;
                                const y = chart.getDatasetMeta(0).data[index].y;
                                const party = parties[index];
                                const logoUrl = partyLogos[party];

                                const logoImage = new Image();
                                logoImage.src = logoUrl;

                                // Wenn das Bild geladen werden kann, zeichnen wir es auf den Balken
                                logoImage.onload = function () {
                                    ctx.drawImage(logoImage, x - 15, y - 10, 30, 30);
                                };

                                // Falls das Bild nicht geladen werden kann, wird der Parteiname angezeigt
                                logoImage.onerror = function () {
                                    ctx.font = '12px Arial';
                                    ctx.fillStyle = 'black';
                                    ctx.fillText(party, x - 15, y - 10);
                                };
                            });
                        }
                    }
                }
            });

        } catch (error) {
            console.error("Fehler beim Abrufen der Umfrageergebnisse:", error);
        }
    }

    // Event-Listener für den Button "Ergebnisse anzeigen"
    showResultsBtn.addEventListener("click", function () {
        const selectedPollId = pollSelect.value;
        const selectedParlament = "DE"; // Hier den richtigen Wert für das Parlament setzen (z.B. "DE" oder eine andere ID)
        if (selectedPollId) {
            fetchResultsForPoll(selectedPollId, selectedParlament);
        } else {
            alert("Bitte wählen Sie eine Umfrage aus.");
        }
    });
});
