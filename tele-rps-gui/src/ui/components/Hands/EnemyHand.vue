<template>
    <Hand :choise="enemyWeapon" name="Opponent hand" />
</template>

<script setup lang="ts">
    import { Figures, useApi } from '../../../logic/api';
    import Hand from './Hand.vue';

    defineProps<{ choise: Figures }>();

    const { enemyWeapon } = useApi();
</script>
