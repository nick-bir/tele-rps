<template>
    <Hand :choise="myWeapon" name="My hand" />
</template>

<script setup lang="ts">
    import { Figures, useApi } from '../../../logic/api';
    import Hand from './Hand.vue';

    defineProps<{ choise: Figures }>();

    const { myWeapon } = useApi();
</script>
