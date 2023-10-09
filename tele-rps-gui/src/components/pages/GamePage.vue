<template>
    <div class="game">
        <Header>fight with {{ enemyName }}</Header>
        <!-- <Score /> -->
        <RpsSelector v-if="!isMyWeaponChosen" @choise="setMyWeapon" />
        <Hands v-if="isMyWeaponChosen" />
    </div>
</template>

<script setup lang="ts">
    import Header from '../Header.vue';
    import RpsSelector from '../RpsSelector.vue';
    import Hands from '../Hands/Hands.vue';
    import { useState } from '../../state';

    const { setMyWeapon, isMyWeaponChosen, enemyName } = useState();
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
../../components/Header.vue../../components/RpsSelector.vue../../components/Hands/Hands.vue
