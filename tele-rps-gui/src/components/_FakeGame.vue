<template>
    <div class="_fake-game">
        Fake Game Controller
        <div>
            <button @click="startNewGame">Start new game</button>
        </div>
        <div>
            <br />
            choose for opponent:
            <br />
            <button @click="setEnemyWeapon('ROCK')">Rock</button>
            <button @click="setEnemyWeapon('PAPER')">Paper</button>
            <button @click="setEnemyWeapon('SCISSORS')">Scissors</button>
        </div>
        <div>
            <br />
            game result:
            <br />
            <button @click="finishGameWithResult('VICTORY')">Victory</button>
            <button @click="finishGameWithResult('DEFEAT')">Defeat</button>
            <button @click="finishGameWithResult('DRAW')">Draw</button>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { GameResult, useState } from '../state';

    const { startNewGame, setEnemyWeapon, setGameResult, setGameStatus } =
        useState();

    function finishGameWithResult(result: GameResult) {
        setGameResult(result);
        setGameStatus('COMPLETED');
    }
</script>

<style scoped>
    ._fake-game {
        margin-top: 40px;
        border: 1px solid black;
        padding: 10px;
    }
</style>
../../state/api
