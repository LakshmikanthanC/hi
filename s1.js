const rollDiceButton = document.getElementById('rollDice');
const diceResultSpan = document.getElementById('diceResult');
const playerPositionSpan = document.getElementById('playerPosition');

let playerPosition = 1;

const snakesAndLadders = {
    16: 6,
    47: 26,
    49: 11,
    56: 53,
    62: 19,
    64: 60,
    87: 24,
    93: 73,
    95: 75,
    98: 78,
    1: 38,
    4: 14,
    9: 31,
    21: 42,
    28: 84,
    36: 44,
    51: 67,
    71: 91,
    80: 100,
};

function rollDice() {
    return Math.floor(Math.random() * 6) + 1;
}

function movePlayer(roll) {
    let newPosition = playerPosition + roll;
    if (newPosition > 100) {
        newPosition = playerPosition;
    } else if (snakesAndLadders[newPosition]) {
        newPosition = snakesAndLadders[newPosition];
    }
    playerPosition = newPosition;
}

rollDiceButton.addEventListener('click', () => {
    const diceRoll = rollDice();
    diceResultSpan.textContent = diceRoll;
    movePlayer(diceRoll);
    playerPositionSpan.textContent = playerPosition;
});
