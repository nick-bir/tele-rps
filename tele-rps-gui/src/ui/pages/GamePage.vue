<template>
    <div class="game">
        <Header>fight with {{ enemyName }}</Header>
        <!-- <Score /> -->
        <RpsSelector v-if="!isMyWeaponChoosen" @choise="setMyWeapon" />
        <Hands v-if="isMyWeaponChoosen" />
    </div>
</template>

<script setup lang="ts">
    import Header from '../components/Header.vue';
    import RpsSelector from '../components/RpsSelector.vue';
    import Hands from '../components/Hands/Hands.vue';
    import { useState } from '../../state/state';

    const { setMyWeapon, isMyWeaponChoosen, enemyName } = useState();
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
