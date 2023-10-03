<template>
    <div class="rps-selector">
        <Subheader>Choose your weapon</Subheader>
        <div class="rps-selector__weapons"></div>
        <button @click="setMyWeapon('rock')"><Weapon type="rock" /></button>
        <button @click="setMyWeapon('paper')"><Weapon type="paper" /></button>
        <button @click="setMyWeapon('scissors')">
            <Weapon type="scissors" />
        </button>
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../logic/api';
    import Weapon from './Weapon.vue';
    import Subheader from './Subheader.vue';

    const { setMyWeapon } = useApi();
</script>

<style>
    .rps-selector {
        text-align: center;
    }
    .rps-selector__weapons {
        display: flex;
        justify-content: center;
        width: 100%;
    }

    .rps-selector button {
        margin: 0 0.5rem;
        padding: 1rem;
        border-radius: 10px;
        border: none;
        background-color: white;
        cursor: pointer;
    }
</style>
