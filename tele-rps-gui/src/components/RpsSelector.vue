<template>
    <div class="rps-selector">
        <Subheader>Choose your weapon</Subheader>
        <div class="rps-selector__weapons"></div>
        <!-- TODO: maybe remove button-->
        <button @click="setMyWeapon('ROCK')"><Weapon type="ROCK" /></button>
        <button @click="setMyWeapon('PAPER')"><Weapon type="PAPER" /></button>
        <button @click="setMyWeapon('SCISSORS')">
            <Weapon type="SCISSORS" />
        </button>
    </div>
</template>

<script setup lang="ts">
    import { useState } from '../state';
    import Weapon from './Weapon.vue';
    import Subheader from './Subheader.vue';

    const { setMyWeapon } = useState();
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
../../state/api
