<template>
    <Hand :choise="enemyWeapon" name="Opponent hand" />
</template>

<script setup lang="ts">
    import { Gesture, useState } from '../../state';
    import Hand from './Hand.vue';

    defineProps<{ choise: Gesture }>();

    const { enemyWeapon } = useState();
</script>
../../../state/api
