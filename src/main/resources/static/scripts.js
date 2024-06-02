document.addEventListener('DOMContentLoaded', function () {
    fetch('http://localhost:8080/cric-info/getMatchInfo/${matchId}}') // Replace with actual API endpoint
        .then(response => response.json())
        .then(data => {
            displayScorecard(data);
        })
        .catch(error => console.error('Error fetching data:', error));
});

function displayScorecard(data) {
    const team1Batting = document.getElementById('team1-batting').querySelector('tbody');
    const team1Bowling = document.getElementById('team1-bowling').querySelector('tbody');
    const team2Batting = document.getElementById('team2-batting').querySelector('tbody');
    const team2Bowling = document.getElementById('team2-bowling').querySelector('tbody');

    // Populate team 1 batting
    data.team1.batting.forEach(player => {
        const row = `<tr>
            <td>${player.name}</td>
            <td>${player.runs}</td>
            <td>${player.balls}</td>
        </tr>`;
        team1Batting.innerHTML += row;
    });

    // Populate team 1 bowling
    data.team1.bowling.forEach(bowler => {
        const row = `<tr>
            <td>${bowler.name}</td>
            <td>${bowler.overs}</td>
            <td>${bowler.runs}</td>
            <td>${bowler.wickets}</td>
        </tr>`;
        team1Bowling.innerHTML += row;
    });

    // Populate team 2 batting
    data.team2.batting.forEach(player => {
        const row = `<tr>
            <td>${player.name}</td>
            <td>${player.runs}</td>
            <td>${player.balls}</td>
        </tr>`;
        team2Batting.innerHTML += row;
    });

    // Populate team 2 bowling
    data.team2.bowling.forEach(bowler => {
        const row = `<tr>
            <td>${bowler.name}</td>
            <td>${bowler.overs}</td>
            <td>${bowler.runs}</td>
            <td>${bowler.wickets}</td>
        </tr>`;
        team2Bowling.innerHTML += row;
    });
}