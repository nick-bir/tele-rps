<template>
    <Hand :choise="enemyWeapon" name="Opponent hand" />
</template>

<script setup lang="ts">
    import { Figures, useState } from '../../state';
    import Hand from './Hand.vue';

    defineProps<{ choise: Figures }>();

    const { enemyWeapon } = useState();
</script>
../../../state/api
