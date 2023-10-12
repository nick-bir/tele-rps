<template>
    <Hand :choise="myWeapon" name="My hand" />
</template>

<script setup lang="ts">
    import { Gesture, useState } from '../../state';
    import Hand from './Hand.vue';

    defineProps<{ choise: Gesture }>();

    const { myWeapon } = useState();
</script>
../../../state/api
