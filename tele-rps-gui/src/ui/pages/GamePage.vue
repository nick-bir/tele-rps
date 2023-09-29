<template>
    <div class="game">
        <Header>Game started</Header>
        <Score />
        <RpsSelector v-if="!isMyChoiseMade" @choise="makeMyChoise" />
        <Hands v-if="isMyChoiseMade || isRoundFinished" />
    </div>
</template>

<script setup lang="ts">
    import Header from '../components/Header.vue';
    import Score from '../components/Score.vue';
    import RpsSelector from '../components/RpsSelector.vue';
    import Hands from '../components/Hands/Hands.vue';
    import { useApi } from '../../logic/api';

    const { isRoundFinished, makeMyChoise, isMyChoiseMade, isGameStarted } =
        useApi();
</script>

<style>
    .game {
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .game__title {
        font-size: 2rem;
        font-weight: bold;
        margin-bottom: 1rem;
    }
</style>
../components/Hands/Hands.vue
