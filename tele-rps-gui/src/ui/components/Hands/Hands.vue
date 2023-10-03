<template>
    <div class="hands">
        <MyHand class="hand" :choise="myWeapon" />
        <EnemyHand class="hand" :choise="enemyWeapon" />
    </div>
</template>

<script setup lang="ts">
    import { useApi } from '../../../logic/api';
    import MyHand from './MyHand.vue';
    import EnemyHand from './EnemyHand.vue';

    const { myWeapon, enemyWeapon } = useApi();
</script>

<style>
    .hands {
        display: flex;
        justify-content: space-around;
        width: 100%;
    }

    .hand {
        align-self: stretch;
    }
</style>
