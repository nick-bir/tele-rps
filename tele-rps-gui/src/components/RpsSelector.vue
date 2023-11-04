<template>
    <div class="rps-selector">
        <Subheader>Choose your weapon</Subheader>
        <div class="rps-selector__weapons">
            <Weapon
                type="ROCK"
                class="rps-selector__weapon"
                @click="setMyWeapon('ROCK')"
            />
            <Weapon
                type="PAPER"
                class="rps-selector__weapon"
                @click="setMyWeapon('PAPER')"
            />
            <Weapon
                type="SCISSORS"
                class="rps-selector__weapon"
                @click="setMyWeapon('SCISSORS')"
            />
        </div>
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

    .rps-selector__weapon {
        margin: 0 0.5rem;
    }
</style>
../../state/api
